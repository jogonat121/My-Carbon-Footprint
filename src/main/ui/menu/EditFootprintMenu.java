package ui.menu;

import data.FootprintRecord;
import data.exceptions.CannotAccessDataException;
import model.Footprint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Represents a menu for editing record
public class EditFootprintMenu extends JFrame implements ActionListener {
    private final Footprint foodFootprint;
    private final Footprint travelFootprint;
    private final Footprint miscFootprint;
    private final JFrame parentFrame;
    private JTextField foodTextField;
    private JTextField travelTextField;
    private JTextField miscTextField;

    // EFFECTS: constructs and runs a JFrame with title edit footprint with JPanels added
    public EditFootprintMenu(FootprintRecord fr, JFrame parentFrame) {
        super("Edit Footprint");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(parentFrame.getWidth(), parentFrame.getHeight());
        setLocationRelativeTo(parentFrame);

        this.parentFrame = parentFrame;
        foodFootprint = fr.getFoodFootprint();
        travelFootprint = fr.getTravelFootprint();
        miscFootprint = fr.getMiscFootprint();

        add(getEditPanel(), BorderLayout.CENTER);
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
    // EFFECTS: constructs and returns a JPanel for editing footprint
    private JPanel getEditPanel() {
        JPanel editPanel = new JPanel(new GridLayout(4, 2));
        JLabel foodLabel = new JLabel("Food Footprint:");
        foodTextField = new JTextField(String.valueOf(foodFootprint.getValue()));
        JLabel travelLabel = new JLabel("Travel Footprint:");
        travelTextField = new JTextField(String.valueOf(travelFootprint.getValue()));
        JLabel miscLabel = new JLabel("Misc. Footprint:");
        miscTextField = new JTextField(String.valueOf(miscFootprint.getValue()));
        JButton submitButton = createButton("Submit", "submit");
        editPanel.add(foodLabel);
        editPanel.add(foodTextField);
        editPanel.add(travelLabel);
        editPanel.add(travelTextField);
        editPanel.add(miscLabel);
        editPanel.add(miscTextField);
        editPanel.add(submitButton);
        return editPanel;
    }

    // EFFECTS: constructs and returns a JButton with given text and command
    private JButton createButton(String buttonText, String actionCommand) {
        JButton button = new JButton(buttonText);
        button.setActionCommand(actionCommand);
        button.addActionListener(this);
        return button;
    }

    // EFFECTS: listens to buttons clicked and performs corresponding actions
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if ("submit".equals(e.getActionCommand())) {
                editFootprints();
            } else {
                setVisible(false);
            }
        } catch (ClassCastException ex) {
            ((LoadFootprintMenu) parentFrame).refresh();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // REQUIRES: foodTextField.getText(), travelTextField.getText(), miscTextField.getText()
    //   to be able to parse as Double
    // MODIFIES: this
    // EFFECTS: updates the footprints with new values entered by the user;
    // throws CannotAccessDataException if error occurs while accessing data
    private void editFootprints() throws CannotAccessDataException {
        foodFootprint.setValue(Double.parseDouble(foodTextField.getText()));
        travelFootprint.setValue(Double.parseDouble(travelTextField.getText()));
        miscFootprint.setValue(Double.parseDouble(miscTextField.getText()));
        setVisible(false);
        ((CalculateFootprintMenu) parentFrame).refresh();
    }
}
