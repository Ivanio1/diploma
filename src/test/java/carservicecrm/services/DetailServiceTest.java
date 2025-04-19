package carservicecrm.services;

import carservicecrm.models.Detail;
import carservicecrm.models.DetailProvider;
import carservicecrm.repositories.DetailRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DetailServiceTest {

    @Mock
    private DetailRepository detailRepository;

    @InjectMocks
    private DetailService detailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveDetail_NewDetail() {
        Detail detail = new Detail();
        DetailProvider provider = new DetailProvider();
        when(detailRepository.findAllByName(detail.getName())).thenReturn(Collections.emptySet());

        boolean result = detailService.saveDetail(detail, provider);

        assertTrue(result);
        verify(detailRepository, times(1)).save(detail);
    }

    @Test
    void testSaveDetail_NewDetailThrows() {
        Detail detail = new Detail();
        DetailProvider provider = new DetailProvider();
        doThrow(new Exception());
        boolean result = detailService.saveDetail(detail, provider);
        assertFalse(result);
    }

    @Test
    void testSaveDetail_ExistingDetail() {
        Detail detail = new Detail();
        detail.setName("Test");
        detail.setStock(10);
        detail.setPrice(100);

        Detail existingDetail = new Detail();
        existingDetail.setStock(5);

        DetailProvider provider = new DetailProvider();
        provider.setDetails(Set.of(existingDetail));

        when(detailRepository.findAllByName(detail.getName())).thenReturn(Set.of(existingDetail));

        boolean result = detailService.saveDetail(detail, provider);

        assertTrue(result);
        assertEquals(15, existingDetail.getStock());
        verify(detailRepository, times(1)).save(existingDetail);
    }

    @Test
    void testGetDetailById() {
        Long id = 1L;
        Detail detail = new Detail();
        when(detailRepository.findDetailById(id)).thenReturn(detail);

        Detail result = detailService.getDetailById(id);

        assertNotNull(result);
        assertEquals(detail, result);
        verify(detailRepository, times(1)).findDetailById(id);
    }

    @Test
    void testGetDetailByStorageStock() {
        Integer stock = 10;
        Detail detail = new Detail();
        when(detailRepository.findDetailByStoragestock(stock)).thenReturn(detail);

        Detail result = detailService.getDetailByStorageStock(stock);

        assertNotNull(result);
        assertEquals(detail, result);
        verify(detailRepository, times(1)).findDetailByStoragestock(stock);
    }

    @Test
    void testGetDetailByName() {
        String name = "Test Detail";
        Detail detail = new Detail();
        when(detailRepository.findByName(name)).thenReturn(detail);

        Detail result = detailService.getDetailByName(name);

        assertNotNull(result);
        assertEquals(detail, result);
        verify(detailRepository, times(1)).findByName(name);
    }

    @Test
    void testList() {
        when(detailRepository.findAll()).thenReturn(Collections.emptyList());

        var result = detailService.list();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(detailRepository, times(1)).findAll();
    }

    @Test
    void testListStorage() {
        when(detailRepository.findAllInStorage()).thenReturn(Collections.emptyList());

        var result = detailService.listStorage();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(detailRepository, times(1)).findAllInStorage();
    }

    @Test
    void testListStorageIfBigger() {
        Integer num = 5;
        when(detailRepository.findAll()).thenReturn(Collections.emptyList());

        var result = detailService.listStorageIfBigger(num);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(detailRepository, times(1)).findAll();
    }

    @Test
    void testUpdateStorageStock() {
        Detail detail = new Detail();
        detail.setStock(10);
        detail.setStoragestock(5);

        detailService.updateStorageStock(detail, 3);

        assertEquals(7, detail.getStock());
        assertEquals(8, detail.getStoragestock());
        verify(detailRepository, times(1)).save(detail);
    }

    @Test
    void testUpdateStorageStockByName() {
        String name = "Test Detail";
        Integer number = 3;

        detailService.updateStorageStockByName(name, number);

        verify(detailRepository, times(1)).updateStorageStockbyname(name, number);
    }

    @Test
    public void testSaveDetailWhenDetailDoesNotExist() {
        Detail detail = new Detail();
        detail.setId(1L);
        detail.setName("Detail1");
        detail.setStock(5);
        detail.setStoragestock(50);
        DetailProvider provider = new DetailProvider();

        when(detailRepository.findAllByName("Detail1")).thenReturn(new HashSet<>());

        boolean result = detailService.saveDetail(detail, provider);

        assertTrue(result);
        verify(detailRepository).save(detail);
    }
}
