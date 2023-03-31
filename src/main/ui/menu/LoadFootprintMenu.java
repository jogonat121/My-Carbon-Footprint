package ui.menu;

import data.FootprintRecord;
import data.UserRecords;
import data.exceptions.CannotAccessDataException;
import model.Footprint;
import persistence.JsonReader;
import ui.utils.ChartManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ui.MyCarbonFootprint.UNITS;

// Represents a menu for loading footprint record from local file
public class LoadFootprintMenu extends JFrame implements ActionListener {
    private FootprintRecord footprintRecord;
    private JTextField fileNameField;
    private JPanel footprintPanel;
    private JLabel foodFootprintLabel;
    private JLabel travelFootprintLabel;
    private JLabel miscFootprintLabel;
    private JLabel totalFootprintLabel;

    // EFFECTS: constructs a menu with your footprint name;
    // throws CannotAccessDataException if error occurs while accessing data
    public LoadFootprintMenu(JFrame parentFrame) throws CannotAccessDataException {
        super("Your Footprint");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(parentFrame.getWidth(), parentFrame.getHeight());
        setLocationRelativeTo(parentFrame);

        add(getFileNamePanel(), BorderLayout.NORTH);
        add(getFootprintPanel(), BorderLayout.CENTER);
        add(getFooterPanel(), BorderLayout.SOUTH);

        setVisible(true);
    }

    // EFFECTS: constructs and returns a JPanel for the footer
    private JPanel getFooterPanel() {
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton backButton = createButton("Back to previous menu", "back");
        footerPanel.add(backButton);
        return footerPanel;
    }

    // MODIFIES: this
    // EFFECTS: constructs and returns a JPanel for displaying the footprint
    private JPanel getFootprintPanel() {
        footprintPanel = new JPanel(new GridLayout(5, 1));
        foodFootprintLabel = new JLabel();
        travelFootprintLabel = new JLabel();
        miscFootprintLabel = new JLabel();
        totalFootprintLabel = new JLabel();
        footprintPanel.add(foodFootprintLabel);
        footprintPanel.add(travelFootprintLabel);
        footprintPanel.add(miscFootprintLabel);
        footprintPanel.add(totalFootprintLabel);
        return footprintPanel;
    }

    // MODIFIES: this
    // EFFECTS: constructs and returns a JPanel for asking file to load
    private JPanel getFileNamePanel() {
        JPanel fileNamePanel = new JPanel();
        fileNamePanel.setLayout(new BoxLayout(fileNamePanel, BoxLayout.Y_AXIS));
        JLabel fileNameLabel = new JLabel();
        fileNameField = new JTextField(10);
        JButton loadButton = createButton("Load", "load");
        fileNamePanel.add(fileNameLabel);
        fileNamePanel.add(fileNameField);
        fileNamePanel.add(loadButton);
        return fileNamePanel;
    }

    // EFFECTS: constructs and returns a JButton with the given text and command
    private JButton createButton(String buttonText, String actionCommand) {
        JButton button = new JButton(buttonText);
        button.setActionCommand(actionCommand);
        button.addActionListener(this);
        return button;
    }

    // MODIFIES: this
    // EFFECTS: loads the footprint record from JSON file and displays the footprint values;
    // throws CannotAccessDataException if error occurs while reading file;
    public void loadFootprintFromFile(String fileName) throws CannotAccessDataException {
        if (fileName != null && !fileName.isEmpty()) {
            footprintRecord = new JsonReader(fileName).read();
            setFootprintValues();
            footprintPanel.add(createToolsPanel());
        }
    }

