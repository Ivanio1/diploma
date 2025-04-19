package carservicecrm.services;

import carservicecrm.models.Worker;
import carservicecrm.repositories.WorkerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class WorkerServiceTest {

    @Mock
    private WorkerRepository workerRepository;

    @InjectMocks
    private WorkerService workerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveWorker_Success() {
        Worker worker = new Worker();

        boolean result = workerService.saveWorker(worker);

        assertTrue(result);
        verify(workerRepository, times(1)).save(worker);
    }

    @Test
    void testSaveWorker_Failure() {
        Worker worker = new Worker();

        doThrow(new RuntimeException("Database error")).when(workerRepository).save(any(Worker.class));

        boolean result = workerService.saveWorker(worker);

        assertFalse(result);
        verify(workerRepository, times(1)).save(worker);
    }

    @Test
    void testList() {
        Worker worker1 = new Worker();
        Worker worker2 = new Worker();
        List<Worker> expectedWorkers = Arrays.asList(worker1, worker2);

        when(workerRepository.findAll()).thenReturn(expectedWorkers);

        List<Worker> actualWorkers = workerService.list();

        assertEquals(expectedWorkers.size(), actualWorkers.size());
        assertTrue(actualWorkers.containsAll(expectedWorkers));
        verify(workerRepository, times(1)).findAll();
    }

    @Test
    void testGetWorker() {
        Long workerId = 1L;
        Worker expectedWorker = new Worker();

        when(workerRepository.findById(workerId)).thenReturn(Optional.of(expectedWorker));

        Worker actualWorker = workerService.getWorker(workerId).orElse(null);

        assertEquals(expectedWorker, actualWorker);
        verify(workerRepository, times(1)).findById(workerId);
    }

    @Test
    void testUpdatePurchaseStatusToDone() {
        Long id = 1L;

        workerService.update_purchase_status_to_done(id);

        verify(workerRepository, times(1)).update_purchase_status_to_done(id);
    }

    @Test
    void testUpdatePurchaseStatusToWaiting() {
        Long id = 1L;

        workerService.update_purchase_status_to_waiting(id);

        verify(workerRepository, times(1)).update_purchase_status_to_waiting(id);
    }

    @Test
    void testUpdatePurchaseStatusToInProcess() {
        Long id = 1L;

        workerService.update_purchase_status_to_in_process(id);

        verify(workerRepository, times(1)).update_purchase_status_to_in_process(id);
    }
}
