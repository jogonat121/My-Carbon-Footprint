package data;

import model.Question;
import model.QuestionBank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    void testLoadQuestions() {
        List<QuestionBank> questionBanks = testQuestionsData.loadQuestions(SAMPLE_QUESTIONS_PATH);
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
    }
}