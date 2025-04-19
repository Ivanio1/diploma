package carservicecrm.models;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

public class WorkerTest {

    private Worker worker;

    @BeforeEach
    public void setUp() {
        worker = new Worker();
    }

    @Test
    public void testWorkerFields() {
        Long id = 1L;
        String specialization = "Electrician";

        worker.setId(id);
        worker.setSpecialization(specialization);

        assertEquals(id, worker.getId());
        assertEquals(specialization, worker.getSpecialization());
    }

    @Test
    public void testWorkerEmployee() {
        Employee employee = new Employee();
        worker.setEmployee(employee);

        assertEquals(employee, worker.getEmployee());
    }

    @Test
    public void testWorkerPurchases() {
        Set<Purchase> purchases = new HashSet<>();
        Purchase purchase = new Purchase();
        purchases.add(purchase);

        worker.setPurchases(purchases);

        assertEquals(purchases, worker.getPurchases());
        assertTrue(worker.getPurchases().contains(purchase));
    }

    @Test
    public void testWorkerRequests() {
        Set<WorkerRequest> workerRequests = new HashSet<>();
        WorkerRequest request = new WorkerRequest();
        workerRequests.add(request);

        worker.setWorkerRequests(workerRequests);

        assertEquals(workerRequests, worker.getWorkerRequests());
        assertTrue(worker.getWorkerRequests().contains(request));
    }

    @Test
    public void testWorkerEmptyPurchases() {
        Set<Purchase> purchases = worker.getPurchases();

        assertNull(purchases);
    }

    @Test
    public void testWorkerEmptyRequests() {
        Set<WorkerRequest> workerRequests = worker.getWorkerRequests();

        assertNull(workerRequests);
    }
}