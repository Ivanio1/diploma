package carservicecrm.controllers;

import carservicecrm.models.Purchase;
import carservicecrm.models.User;
import carservicecrm.repositories.PurchaseRepository;
import carservicecrm.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AnalyticsControllerTest {

    @InjectMocks
    private AnalyticsController analyticsController;

    @Mock
    private UserService userService;

    @Mock
    private PurchaseRepository purchaseRepository;

    @Mock
    private Model model;

    @Mock
    private Principal principal;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAnalytics_ShouldAddUserAndMonthlyCounts() {
        Purchase purchase1 = new Purchase();
        purchase1.setCreatedat(LocalDateTime.of(2023, 1, 15, 10, 0));

        Purchase purchase2 = new Purchase();
        purchase2.setCreatedat(LocalDateTime.of(2023, 1, 20, 10, 0));

        Purchase purchase3 = new Purchase();
        purchase3.setCreatedat(LocalDateTime.of(2023, 2, 5, 10, 0));

        List<Purchase> purchases = Arrays.asList(purchase1, purchase2, purchase3);

        when(purchaseRepository.findAll()).thenReturn(purchases);
        when(userService.getUserByPrincipal(principal)).thenReturn(new User());

        String result = analyticsController.getAnalytics(model, principal);

        verify(model).addAttribute("user", userService.getUserByPrincipal(principal));
        verify(model).addAttribute("monthlyCounts", new int[]{2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
        assertEquals("monthly", result);
    }

    @Test
    void getWeeklyAnalytics_ShouldAddUserAndWeeklyCounts() {
        Purchase purchase1 = new Purchase();
        purchase1.setCreatedat(LocalDateTime.of(2023, 1, 1, 10, 0));

        Purchase purchase2 = new Purchase();
        purchase2.setCreatedat(LocalDateTime.of(2023, 1, 8, 10, 0));

        Purchase purchase3 = new Purchase();
        purchase3.setCreatedat(LocalDateTime.of(2023, 1, 15, 10, 0));

        List<Purchase> purchases = Arrays.asList(purchase1, purchase2, purchase3);

        when(purchaseRepository.findAll()).thenReturn(purchases);
        when(userService.getUserByPrincipal(principal)).thenReturn(new User());

        String result = analyticsController.getWeeklyAnalytics(model, principal);

        verify(model).addAttribute("user", userService.getUserByPrincipal(principal));
        assertEquals("weekly", result);
    }

    @Test
    void getDailyAnalytics_ShouldAddUserAndDailyCounts() {
        Purchase purchase1 = new Purchase();
        purchase1.setCreatedat(LocalDateTime.of(2023, 1, 1, 10, 0));

        Purchase purchase2 = new Purchase();
        purchase2.setCreatedat(LocalDateTime.of(2023, 1, 1, 11, 0));

        Purchase purchase3 = new Purchase();
        purchase3.setCreatedat(LocalDateTime.of(2023, 1, 2, 10, 0));

        List<Purchase> purchases = Arrays.asList(purchase1, purchase2, purchase3);

        when(purchaseRepository.findAll()).thenReturn(purchases);
        when(userService.getUserByPrincipal(principal)).thenReturn(new User());

        String result = analyticsController.getDailyAnalytics(model, principal);

        verify(model).addAttribute("user", userService.getUserByPrincipal(principal));
        assertEquals("daily", result);
    }
}