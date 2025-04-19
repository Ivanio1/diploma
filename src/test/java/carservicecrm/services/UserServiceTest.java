package carservicecrm.services;

import carservicecrm.models.Car;
import carservicecrm.models.Purchase;
import carservicecrm.models.User;
import carservicecrm.models.enums.Role;
import carservicecrm.repositories.CarRepository;
import carservicecrm.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.Principal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setActive(true);
        user.setRoles(new HashSet<>(Collections.singletonList(Role.ROLE_USER)));
    }

    @Test
    void createUser_Success() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(null);
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");

        boolean result = userService.createUser(user);

        assertTrue(result);
        verify(userRepository).save(user);
        assertEquals("encodedPassword", user.getPassword());
    }

    @Test
    void createUser_UserAlreadyExists() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);

        boolean result = userService.createUser(user);

        assertFalse(result);
        verify(userRepository, never()).save(user);
    }

    @Test
    void list() {
        List<User> users = Arrays.asList(user);
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.list();

        assertEquals(users, result);
    }

    @Test
    void listClients() {
        User client = new User();
        client.setRoles(new HashSet<>(Collections.singletonList(Role.ROLE_USER)));
        when(userRepository.findAll()).thenReturn(Arrays.asList(user, client));

        List<User> result = userService.listClients();

        assertEquals(2, result.size());
        assertTrue(result.contains(client));
    }

    @Test
    void banUser_Ban() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.banUser(1L);

        assertFalse(user.isActive());
        verify(userRepository).save(user);
    }

    @Test
    void banUser_Unban() {
        user.setActive(false);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.banUser(1L);

        assertTrue(user.isActive());
        verify(userRepository).save(user);
    }

    @Test
    void changeUserRoles() {
        Map<String, String> rolesMap = new HashMap<>();
        rolesMap.put("ROLE_ADMIN", "true");

        when(userRepository.save(any(User.class))).thenReturn(user);

        userService.changeUserRoles(user, rolesMap);

        assertTrue(user.getRoles().contains(Role.ROLE_ADMIN));
        verify(userRepository).save(user);
    }

    @Test
    void getUserByPrincipal_NullPrincipal() {
        User result = userService.getUserByPrincipal(null);
        assertNotNull(result);
    }

    @Test
    void getUserByPrincipal_ValidPrincipal() {
        Principal principal = () -> "test@example.com";
        when(userRepository.findByEmail(principal.getName())).thenReturn(user);

        User result = userService.getUserByPrincipal(principal);

        assertEquals(user, result);
    }

    @Test
    void getUserByEmail() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(user);

        User result = userService.getUserByEmail("test@example.com");

        assertEquals(user, result);
    }

    @Test
    void getUserById() {
        when(userRepository.findUserById(1L)).thenReturn(user);

        User result = userService.getUserById(1L);

        assertEquals(user, result);
    }

    @Test
    void deleteUser_UserExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.deleteUser(1L);

        verify(userRepository).deleteById(1L);
    }

    @Test
    void deleteUser_UserDoesNotExist() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        userService.deleteUser(1L);

        verify(userRepository, never()).deleteById(anyLong());
    }

    @Test
    void activeUsers() {
        List<User> users = Arrays.asList(user);
        when(userRepository.findAllActiveUsers()).thenReturn(users);

        List<User> result = userService.activeUsers();

        assertEquals(users, result);
    }

    @Test
    void addCarToUser() {
        Car car = new Car();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.addCarToUser(1L, car);

        assertTrue(user.getCars().contains(car));
        verify(userRepository).save(user);
    }

    @Test
    void removeCarFromUser() {
        Car car = new Car();
        user.addCar(car);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.removeCarFromUser(1L, car);

        assertFalse(user.getCars().contains(car));
        verify(carRepository).delete(car);
        verify(userRepository).save(user);
    }

    @Test
    void getUserCars() {
        Set<Car> cars = new HashSet<>();
        when(userRepository.getUserCars(1L)).thenReturn(cars);

        Set<Car> result = userService.getUserCars(1L);

        assertEquals(cars, result);
    }

    @Test
    void getUserPurchases() {
        Set<Purchase> purchases = new HashSet<>();
        when(userRepository.getUserPurchases(1L)).thenReturn(purchases);

        Set<Purchase> result = userService.getUserPurchases(1L);

        assertEquals(purchases, result);
    }
}