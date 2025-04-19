package carservicecrm.services;

import carservicecrm.models.Employee;
import carservicecrm.models.Sto;
import carservicecrm.models.Worker;
import carservicecrm.repositories.StoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StoServiceTest {

    @Mock
    private StoRepository stoRepository;

    @InjectMocks
    private StoService stoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testList() {
        when(stoRepository.findAllStoes()).thenReturn(Collections.emptyList());

        List<Sto> result = stoService.list();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(stoRepository, times(1)).findAllStoes();
    }

    @Test
    void testSaveSto() {
        Sto sto = new Sto();
        when(stoRepository.save(sto)).thenReturn(sto);

        boolean result = stoService.saveSto(sto);

        assertTrue(result);
        verify(stoRepository, times(1)).save(sto);
    }

    @Test
    void testDeleteSto_Exists() {
        Long id = 1L;
        Sto sto = new Sto();
        sto.setId(id);
        when(stoRepository.findStoById(id)).thenReturn(sto);

        stoService.deleteSto(id);

        verify(stoRepository, times(1)).deleteStoById(id);
    }

    @Test
    void testDeleteSto_NotExists() {
        Long id = 1L;
        when(stoRepository.findStoById(id)).thenReturn(null);

        stoService.deleteSto(id);

        verify(stoRepository, times(0)).deleteStoById(id);
    }

    @Test
    void testAddEmployeeToSto() {
        Long stoId = 1L;
        Employee employee = new Employee();
        Sto sto = new Sto();
        when(stoRepository.findStoById(stoId)).thenReturn(sto);

        stoService.addEmployeeToSto(stoId, employee);

        verify(stoRepository, times(1)).save(sto);
        assertTrue(sto.getEmployees().contains(employee));
    }

    @Test
    void testRemoveEmployeeFromSto() {
        Long stoId = 1L;
        Employee employee = new Employee();
        Sto sto = new Sto();
        sto.addEmployee(employee);
        when(stoRepository.findStoById(stoId)).thenReturn(sto);

        stoService.removeEmployeeFromSto(stoId, employee);

        verify(stoRepository, times(1)).save(sto);
        assertFalse(sto.getEmployees().contains(employee));
    }

    @Test
    void testGetStoEmployees() {
        Long stoId = 1L;
        Set<Employee> employees = new HashSet<>();
        when(stoRepository.getStoEmployees(stoId)).thenReturn(employees);

        Set<Employee> result = stoService.getStoEmployees(stoId);

        assertNotNull(result);
        assertEquals(employees, result);
        verify(stoRepository, times(1)).getStoEmployees(stoId);
    }

    @Test
    void testGetStoWorkers() {
        Long stoId = 1L;
        Employee employee = new Employee();
        Worker worker = new Worker();
        employee.setWorker(worker);
        Set<Employee> employees = new HashSet<>();
        employees.add(employee);
        when(stoRepository.getStoEmployees(stoId)).thenReturn(employees);

        Set<Worker> result = stoService.getStoWorkers(stoId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.contains(worker));
        verify(stoRepository, times(1)).getStoEmployees(stoId);
    }

    @Test
    void testGetSto() {
        Long stoId = 1L;
        Sto sto = new Sto();
        when(stoRepository.findStoById(stoId)).thenReturn(sto);

        Sto result = stoService.getSto(stoId);

        assertNotNull(result);
        assertEquals(sto, result);
        verify(stoRepository, times(1)).findStoById(stoId);
    }

    @Test
    void testGetStoByName() {
        String name = "Test STO";
        Sto sto = new Sto();
        when(stoRepository.findByName(name)).thenReturn(sto);
        Sto result = stoService.getStoByName(name);

        assertNotNull(result);
        assertEquals(sto, result);
        verify(stoRepository, times(1)).findByName(name);
    }

    @Test
    public void testSaveStoFailure() {
        Sto sto = new Sto();
        doThrow(new RuntimeException("Save exception")).when(stoRepository).save(sto);
        boolean result = stoService.saveSto(sto);
        assertFalse(result);
        verify(stoRepository, times(1)).save(sto);
    }
}