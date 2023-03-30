package ui.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.*;
import java.util.List;

// Represents a JPanel with chart for user vs average footprint comparison
public class ChartManager extends JPanel {
    private final List<Double> valuesUser = new ArrayList<>();
    private final List<Double> valuesAvg = new ArrayList<>();
    private final String[] categories = {"Food", "Travel", "Misc.", "Total"};

    // EFFECTS: constructs a chart manager and processes the given data points
    public ChartManager(Map<String, List<Double>> data) {
        List<Double> foodValues = data.get("food");
        List<Double> travelValues = data.get("travel");
        List<Double> miscValues = data.get("misc");
        List<Double> totalValues = data.get("total");
        valuesUser.add(foodValues.get(0));
        valuesUser.add(travelValues.get(0));
        valuesUser.add(miscValues.get(0));
        valuesUser.add(totalValues.get(0));
        valuesAvg.add(foodValues.get(1));
        valuesAvg.add(travelValues.get(1));
        valuesAvg.add(miscValues.get(1));
        valuesAvg.add(totalValues.get(1));

        setPreferredSize(new Dimension(300, 300));
    }

    // MODIFIES: this
    // EFFECTS: draws the comparison chart
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        drawTitle(g2d);
        drawLegend(g2d);
        drawBars(g2d);
    }

    // REQUIRES: (valuesUser.size() >= categories.length) && (valuesAvg.size() >= categories.length)
    // EFFECTS: draws the user and average bars for each category
    private void drawBars(Graphics2D g2d) {
        int barWidth = 20;
        int gap = 20;
        int categoryCount = categories.length;

        for (int i = 0; i < categoryCount; i++) {
            int x1 = (i * (barWidth * 2 + gap)) + gap;
            int y1 = (int) (getHeight() - valuesUser.get(i) - 25);
            g2d.setColor(Color.RED);
            g2d.fill(new Rectangle2D.Double(x1, y1, barWidth, valuesUser.get(i)));

            int x2 = x1 + barWidth;
            int y2 = (int) (getHeight() - valuesAvg.get(i) - 25);
            g2d.setColor(Color.BLUE);
            g2d.fill(new Rectangle2D.Double(x2, y2, barWidth, valuesAvg.get(i)));

            g2d.setColor(Color.BLACK);
            FontMetrics fm = g2d.getFontMetrics();
            int labelWidth = fm.stringWidth(categories[i]);
            int labelX = x1 + (barWidth - labelWidth / 2);
            int labelY = getHeight() - 5;
            g2d.drawString(categories[i], labelX, labelY);
        }
    }

    // EFFECTS: draws the title of the chart
    private void drawTitle(Graphics2D g2d) {
        String title = "Your Footprint vs Average Footprint";
        g2d.setColor(Color.BLACK);
        FontMetrics fm = g2d.getFontMetrics();
        int titleWidth = fm.stringWidth(title);
        int titleX = (getWidth() - titleWidth) / 2;
        int titleY = 30;
        g2d.drawString(title, titleX, titleY);
    }

    // EFFECTS: draws the legend of the chart for User and Average
    private void drawLegend(Graphics2D g2d) {
        int legendX = getWidth() - 100;
        int legendY = 50;
        int boxSize = 12;

        g2d.setColor(Color.RED);
        g2d.fill(new Rectangle2D.Double(legendX, legendY, boxSize, boxSize));
        g2d.setColor(Color.BLACK);
        g2d.drawString("User", legendX + boxSize + 5, legendY + boxSize);
        g2d.setColor(Color.BLUE);
        g2d.fill(new Rectangle2D.Double(legendX, legendY + boxSize + 5, boxSize, boxSize));
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.PLAIN, 12));
        g2d.drawString("Average", legendX + boxSize + 5, legendY + boxSize * 2 + 5);
    }

}