package model;

import model.exceptions.EmptyQuestionBankException;
import model.exceptions.InvalidNumberOfAnswersException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class QuestionBankTest {
    private QuestionBank testQuestionBank;

    @BeforeEach
    void runBefore() {
        testQuestionBank = new QuestionBank("Test");
    }

    @Test
    void testQuestionBank() {
        assertTrue(testQuestionBank.isEmpty());
        assertTrue(testQuestionBank.getQuestionBank().isEmpty());
        assertEquals("Test", testQuestionBank.getCategory());
    }

    @Test
    void length() {
        Question testQuestion1 = new Question("Test 1", 0, "Test");
        Question testQuestion2 = new Question("Test 2", 0, "Test");

        assertEquals(0, testQuestionBank.length());
        testQuestionBank.addQuestion(testQuestion1);
        assertEquals(1, testQuestionBank.length());
        testQuestionBank.addQuestion(testQuestion2);
        assertEquals(2, testQuestionBank.length());
        testQuestionBank.addQuestion(testQuestion1);
        assertEquals(3, testQuestionBank.length());
    }

    @Test
    void testGetNextQuestionEmpty() {
        Question testQuestion = new Question("Test ?", 0, "Test");
        try {
            assertEquals(testQuestion, testQuestionBank.getNextQuestion());
            fail("EmptyQuestionBankException expected");
        } catch (EmptyQuestionBankException e) {
            // expected
        }
    }

    @Test
    void testGetNextQuestion() {
        Question testQuestion = new Question("Test ?", 0, "Test");
        testQuestionBank.addQuestion(testQuestion);
        assertEquals(1, testQuestionBank.length());

        try {
            assertEquals(testQuestion, testQuestionBank.getNextQuestion());
        } catch (EmptyQuestionBankException e) {
            fail();
        }
        assertEquals(0, testQuestionBank.length());
    }

    @Test
    void testIsEmpty() {
        assertTrue(testQuestionBank.isEmpty());

        Question testQuestion = new Question("Test ?", 0, "Test");
        testQuestionBank.addQuestion(testQuestion);
        assertFalse(testQuestionBank.isEmpty());
    }

    @Test
    void testAddQuestion() {
        assertTrue(testQuestionBank.isEmpty());

        Question testQuestion = new Question("Test ?", 0, "Test");
        testQuestionBank.addQuestion(testQuestion);
        assertFalse(testQuestionBank.isEmpty());
        assertEquals(1, testQuestionBank.length());

        testQuestionBank.addQuestion(testQuestion);
        assertFalse(testQuestionBank.isEmpty());
        assertEquals(2, testQuestionBank.length());
    }

    @Test
    void testCalculateFootprintEmptyQuestionBank() {
        List<Double> testValues = new ArrayList<>();
        testValues.add(1.5);

        try {
            assertEquals(0, testQuestionBank.calculateFootprint(testValues).getValue());
            assertEquals("Test", testQuestionBank.calculateFootprint(testValues).getCategory());
            fail("EmptyQuestionBank expected");
        } catch (InvalidNumberOfAnswersException ignored) {
        } catch (EmptyQuestionBankException e) {
            // expected
        }
        assertTrue(testQuestionBank.isEmpty());
    }

    @Test
    void testCalculateFootprintInvalidValues() {
        List<Double> testValues = new ArrayList<>();
        Question testQuestion = new Question("Test ?", 0, "Test");
        testQuestionBank.addQuestion(testQuestion);

        try {
            assertEquals(0, testQuestionBank.calculateFootprint(testValues).getValue());
            assertEquals("Test", testQuestionBank.calculateFootprint(testValues).getCategory());
            assertTrue(testQuestionBank.isEmpty());
            fail("InvalidNumberOfAnswersException expected");
        } catch (InvalidNumberOfAnswersException e) {
            // expected
        } catch (EmptyQuestionBankException e) {
            fail();
        }
    }

    @Test
    void testCalculateFootprintTypical() {
        List<Double> testValues;
        Footprint resultFootprint;
        try {
            Question testQuestion = new Question("Test ?", 10, "Test");
            testQuestionBank.addQuestion(testQuestion);
            testValues = new ArrayList<>();
            testValues.add(5.5);
            resultFootprint = testQuestionBank.calculateFootprint(testValues);
            assertEquals(55, resultFootprint.getValue());
            assertEquals("Test", resultFootprint.getCategory());
            assertTrue(testQuestionBank.isEmpty());

            Question testQuestion2 = new Question("Test2 ?", 2, "Test");
            testQuestionBank.addQuestion(testQuestion);
            testQuestionBank.addQuestion(testQuestion2);
            testQuestionBank.addQuestion(testQuestion);
            testValues = new ArrayList<>();
            testValues.add(0.0);
            testValues.add(0.55);
            testValues.add(10.0);
            resultFootprint = testQuestionBank.calculateFootprint(testValues);
            assertEquals(101.1, resultFootprint.getValue());
            assertEquals("Test", resultFootprint.getCategory());
            assertTrue(testQuestionBank.isEmpty());
        } catch (InvalidNumberOfAnswersException | EmptyQuestionBankException e) {
            fail();
        }

    }

}