    // MODIFIES: this
    // EFFECTS: updates and displays the footprint values
    private void setFootprintValues() {
        Footprint foodFootprint = footprintRecord.getFoodFootprint();
        Footprint travelFootprint = footprintRecord.getTravelFootprint();
        Footprint miscFootprint = footprintRecord.getMiscFootprint();
        foodFootprintLabel.setText("Your Food Footprint is: " + foodFootprint.getValue() + UNITS);
        travelFootprintLabel.setText("Your Travel Footprint is: " + travelFootprint.getValue() + UNITS);
        miscFootprintLabel.setText("Your Misc. Footprint is: " + miscFootprint.getValue() + UNITS);
        totalFootprintLabel.setText("Your Total Footprint is: " + footprintRecord.getTotalValue() + UNITS);
    }

    // EFFECTS: creates and returns a JPanel with buttons for footprint tools
    private JPanel createToolsPanel() {
        JPanel buttonsPanel = new JPanel(new GridLayout(1, 3));
        JButton editButton = createButton("Edit Footprint", "edit");
        JButton saveButton = createButton("Save Footprint", "save");
        JButton contributeButton = createButton("Compare", "compare");
        buttonsPanel.add(editButton);
        buttonsPanel.add(saveButton);
        buttonsPanel.add(contributeButton);
        return buttonsPanel;
    }

    // MODIFIES: file
    // EFFECTS: saves the edits to the JSON file
    private void saveRecord(FootprintRecord footprintRecord, String fileName) {
        if (fileName != null && !fileName.isEmpty()) {
            try {
                footprintRecord.exportFile(fileName);
                JOptionPane.showMessageDialog(this,
                        "Successfully saved to ./data/" + fileName + ".json", "Footprint Saved",
                        JOptionPane.PLAIN_MESSAGE, new ImageIcon("./data/icons/saved.png"));
            } catch (CannotAccessDataException e) {
                JOptionPane.showMessageDialog(this, "Cannot write data to file",
                        "Cannot Access Data Exception", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    // REQUIRES: new UserRecords.getAverages().size() >= 4
    // EFFECTS: generates data for comparison chart and displays a user vs average footprint chart;
    // throws CannotAccessDataException if error occurs while reading average data;
    private void showComparison() throws CannotAccessDataException {
        List<Double> foodValues = new ArrayList<>();
        List<Double> travelValues = new ArrayList<>();
        List<Double> miscValues = new ArrayList<>();
        List<Double> totalValues = new ArrayList<>();
        UserRecords userRecords = new UserRecords();
        List<Double> averages = userRecords.getAverages();

        foodValues.add(footprintRecord.getFoodFootprint().getValue());
        travelValues.add(footprintRecord.getTravelFootprint().getValue());
        miscValues.add(footprintRecord.getMiscFootprint().getValue());
        totalValues.add(footprintRecord.getTotalValue());
        foodValues.add(averages.get(0));
        travelValues.add(averages.get(1));
        miscValues.add(averages.get(2));
        totalValues.add(averages.get(3));

        Map<String, List<Double>> chartData = new HashMap<>();
        chartData.put("food", foodValues);
        chartData.put("travel", travelValues);
        chartData.put("misc", miscValues);
        chartData.put("total", totalValues);
        ChartManager chartPanel = new ChartManager(chartData);
        JOptionPane.showMessageDialog(null, chartPanel,
                "Comparison Graph", JOptionPane.PLAIN_MESSAGE);

    }

    // EFFECTS: listens to buttons clicked and performs corresponding actions
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            switch (e.getActionCommand()) {
                case "load":
                    loadFootprintFromFile(fileNameField.getText());
                    break;
                case "edit":
                    new EditFootprintMenu(footprintRecord, this);
                    break;
                case "save":
                    saveRecord(footprintRecord, fileNameField.getText());
                    break;
                case "compare":
                    showComparison();
                    break;
                default:
                    setVisible(false);
            }
        } catch (CannotAccessDataException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Cannot read file!",
                    JOptionPane.WARNING_MESSAGE, new ImageIcon("./data/icons/notFound.png"));
        }
    }

    // EFFECTS: refreshes the JFrame with updated values
    public void refresh() {
        setFootprintValues();
    }
}
