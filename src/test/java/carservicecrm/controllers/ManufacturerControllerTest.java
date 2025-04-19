package carservicecrm.controllers;

import carservicecrm.models.Employee;
import carservicecrm.models.Manufacturer;
import carservicecrm.models.User;
import carservicecrm.models.WorkerRequest;
import carservicecrm.services.UserService;
import carservicecrm.services.WorkerRequestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ManufacturerControllerTest {

    @Mock
    private WorkerRequestService workerRequestService;

    @Mock
    private UserService userService;

    @InjectMocks
    private ManufacturerController manufacturerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveRequest() {
        String email = "test@example.com";
        String questionText = "What is the best product?";
        User user = new User();
        user.setEmail(email);
        Employee employee = new Employee();
        Manufacturer manufacturer = new Manufacturer();
        employee.setManufacturer(manufacturer);
        user.setEmployee(employee);
        when(userService.getUserByEmail(email)).thenReturn(user);

        String result = manufacturerController.saveRequest(email, questionText);

        verify(workerRequestService, times(1)).save(any(WorkerRequest.class));

        assertEquals("redirect:/profile", result);
    }

}