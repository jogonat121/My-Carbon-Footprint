package model;

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
    void testGetNextQuestion() {
        Question testQuestion = new Question("Test ?", 0, "Test");
        testQuestionBank.addQuestion(testQuestion);
        assertEquals(1, testQuestionBank.length());
        assertEquals(testQuestion, testQuestionBank.getNextQuestion());
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
    void testCalculateFootprintEmpty() {
        List<Double> testValues = new ArrayList<>();
        testValues.add(1.5);

        assertEquals(0, testQuestionBank.calculateFootprint(testValues).getValue());
        assertEquals("Test", testQuestionBank.calculateFootprint(testValues).getCategory());
        assertTrue(testQuestionBank.isEmpty());
    }

    @Test
    void testCalculateFootprintTypical() {
        List<Double> testValues = new ArrayList<>();

        Question testQuestion = new Question("Test ?", 10, "Test");
        testQuestionBank.addQuestion(testQuestion);
        testValues.add(5.5);
        assertEquals(55, testQuestionBank.calculateFootprint(testValues).getValue());
        assertEquals("Test", testQuestionBank.calculateFootprint(testValues).getCategory());
        assertTrue(testQuestionBank.isEmpty());

        Question testQuestion2 = new Question("Test2 ?", 2, "Test");
        testQuestionBank.addQuestion(testQuestion);
        testQuestionBank.addQuestion(testQuestion2);
        testQuestionBank.addQuestion(testQuestion);
        testValues.add(0.0);
        testValues.add(0.55);
        assertEquals(60.5, testQuestionBank.calculateFootprint(testValues).getValue());
        assertEquals("Test", testQuestionBank.calculateFootprint(testValues).getCategory());
        assertTrue(testQuestionBank.isEmpty());

    }

}
