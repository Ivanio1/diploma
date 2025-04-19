package carservicecrm.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AdministratorTest {
    private Administrator administrator;
    private Employee employee;
    private Purchase purchase;

    @BeforeEach
    public void setUp() {
        administrator = new Administrator();
        employee = new Employee();
        purchase = new Purchase();
    }

    @Test
    public void testGettersAndSetters() {
        administrator.setId(1L);
        administrator.setEmployee(employee);

        Set<Purchase> purchases = new HashSet<>();
        purchases.add(purchase);
        administrator.setPurchases(purchases);

        assertEquals(1L, administrator.getId());
        assertEquals(employee, administrator.getEmployee());
        assertEquals(purchases, administrator.getPurchases());
    }

}