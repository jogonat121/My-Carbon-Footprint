package data;

import data.exceptions.CannotAccessDataException;
import model.Question;
import model.QuestionBank;
import model.exceptions.EmptyQuestionBankException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class QuestionsDataTest {
    private QuestionsData testQuestionsData;
    private static final String SAMPLE_QUESTIONS_PATH = "./data/testQuestions.csv";

    @BeforeEach
    void runBefore() {
        testQuestionsData = new QuestionsData();
    }

    @Test
    void questionsData() {
        assertEquals(QuestionsData.PATH, testQuestionsData.getPath());
        assertEquals("Food", testQuestionsData.getFoodQuestionBank().getCategory());
        assertEquals("Travel", testQuestionsData.getTravelQuestionBank().getCategory());
        assertEquals("Misc.", testQuestionsData.getMiscQuestionBank().getCategory());
    }

    @Test
    void testLoadQuestionsNoData() {
        try {
            List<QuestionBank> questionBanks = testQuestionsData.loadQuestions("./data/fileDoesNotExist");
            fail("CannotAccessDataException expected");
        } catch (CannotAccessDataException e) {
            // expected
        }
    }

    @Test
    void testLoadQuestionsEmptyQuestionBank() {
        List<QuestionBank> questionBanks;
        try {
            questionBanks = testQuestionsData.loadQuestions(SAMPLE_QUESTIONS_PATH);
            QuestionBank foodQuestionBank = questionBanks.get(0);
            QuestionBank travelQuestionBank = questionBanks.get(1);
            QuestionBank miscQuestionBank = questionBanks.get(2);

            assertEquals("test Food?", foodQuestionBank.getNextQuestion().getQuery());
            assertEquals("Unknown", foodQuestionBank.getNextQuestion().getCategory());
            fail("EmptyQuestionBankException expected");
        } catch (CannotAccessDataException e) {
            fail();
        } catch (EmptyQuestionBankException e) {
            // expected
        }
    }

    @Test
    void testLoadQuestions() {
        List<QuestionBank> questionBanks;
        try {
            questionBanks = testQuestionsData.loadQuestions(SAMPLE_QUESTIONS_PATH);
            QuestionBank foodQuestionBank = questionBanks.get(0);
            QuestionBank travelQuestionBank = questionBanks.get(1);
            QuestionBank miscQuestionBank = questionBanks.get(2);

            assertEquals(1, foodQuestionBank.length());
            assertEquals(2, travelQuestionBank.length());
            assertEquals(1, miscQuestionBank.length());

            assertEquals("test Food?", foodQuestionBank.getNextQuestion().getQuery());
            assertEquals(2, travelQuestionBank.getNextQuestion().getUnitFootprint());
            assertEquals("Unknown", miscQuestionBank.getNextQuestion().getCategory());
            assertEquals(5.5, travelQuestionBank.getNextQuestion().getUnitFootprint());
        } catch (CannotAccessDataException | EmptyQuestionBankException e) {
            fail();
        }
    }
}