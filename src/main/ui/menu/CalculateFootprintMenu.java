package ui.menu;

import data.FootprintRecord;
import data.QuestionsData;
import data.UserRecords;
import data.exceptions.CannotAccessDataException;
import model.Footprint;
import model.Question;
import model.QuestionBank;
import model.exceptions.EmptyQuestionBankException;
import model.exceptions.InvalidNumberOfAnswersException;
import ui.utils.ChartManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

import static ui.MyCarbonFootprint.UNITS;

// Represents a menu for calculating footprint
public class CalculateFootprintMenu extends JFrame implements ActionListener {
    private List<QuestionBank> questionBanks;
    private LinkedList<Question> questions;
    private int numFoodQuestions;
    private int numTravelQuestions;
    private int numMiscQuestions;
    private List<Double> answers;
    private JLabel questionLabel;
    private JTextField answerField;
    private JPanel resultPanel;
    private JPanel mainPanel;
    private JLabel foodFootprintLabel;
    private JLabel travelFootprintLabel;
    private JLabel miscFootprintLabel;
    private JLabel totalFootprintLabel;
    private FootprintRecord footprintRecord;
    private Footprint foodFootprint;
    private Footprint travelFootprint;
    private Footprint miscFootprint;

    // EFFECTS: constructs and runs a JFrame with title calculate footprint with JPanels added
    // throws CannotAccessDataException if error occurs while accessing data;
    // throws EmptyQuestionBankException if there are no questions to load
    public CalculateFootprintMenu(JFrame parentFrame)
            throws CannotAccessDataException, EmptyQuestionBankException {
        super("Calculate Footprint");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(parentFrame.getWidth(), parentFrame.getHeight());
        setLocationRelativeTo(parentFrame);

        add(getQnaPanel(), BorderLayout.NORTH);
        add(getMainPanel(), BorderLayout.CENTER);
        add(getFooterPanel(), BorderLayout.SOUTH);

        setVisible(true);
        init();
    }

    // MODIFIES: this
    // EFFECTS: constructs and returns a JPanel for questions and answers
    private JPanel getQnaPanel() {
        JPanel qnaPanel = new JPanel();
        qnaPanel.setLayout(new BoxLayout(qnaPanel, BoxLayout.Y_AXIS));
        questionLabel = new JLabel();
        answerField = new JTextField(10);
        JButton submitButton = createButton("Submit", "submit");
        qnaPanel.add(questionLabel);
        qnaPanel.add(answerField);
        qnaPanel.add(submitButton);

        return qnaPanel;
    }

    // MODIFIES: this
    // EFFECTS: constructs and returns a JPanel for the resultant footprint
    private JPanel getResultPanel() {
        JPanel footprintPanel = new JPanel(new GridLayout(4, 1));
        foodFootprintLabel = new JLabel();
        travelFootprintLabel = new JLabel();
        miscFootprintLabel = new JLabel();
        totalFootprintLabel = new JLabel();
        footprintPanel.add(foodFootprintLabel);
        footprintPanel.add(travelFootprintLabel);
        footprintPanel.add(miscFootprintLabel);
        footprintPanel.add(totalFootprintLabel);

        resultPanel = new JPanel(new GridLayout(1, 2));
        resultPanel.add(footprintPanel);
        return resultPanel;
    }

    // MODIFIES: this
    // EFFECTS: constructs and returns the main JPanel with resultant panel and one extra panel space
    private JPanel getMainPanel() {
        mainPanel = new JPanel(new GridLayout(2, 1));
        mainPanel.add(getResultPanel());
        return mainPanel;
    }

    // EFFECTS: constructs and returns a JPanel for the footer
    private JPanel getFooterPanel() {
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton backButton = createButton("Back to previous menu", "back");
        footerPanel.add(backButton);
        return footerPanel;
    }

    // EFFECTS: constructs and returns a JButton with given text and command
    private JButton createButton(String buttonText, String actionCommand) {
        JButton button = new JButton(buttonText);
        button.setActionCommand(actionCommand);
        button.addActionListener(this);
        return button;
    }

