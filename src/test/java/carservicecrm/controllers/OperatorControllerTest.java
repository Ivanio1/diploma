package carservicecrm.controllers;

import carservicecrm.models.User;
import carservicecrm.services.QuestionService;
import carservicecrm.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OperatorControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private QuestionService questionService;

    @Mock
    private Model model;

    @Mock
    private Principal principal;

    @InjectMocks
    private OperatorController operatorController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAdmin() {
        User user = new User();
        when(userService.getUserByPrincipal(principal)).thenReturn(user);
        when(questionService.list()).thenReturn(Collections.emptyList());

        String viewName = operatorController.admin(model, principal);

        verify(model).addAttribute("user", user);
        verify(model).addAttribute("questions", Collections.emptyList());
        assertEquals("operator", viewName);
    }

    @Test
    void testDeleteQuestion() {
        Long questionId = 1L;

        String redirectView = operatorController.deleteQuestion(questionId);

        verify(questionService).deleteQuestion(questionId);
        assertEquals("redirect:/operator/panel", redirectView);
    }
}