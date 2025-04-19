package carservicecrm.services;

import carservicecrm.models.Employee;
import carservicecrm.repositories.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveEmployeeSuccess() {
        Employee employee = new Employee();
        assertTrue(employeeService.saveEmployee(employee));
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    public void testSaveEmployeeFailure() {
        Employee employee = new Employee();
        doThrow(new RuntimeException()).when(employeeRepository).save(employee);
        assertFalse(employeeService.saveEmployee(employee));
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    public void testGetEmployee() {
        Long userId = 1L;
        Employee employee = new Employee();
        when(employeeRepository.findByUserId(userId)).thenReturn(employee);

        Employee result = employeeService.getEmployee(userId);

        assertNotNull(result);
        assertEquals(employee, result);
        verify(employeeRepository, times(1)).findByUserId(userId);
    }

    @Test
    public void testGetEmployeeById() {
        Long id = 1L;
        Employee employee = new Employee();
        when(employeeRepository.findEmployeeById(id)).thenReturn(employee);

        Employee result = employeeService.getEmployeeById(id);

        assertNotNull(result);
        assertEquals(employee, result);
        verify(employeeRepository, times(1)).findEmployeeById(id);
    }

    @Test
    public void testListEmployees() {
        Employee employee = new Employee();
        when(employeeRepository.findAllEmployees()).thenReturn(Collections.singletonList(employee));

        List<Employee> result = employeeService.list();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(employee, result.get(0));
        verify(employeeRepository, times(1)).findAllEmployees();
    }
}