    // MODIFIES: this
    // EFFECTS: initializes the questions banks and loads questions from data;
    // throws CannotAccessDataException if error occurs while reading data;
    // throws EmptyQuestionBankException if there are no questions to load
    private void init() throws CannotAccessDataException, EmptyQuestionBankException {
        try {
            QuestionsData questionsData = new QuestionsData();
            questionBanks = questionsData.loadQuestions();
            loadQuestions();
        } catch (InvalidNumberOfAnswersException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Error: InvalidNumberOfAnswersException", JOptionPane.ERROR_MESSAGE);
            init();
        }
    }

    // MODIFIES: this
    // EFFECTS: loads questions this.questions and asks them
    // throws InvalidNumberOfAnswersException if no. of answers > no. of questions;
    // throws EmptyQuestionBankException if there are 0 questions in question bank;
    // throws CannotAccessDataException if error occurs while accessing data
    private void loadQuestions()
            throws InvalidNumberOfAnswersException, CannotAccessDataException, EmptyQuestionBankException {
        QuestionBank foodQuestionBank = questionBanks.get(0);
        QuestionBank travelQuestionBank = questionBanks.get(1);
        QuestionBank miscQuestionBank = questionBanks.get(2);
        questions = new LinkedList<>();
        questions.addAll(questionBanks.get(0).getQuestionBank());
        questions.addAll(questionBanks.get(1).getQuestionBank());
        questions.addAll(questionBanks.get(2).getQuestionBank());
        numFoodQuestions = foodQuestionBank.length();
        numTravelQuestions = travelQuestionBank.length();
        numMiscQuestions = miscQuestionBank.length();

        askQuestions();
    }

    // REQUIRES: answers.size() > 0
    // MODIFIES: this
    // EFFECTS: displays the calculated footprint assigned to relevant categories;
    // throws InvalidNumberOfAnswersException if no. of answers > no. of questions;
    // throws EmptyQuestionBankException if there are 0 questions in question bank;
    // throws CannotAccessDataException if error occurs while accessing data
    private void processAnswers() throws
            InvalidNumberOfAnswersException, EmptyQuestionBankException, CannotAccessDataException {
        List<Double> foodValues = answers.subList(0, numFoodQuestions);
        List<Double> travelValues = answers.subList(numFoodQuestions, numFoodQuestions + numTravelQuestions);
        List<Double> miscValues = answers.subList(travelValues.size(), travelValues.size() + numMiscQuestions);
        foodFootprint = questionBanks.get(0).calculateFootprint(foodValues);
        travelFootprint = questionBanks.get(1).calculateFootprint(travelValues);
        miscFootprint = questionBanks.get(2).calculateFootprint(miscValues);
        displayFootprint();
    }

    // MODIFIES: this
    // EFFECTS: displays the footprint values and the footprint resultant panel;
    // throws CannotAccessDataException if error occurs while accessing data
    private void displayFootprint() throws CannotAccessDataException {
        setFootprintValues();
        resultPanel.add(getChart());
        mainPanel.add(getButtonsPanel());
    }

    // EFFECTS: constructs and returns footprint utilities buttons panel
    private JPanel getButtonsPanel() {
        JPanel buttonsPanel = new JPanel(new GridLayout(1, 3));
        JButton editButton = createButton("Edit Footprint", "edit");
        JButton saveButton = createButton("Save Footprint", "save");
        JButton contributeButton = createButton("Contribute to our data", "contribute");
        buttonsPanel.add(editButton);
        buttonsPanel.add(saveButton);
        buttonsPanel.add(contributeButton);
        return buttonsPanel;
    }

    // EFFECTS: constructs and returns a user vs average footprint chart;
    // throws CannotAccessDataException if error occurs while accessing data
    private JPanel getChart() throws CannotAccessDataException {
        List<Double> foodValues = new ArrayList<>();
        List<Double> travelValues = new ArrayList<>();
        List<Double> miscValues = new ArrayList<>();
        List<Double> totalValues = new ArrayList<>();
        UserRecords userRecords = new UserRecords();
        List<Double> averages = userRecords.getAverages();

        foodValues.add(foodFootprint.getValue());
        travelValues.add(travelFootprint.getValue());
        miscValues.add(miscFootprint.getValue());
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

        return new ChartManager(chartData);
    }

