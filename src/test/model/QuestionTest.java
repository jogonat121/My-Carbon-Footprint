package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QuestionTest {
    private Question testQuestionFood;
    private Question testQuestionTravel;
    private Question testQuestionZero;

    @BeforeEach
    void runBefore() {
        testQuestionFood = new Question("test Food?", 10, "Food");
        testQuestionTravel = new Question("test Travel?", 100, "Travel");
        testQuestionZero = new Question("Am I a test?", 0, "Misc.");
    }

    @Test
    void testQuestion() {
        assertEquals("test Food?", testQuestionFood.getQuery());
        assertEquals(10, testQuestionFood.getUnitFootprint());
        assertEquals("Food", testQuestionFood.getCategory());
        assertEquals(100, testQuestionTravel.getUnitFootprint());
        assertEquals("Misc.", testQuestionZero.getCategory());
        assertEquals("Am I a test?", testQuestionZero.getQuery());
    }

    @Test
    void testCalculateFootprintZero() {
        assertEquals(0, testQuestionZero.calculateFootprint(1));
        assertEquals(0, testQuestionZero.calculateFootprint(101.5));
        assertEquals(0, testQuestionTravel.calculateFootprint(0));
    }

    @Test
    void testCalculateFootprint() {
        assertEquals(400, testQuestionTravel.calculateFootprint(4));
        assertEquals(5.5, testQuestionFood.calculateFootprint(0.55));
        assertEquals(41.21, testQuestionFood.calculateFootprint(4.121));
    }
}
