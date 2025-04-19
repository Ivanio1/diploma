package carservicecrm.controllers;

import carservicecrm.models.Employee;
import carservicecrm.models.User;
import carservicecrm.models.Worker;
import carservicecrm.models.WorkerRequest;
import carservicecrm.services.PurchaseService;
import carservicecrm.services.UserService;
import carservicecrm.services.WorkerRequestService;
import carservicecrm.services.WorkerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WithMockUser(roles = {"WORKER", "ADMIN"})
class WorkerControllerTest {

    @Mock
    private WorkerRequestService workerRequestService;

    @Mock
    private UserService userService;

    @Mock
    private PurchaseService purchaseService;

    @Mock
    private WorkerService workerService;

    @Mock
    private Principal principal;

    @Mock
    private Model model;

    @InjectMocks
    private WorkerController workerController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(workerController).build();
        when(principal.getName()).thenReturn("test@test.com");
    }

    @Test
    void testPanel() throws Exception {
        User user = new User();
        Worker worker = new Worker();
        worker.setId(1L);
        Employee employee = new Employee();
        employee.setWorker(worker);
        user.setEmployee(employee);
        when(userService.getUserByPrincipal(principal)).thenReturn(user);
        when(purchaseService.listAllocByWorker(1L)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/worker/panel").principal(principal))
                .andExpect(status().isOk())
                .andExpect(view().name("worker"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("purchases"));
    }

    @Test
    void testSaveRequest() throws Exception {
        String email = "test@test.com";
        String questionText = "test question";
        Worker worker = new Worker();
        Employee employee = new Employee();
        employee.setWorker(worker);
        worker.setId(1L);
        User user = new User();
        user.setEmail(email);
        user.setEmployee(employee);
        when(userService.getUserByEmail(email)).thenReturn(user);

        mockMvc.perform(post("/worker/add/request")
                        .param("email", email)
                        .param("questionText", questionText))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile"));

        verify(workerRequestService, times(1)).save(any(WorkerRequest.class));
    }

    @Test
    void testProcess() throws Exception {
        Long id = 1L;

        mockMvc.perform(post("/worker/process/{id}", id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/worker/panel"));

        verify(workerService, times(1)).update_purchase_status_to_in_process(id);
    }

    @Test
    public void testDone() throws Exception {
        Long id = 1L;

        String result = workerController.done(model, id);

        verify(workerService).update_purchase_status_to_done(id);
        assertEquals("redirect:/worker/panel", result);
    }

    @Test
    public void testWaiting() throws Exception {
        Long id = 2L;

        String result = workerController.waiting(model, id);

        verify(workerService).update_purchase_status_to_waiting(id);
        assertEquals("redirect:/worker/panel", result);
    }
}