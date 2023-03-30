package ui.menu;

import data.UserRecords;
import data.exceptions.CannotAccessDataException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import static ui.MyCarbonFootprint.UNITS;

// Represents a menu for displaying the average footprint
public class AveragesMenu extends JFrame implements ActionListener {
    private JLabel foodFootprintLabel;
    private JLabel travelFootprintLabel;
    private JLabel miscFootprintLabel;
    private JLabel totalFootprintLabel;

    // EFFECTS: constructs and runs a JFrame with title averages with JPanels added
    // throws CannotAccessDataException if error occurs while accessing data
    public AveragesMenu(JFrame parentFrame) throws CannotAccessDataException {
        super("Averages");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(parentFrame.getWidth(), parentFrame.getHeight());
        setLocationRelativeTo(parentFrame);

        add(getAveragesPanel(), BorderLayout.CENTER);
        add(getFooterPanel(), BorderLayout.SOUTH);

        setVisible(true);
        setAverages();
    }

    // EFFECTS: constructs and returns a JPanel for the footer
    private JPanel getFooterPanel() {
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton backButton = new JButton("Back to previous menu");
        backButton.setActionCommand("back");
        backButton.addActionListener(this);
        footerPanel.add(backButton);
        return footerPanel;
    }

    // MODIFIES: this
    // EFFECTS: constructs and returns a JPanel for the averages
    private JPanel getAveragesPanel() {
        JPanel averagesPanel = new JPanel(new GridLayout(4, 1));
        foodFootprintLabel = new JLabel();
        travelFootprintLabel = new JLabel();
        miscFootprintLabel = new JLabel();
        totalFootprintLabel = new JLabel();
        averagesPanel.add(foodFootprintLabel);
        averagesPanel.add(travelFootprintLabel);
        averagesPanel.add(miscFootprintLabel);
        averagesPanel.add(totalFootprintLabel);
        return averagesPanel;
    }

    // MODIFIES: this
    // EFFECTS: retrieves and updates the average values of the footprint
    // throws CannotAccessDataException if error occurs while accessing records
    private void setAverages() throws CannotAccessDataException {
        UserRecords userRecords = new UserRecords();
        List<Double> averages = userRecords.getAverages();

        foodFootprintLabel.setText("\nThe Average Food Footprint is: " + averages.get(0) + UNITS);
        travelFootprintLabel.setText("The Average Travel Footprint is: " + averages.get(1) + UNITS);
        miscFootprintLabel.setText("The Average Misc. Footprint is: " + averages.get(2) + UNITS);
        totalFootprintLabel.setText("The Average Total Footprint is: " + averages.get(3) + UNITS);
    }

    // EFFECTS: listens to buttons clicked and performs corresponding actions
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("back")) {
            setVisible(false);
        }
    }
}
