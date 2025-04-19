package carservicecrm.services;

import carservicecrm.models.Purchase;
import carservicecrm.models.Worker;
import carservicecrm.repositories.PurchaseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PurchaseServiceTest {

    @Mock
    private PurchaseRepository purchaseRepository;

    @InjectMocks
    private PurchaseService purchaseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testList() {
        when(purchaseRepository.findAllPurchases()).thenReturn(Collections.emptyList());

        List<Purchase> result = purchaseService.list();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(purchaseRepository, times(1)).findAllPurchases();
    }

    @Test
    void testListUnalloc() {
        when(purchaseRepository.findAllUnAllocPurchases()).thenReturn(Collections.emptyList());

        List<Purchase> result = purchaseService.listUnalloc();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(purchaseRepository, times(1)).findAllUnAllocPurchases();
    }

    @Test
    void testListAlloc() {
        when(purchaseRepository.findAllAllocPurchases()).thenReturn(Collections.emptyList());

        List<Purchase> result = purchaseService.listAlloc();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(purchaseRepository, times(1)).findAllAllocPurchases();
    }

    @Test
    void testListAllocByWorker() {
        Long workerId = 1L;
        Worker worker = new Worker();
        worker.setId(workerId);
        Purchase purchase = new Purchase();
        purchase.setWorker(worker);
        when(purchaseRepository.findAllAllocPurchases()).thenReturn(List.of(purchase));

        List<Purchase> result = purchaseService.listAllocByWorker(workerId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(workerId, result.get(0).getWorker().getId());
        verify(purchaseRepository, times(1)).findAllAllocPurchases();
    }

    @Test
    void testSavePurchase() {
        Purchase purchase = new Purchase();
        when(purchaseRepository.save(purchase)).thenReturn(purchase);

        purchaseService.savePurchase(purchase);

        verify(purchaseRepository, times(1)).save(purchase);
        verify(purchaseRepository, times(1)).updatePurchase(purchase.getId());
    }

    @Test
    void testGetPurchase() {
        Long id = 1L;
        Purchase purchase = new Purchase();
        when(purchaseRepository.findPurchaseById(id)).thenReturn(purchase);

        Purchase result = purchaseService.getPurchase(id);

        assertNotNull(result);
        assertEquals(purchase, result);
        verify(purchaseRepository, times(1)).findPurchaseById(id);
    }

    @Test
    void testDeletePurchase_Exists() {
        Long id = 1L;
        Purchase purchase = new Purchase();
        purchase.setId(id);
        when(purchaseRepository.findPurchaseById(id)).thenReturn(purchase);

        purchaseService.deletePurchase(id);

        verify(purchaseRepository, times(1)).deletePurchaseById(id);
    }

    @Test
    void testDeletePurchase_NotExists() {
        Long id = 1L;
        when(purchaseRepository.findPurchaseById(id)).thenReturn(null);

        purchaseService.deletePurchase(id);

        verify(purchaseRepository, times(0)).deletePurchaseById(id);
    }
}