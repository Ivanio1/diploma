package carservicecrm.models;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
public class ManufacturerTest {

    @Test
    public void testManufacturerFields() {
        Manufacturer manufacturer = new Manufacturer();
        Long id = 1L;
        String specialization = "Engine Manufacturing";

        manufacturer.setId(id);
        manufacturer.setDetail_specialization(specialization);

        assertEquals(id, manufacturer.getId());
        assertEquals(specialization, manufacturer.getDetail_specialization());
    }

    @Test
    public void testManufacturerWorkerRequests() {
        Manufacturer manufacturer = new Manufacturer();
        Set<WorkerRequest> workerRequests = new HashSet<>();
        WorkerRequest request = new WorkerRequest();
        workerRequests.add(request);

        manufacturer.setWorkerRequests(workerRequests);

        assertEquals(workerRequests, manufacturer.getWorkerRequests());
        assertTrue(manufacturer.getWorkerRequests().contains(request));
    }

    @Test
    public void testManufacturerEmployee() {
        Manufacturer manufacturer = new Manufacturer();
        Employee employee = new Employee();

        manufacturer.setEmployee(employee);

        assertEquals(employee, manufacturer.getEmployee());
    }
}