    // MODIFIES: this
    // EFFECTS: updates the footprint values
    private void setFootprintValues() {
        Double[] footprintValues = new Double[]
                {foodFootprint.getValue(), travelFootprint.getValue(), miscFootprint.getValue()};
        String uniqueID = UUID.randomUUID().toString();
        footprintRecord = new FootprintRecord(uniqueID, foodFootprint, travelFootprint, miscFootprint);
        double totalFootprintValue = Math.round((footprintValues[0] + footprintValues[1] + footprintValues[2]) * 1000)
                / 1000.0;

        foodFootprintLabel.setText("Your Food Footprint is: " + footprintValues[0] + UNITS);
        travelFootprintLabel.setText("Your Travel Footprint is: " + footprintValues[1] + UNITS);
        miscFootprintLabel.setText("Your Misc. Footprint is: " + footprintValues[2] + UNITS);
        totalFootprintLabel.setText("Your Total Footprint is: " + totalFootprintValue + UNITS);
    }

    // MODIFIES: this
    // EFFECTS: updates the user vs average footprint chart
    private void updateChart() throws CannotAccessDataException {
        resultPanel.remove(1);
        resultPanel.add(getChart());
    }

    // MODIFIES: this
    // EFFECTS: displays and asks the questions; initialises this.answers;
    // throws InvalidNumberOfAnswersException if no. of answers > no. of questions;
    // throws EmptyQuestionBankException if there are 0 questions in question bank;
    // throws CannotAccessDataException if error occurs while accessing data
    private void askQuestions()
            throws InvalidNumberOfAnswersException, CannotAccessDataException, EmptyQuestionBankException {
        answers = new ArrayList<>();
        askNextQuestion();
    }

    // MODIFIES: this
    // EFFECTS: displays and asks the next question in this.questions;
    // throws InvalidNumberOfAnswersException if no. of answers > no. of questions;
    // throws EmptyQuestionBankException if there are 0 questions in question bank;
    // throws CannotAccessDataException if error occurs while accessing data
    private void askNextQuestion()
            throws InvalidNumberOfAnswersException, CannotAccessDataException, EmptyQuestionBankException {
        if (questions.isEmpty()) {
            processAnswers();
        }
        questionLabel.setText(questions.removeFirst().getQuery());
        answerField.setText("");
    }

    // MODIFIES: data
    // EFFECTS: adds the footprint record to user records data file, and displays the unique ID of that record;
    // throws CannotAccessDataException if error occurs while writing data;
    private void addToRecords(FootprintRecord footprintRecord) throws CannotAccessDataException {
        if (footprintRecord.saveData()) {
            JTextField message = new JTextField("Successfully recorded!"
                    + "\nPlease remember the unique ID for your record which is " + footprintRecord.getId());
            message.setEditable(false);
            JOptionPane.showMessageDialog(this, message, "Thank you for your contribution",
                    JOptionPane.PLAIN_MESSAGE);
        }
    }

    // MODIFIES: data
    // EFFECTS: exports the footprint record as a JSON file
    private void exportRecord(FootprintRecord footprintRecord) {
        String fileName = JOptionPane.showInputDialog(this,
                "Enter name of the file to save as:");
        if (fileName != null && !fileName.isEmpty()) {
            try {
                footprintRecord.exportFile(fileName);
                JOptionPane.showMessageDialog(this,
                        "Successfully saved to ./data/" + fileName + ".json",
                        "Footprint Saved", JOptionPane.PLAIN_MESSAGE);
            } catch (CannotAccessDataException e) {
                JOptionPane.showMessageDialog(this, "Cannot write data to file",
                        "Cannot Access Data Exception", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    // EFFECTS: listens to buttons clicked and performs corresponding actions
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            switch (e.getActionCommand()) {
                case "submit":
                    processAnswer();
                    askNextQuestion();
                    break;
                case "edit":
                    new EditFootprintMenu(footprintRecord, this);
                    break;
                case "save":
                    exportRecord(footprintRecord);
                    break;
                case "contribute":
                    addToRecords(footprintRecord);
                    break;
                default:
                    setVisible(false);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // MODIFIES: this
    // EFFECTS: process answer for current question and add to this.answers
    private void processAnswer() {
        double answer = Double.parseDouble(answerField.getText());
        answers.add(answer);
    }

    // EFFECTS: updates the resultant footprint values and user vs average footprint chart;
    // throws CannotAccessDataException if error occurs while accessing data
    public void refresh() throws CannotAccessDataException {
        setFootprintValues();
        updateChart();
    }
}
