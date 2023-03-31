package ui;

import data.UserRecords;
import data.exceptions.CannotAccessDataException;
import data.exceptions.RecordNotFoundException;
import model.exceptions.EmptyQuestionBankException;
import ui.menu.AveragesMenu;
import ui.menu.CalculateFootprintMenu;
import ui.menu.LoadFootprintMenu;
import ui.utils.ImageManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Represents the UI
public class MyCarbonFootprint extends JFrame implements ActionListener {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final String UNITS = " metric tonnes CO2 a year";

    // EFFECTS: constructs the UI and runs the app
    public MyCarbonFootprint() {
        super("My Carbon Footprint");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);

        add(mainPanel(), BorderLayout.CENTER);

        setVisible(true);
    }

    private JPanel mainPanel() {
        JPanel mainPanel = new JPanel(new GridLayout(2, 1));
        mainPanel.add(new ImageManager(new ImageIcon("./data/icons/logo.png").getImage()));
        mainPanel.add(getButtonsPanel());
        return mainPanel;
    }

    private JPanel getButtonsPanel() {
        JPanel buttonsPanel = new JPanel(new GridLayout(5, 1));
        JButton calculateButton = createButton("Calculate My Footprint", "calcMenu");
        JButton averageButton = createButton("Check Average Footprint", "avgMenu");
        JButton loadButton = createButton("Load Footprint From File", "loadMenu");
        JButton deleteButton = createButton("Delete My Contribution", "deleteMenu");
        JButton quitButton = createButton("Quit", "quit");
        buttonsPanel.add(calculateButton);
        buttonsPanel.add(averageButton);
        buttonsPanel.add(loadButton);
        buttonsPanel.add(deleteButton);
        buttonsPanel.add(quitButton);
        return buttonsPanel;
    }

    private JButton createButton(String buttonText, String actionCommand) {
        JButton button = new JButton(buttonText);
        button.setActionCommand(actionCommand);
        button.addActionListener(this);
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            switch (e.getActionCommand()) {
                case "calcMenu":
                    new CalculateFootprintMenu(this);
                    break;
                case "avgMenu":
                    new AveragesMenu(this);
                    break;
                case "loadMenu":
                    new LoadFootprintMenu(this);
                    break;
                case "deleteMenu":
                    deleteRecord();
                    break;
                default:
                    dispose();
                    System.exit(0);
                    break;
            }
        } catch (CannotAccessDataException | EmptyQuestionBankException ex) {
            System.out.println(ex.getMessage());
        }
    }

    // EFFECTS: asks record id and deletes the record of the entered id if found;
    // throws CannotAccessDataException if error occurs while deleting record from data
    private void deleteRecord() throws CannotAccessDataException {
        String recordID = (String) JOptionPane.showInputDialog(this,
                "Enter unique ID of the record to be deleted:", "Delete Record", JOptionPane.PLAIN_MESSAGE,
                new ImageIcon("./data/icons/deleted.png"), null, null);

        UserRecords userRecords = new UserRecords();
        if (recordID != null && !recordID.isEmpty()) {
            try {
                userRecords.removeRecord(recordID, false);
                JOptionPane.showMessageDialog(this,
                        "Successfully deleted record with ID: " + recordID, "Record deleted",
                        JOptionPane.PLAIN_MESSAGE, new ImageIcon("./data/icons/deleted.png"));
            } catch (RecordNotFoundException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Record not found!",
                        JOptionPane.WARNING_MESSAGE, new ImageIcon("./data/icons/notFound.png"));
            }
        }
    }
}
