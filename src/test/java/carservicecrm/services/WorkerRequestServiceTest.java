package carservicecrm.services;

import carservicecrm.models.WorkerRequest;
import carservicecrm.repositories.WorkerRequestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WorkerRequestServiceTest {

    @Mock
    private WorkerRequestRepository workerRequestRepository;

    @InjectMocks
    private WorkerRequestService workerRequestService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveWorkerRequestSuccess() {
        WorkerRequest workerRequest = new WorkerRequest();
        workerRequest.setId(1L);
        workerRequest.setRequestText("Sample WorkerRequest");

        boolean result = workerRequestService.save(workerRequest);

        assertTrue(result);
        verify(workerRequestRepository, times(1)).save(workerRequest);
    }

    @Test
    void testSaveWorkerRequestFailure() {
        WorkerRequest workerRequest = new WorkerRequest();

        doThrow(new RuntimeException("Database error")).when(workerRequestRepository).save(workerRequest);

        boolean result = workerRequestService.save(workerRequest);

        assertFalse(result);
        verify(workerRequestRepository, times(1)).save(workerRequest);
    }

    @Test
    void testListWorkerRequests() {
        List<WorkerRequest> expectedWorkerRequests = new ArrayList<>();
        WorkerRequest workerRequest = new WorkerRequest();
        workerRequest.setId(1L);
        workerRequest.setRequestText("WorkerRequest 1");
        expectedWorkerRequests.add(workerRequest);

        WorkerRequest workerRequest1 = new WorkerRequest();
        workerRequest1.setId(1L);
        workerRequest1.setRequestText("WorkerRequest 1");
        expectedWorkerRequests.add(workerRequest1);

        when(workerRequestRepository.findAllRequests()).thenReturn(expectedWorkerRequests);

        List<WorkerRequest> actualWorkerRequests = workerRequestService.list();

        assertEquals(expectedWorkerRequests.size(), actualWorkerRequests.size());
        assertIterableEquals(expectedWorkerRequests, actualWorkerRequests);
        verify(workerRequestRepository, times(1)).findAllRequests();
    }

    @Test
    void testDeleteWorkerRequestExists() {
        WorkerRequest workerRequest = new WorkerRequest();
        workerRequest.setId(1L);

        when(workerRequestRepository.findRequestById(1L)).thenReturn(workerRequest);

        workerRequestService.deleteRequest(1L);

        verify(workerRequestRepository, times(1)).deleteRequestById(1L);
        verify(workerRequestRepository, times(1)).findRequestById(1L);
    }

    @Test
    void testDeleteWorkerRequestNotFound() {
        when(workerRequestRepository.findRequestById(1L)).thenReturn(null);

        workerRequestService.deleteRequest(1L);

        verify(workerRequestRepository, times(0)).deleteRequestById(1L);
        verify(workerRequestRepository, times(1)).findRequestById(1L);
    }
}