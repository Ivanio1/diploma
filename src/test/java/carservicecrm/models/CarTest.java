package carservicecrm.models;

import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class CarTest {

    @Test
    public void testCarFields() {
        Car car = new Car();
        Long id = 1L;
        String brand = "Toyota";
        String model = "Corolla";
        Date creationDate = new Date();

        car.setId(id);
        car.setBrand(brand);
        car.setModel(model);
        car.setCreation_date(creationDate);

        assertEquals(id, car.getId());
        assertEquals(brand, car.getBrand());
        assertEquals(model, car.getModel());
        assertEquals(creationDate, car.getCreation_date());
    }

    @Test
    public void testCarUsers() {
        Car car = new Car();
        Set<User> users = new HashSet<>();
        User user = new User();
        users.add(user);

        car.setUsers(users);

        assertEquals(users, car.getUsers());
        assertTrue(car.getUsers().contains(user));
    }

    @Test
    public void testCarPurchases() {
        Car car = new Car();
        Set<Purchase> purchases = new HashSet<>();
        Purchase purchase = new Purchase();
        purchases.add(purchase);

        car.setPurchases(purchases);

        assertEquals(purchases, car.getPurchases());
        assertTrue(car.getPurchases().contains(purchase));
    }
}