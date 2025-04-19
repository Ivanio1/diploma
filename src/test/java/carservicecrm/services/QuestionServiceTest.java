package carservicecrm.services;

import carservicecrm.models.Question;
import carservicecrm.repositories.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QuestionServiceTest {

    @Mock
    private QuestionRepository questionRepository;

    @InjectMocks
    private QuestionService questionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveQuestionSuccess() {
        Question question = new Question();
        question.setId(1L);
        question.setQuestionText("Sample Question");

        boolean result = questionService.save(question);

        assertTrue(result);
        verify(questionRepository, times(1)).save(question);
    }

    @Test
    void testSaveQuestionFailure() {
        Question question = new Question();

        doThrow(new RuntimeException("Database error")).when(questionRepository).save(question);

        boolean result = questionService.save(question);

        assertFalse(result);
        verify(questionRepository, times(1)).save(question);
    }

    @Test
    void testListQuestions() {
        List<Question> expectedQuestions = new ArrayList<>();
        Question question = new Question();
        question.setId(1L);
        question.setQuestionText("Question 1");
        expectedQuestions.add(question);

        Question question1 = new Question();
        question1.setId(1L);
        question1.setQuestionText("Question 1");
        expectedQuestions.add(question1);

        when(questionRepository.findAllQuestions()).thenReturn(expectedQuestions);

        List<Question> actualQuestions = questionService.list();

        assertEquals(expectedQuestions.size(), actualQuestions.size());
        assertIterableEquals(expectedQuestions, actualQuestions);
        verify(questionRepository, times(1)).findAllQuestions();
    }

    @Test
    void testDeleteQuestionExists() {
        Question question = new Question();
        question.setId(1L);

        when(questionRepository.findQuestionById(1L)).thenReturn(question);

        questionService.deleteQuestion(1L);

        verify(questionRepository, times(1)).deleteQuestionById(1L);
        verify(questionRepository, times(1)).findQuestionById(1L);
    }

    @Test
    void testDeleteQuestionNotFound() {
        when(questionRepository.findQuestionById(1L)).thenReturn(null);

        questionService.deleteQuestion(1L);

        verify(questionRepository, times(0)).deleteQuestionById(1L);
        verify(questionRepository, times(1)).findQuestionById(1L);
    }
}