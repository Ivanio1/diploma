package carservicecrm.controllers;

import carservicecrm.controllers.ErrorController;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ErrorControllerTest {

    @InjectMocks
    private ErrorController errorController;

    @Mock
    private Model model;

    public ErrorControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void handleErrors_ShouldAddMessageAndReturnErrorView() {
        String result = errorController.handleErrors(model);

        verify(model).addAttribute("message", "Произошла ошибка!");
        assertEquals("error", result);
    }

    @Test
    void showErrorPage_ShouldReturnErrorView() {
        String result = errorController.showErrorPage();

        assertEquals("error", result);
    }
}