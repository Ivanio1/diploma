package carservicecrm.controllers;

import carservicecrm.models.*;
import carservicecrm.services.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.security.Principal;
import java.text.ParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private CarService carService;

    @Mock
    private QuestionService questionService;

    @Mock
    private OfferService offerService;

    @Mock
    private StoService stoService;


    @Mock
    private PurchaseService purchaseService;

    @Mock
    private Principal principal;

    @Mock
    private Model model;

    @Mock
    private ReviewService reviewService;

    @InjectMocks
    private UserController userController;

    private User user;
    private Car car;
    private Question question;
    private Purchase purchase;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");

        car = new Car();
        car.setId(1L);
        car.setBrand("Toyota");
        car.setModel("Camry");

        question = new Question();
        question.setUser(user);
        question.setQuestionText("What is the best car?");

        purchase = new Purchase();
        purchase.setUser(user);
        purchase.setCar(car);
    }

    @Test
    void saveQuestion_Success() {
        String result = userController.saveQuestion(user.getEmail(), question.getQuestionText());

        verify(questionService).save(any(Question.class));
        assertEquals("redirect:/profile", result);
    }

    @Test
    void saveCar_Success() throws ParseException {
        String brand = "Toyota";
        String model = "Camry";
        String creationDate = "2020";

        when(userService.getUserByEmail(user.getEmail())).thenReturn(user);

        String result = userController.saveCar(user.getEmail(), brand, model, creationDate);

        verify(carService).saveCar(any(Car.class));
        assertEquals("redirect:/user/my/cars", result);
    }

    @Test
    void deleteCar_Success() {
        Long carId = 1L;

        when(userService.getUserByEmail(user.getEmail())).thenReturn(user);
        when(carService.getCar(carId)).thenReturn(car);

        String result = userController.deleteCar(user.getEmail(), carId);

        verify(userService).removeCarFromUser(user.getId(), car);
        assertEquals("redirect:/user/my/cars", result);
    }


    @Test
    void userCars_Success() {
        Model model = mock(Model.class);

        when(userService.getUserByPrincipal(any())).thenReturn(user);
        when(userService.getUserCars(user.getId())).thenReturn(Set.of(car));

        String result = userController.usercars(model, null);

        verify(model).addAttribute("cars", Set.of(car));
        verify(model).addAttribute("user", user);
        assertEquals("user-cars", result);
    }

    @Test
    void userPurchases_Success() {
        Model model = mock(Model.class);

        when(userService.getUserByPrincipal(any())).thenReturn(user);
        when(userService.getUserPurchases(user.getId())).thenReturn(Set.of(purchase));

        String result = userController.userpurchases(model, null);

        verify(model).addAttribute("purchases", Set.of(purchase));
        verify(model).addAttribute("user", user);
        assertEquals("user-purchases", result);
    }

    @Test
    void login_ShouldAddUserAndReturnLoginView() {
        when(userService.getUserByPrincipal(principal)).thenReturn(new User());

        String result = userController.login(principal, model);

        verify(model).addAttribute("user", userService.getUserByPrincipal(principal));
        assertEquals("login", result);
    }

    @Test
    void profile_ShouldAddUserAndOffersAndReturnProfileView() {
        User user = new User();
        when(userService.getUserByPrincipal(principal)).thenReturn(user);
        when(offerService.listOffers("")).thenReturn(List.of());

        String result = userController.profile(principal, model);

        verify(model).addAttribute("user", user);
        verify(model).addAttribute("offers", offerService.listOffers(""));
        assertEquals("profile", result);
    }

    @Test
    void registrationGet_ShouldAddUserAndReturnRegistrationView() {
        when(userService.getUserByPrincipal(principal)).thenReturn(new User());

        String result = userController.registration(principal, model);

        verify(model).addAttribute("user", userService.getUserByPrincipal(principal));
        assertEquals("registration", result);
    }

    @Test
    void createUser_ShouldCreateUserAndRedirect() {
        User user = new User();
        when(userService.createUser(user)).thenReturn(true);

        String result = userController.createUser(user, model);

        assertEquals("redirect:/login", result);
    }

    @Test
    void createUser_ShouldAddErrorMessageAndReturnRegistrationViewWhenUserExists() {
        User user = new User();
        when(userService.createUser(user)).thenReturn(false);

        String result = userController.createUser(user, model);

        verify(model).addAttribute("errorMessage", "Пользователь с почтой: " + user.getEmail() + " уже существует.");
        assertEquals("registration", result);
    }

    @Test
    void userInfo_ShouldAddUserAndUserByPrincipalAndReturnUserInfoView() {
        User user = new User();
        when(userService.getUserByPrincipal(principal)).thenReturn(new User());

        String result = userController.userInfo(user, model, principal);

        verify(model).addAttribute("user", user);
        verify(model).addAttribute("userByPrincipal", userService.getUserByPrincipal(principal));
        assertEquals("user-info", result);
    }

    @Test
    void deleteProduct_ShouldCallDeleteMethodsAndRedirect() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setReviews(new HashSet<>());
        user.setPurchases(new HashSet<>());
        when(userService.getUserById(userId)).thenReturn(user);

        String result = userController.deleteProduct(userId, principal);

        verify(userService).deleteUser(userId);
        assertEquals("redirect:/admin", result);
    }

    @Test
    void adminusers_ShouldAddReviewsAndUserAndReturnReviewsView() {
        when(reviewService.list()).thenReturn(List.of());
        when(userService.getUserByPrincipal(principal)).thenReturn(new User());

        String result = userController.adminusers(model, principal);

        verify(model).addAttribute("reviews", reviewService.list());
        verify(model).addAttribute("user", userService.getUserByPrincipal(principal));
        assertEquals("reviews", result);
    }

    @Test
    void userCreatePurchaseFrom_ShouldAddAttributesAndReturnPurchaseFormView() {
        User user = new User();
        user.setId(1L);

        when(userService.getUserByPrincipal(principal)).thenReturn(user);
        when(userService.getUserCars(user.getId())).thenReturn(Set.of(new Car()));
        when(offerService.listOffers("")).thenReturn(List.of());
        when(stoService.list()).thenReturn(List.of());

        String result = userController.userCreatePurchaseFrom(model, principal);

        verify(model).addAttribute("cars", userService.getUserCars(user.getId()));
        verify(model).addAttribute("offers", offerService.listOffers(""));
        verify(model).addAttribute("stos", stoService.list());
        verify(model).addAttribute("user", user);
        assertEquals("purchase-form", result);
    }

    @Test
    void userCreatePurchaseFrom_ShouldCreatePurchaseAndRedirect() {
        String email = "test@example.com";
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("offer1", "value1");
        form.add("sto", "testSto");
        form.add("car", "1");
        Long carId = 1L;

        User user = new User();
        user.setEmail(email);

        Car car = new Car();

        when(userService.getUserByEmail(email)).thenReturn(user);
        when(carService.getCar(carId)).thenReturn(car);
        when(offerService.getOfferByName("offer1")).thenReturn(new Offer());

        String result = userController.userCreatePurchaseFrom(email, form, "testSto", carId);

        verify(purchaseService).savePurchase(any(Purchase.class));

        assertEquals("redirect:/", result);
    }


}