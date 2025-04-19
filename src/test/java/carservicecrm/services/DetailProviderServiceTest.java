package carservicecrm.services;

import carservicecrm.models.Detail;
import carservicecrm.models.DetailProvider;
import carservicecrm.repositories.DetailProviderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DetailProviderServiceTest {

    @Mock
    private DetailProviderRepository detailProviderRepository;

    @InjectMocks
    private DetailProviderService detailProviderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testList() {
        when(detailProviderRepository.findAllProviders()).thenReturn(Collections.emptyList());

        var result = detailProviderService.list();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(detailProviderRepository, times(1)).findAllProviders();
    }

    @Test
    void testSaveProvider_Success() {
        DetailProvider detailProvider = new DetailProvider();
        when(detailProviderRepository.save(any(DetailProvider.class))).thenReturn(detailProvider);

        boolean result = detailProviderService.saveProvider(detailProvider);

        assertTrue(result);
        verify(detailProviderRepository, times(1)).save(detailProvider);
    }

    @Test
    void testSaveProvider_Failure() {
        DetailProvider detailProvider = new DetailProvider();
        doThrow(new RuntimeException("Database error")).when(detailProviderRepository).save(any(DetailProvider.class));

        boolean result = detailProviderService.saveProvider(detailProvider);

        assertFalse(result);
        verify(detailProviderRepository, times(1)).save(detailProvider);
    }

    @Test
    void testDeleteProvider_Success() {
        Long providerId = 1L;
        DetailProvider provider = new DetailProvider();
        provider.setId(providerId);
        when(detailProviderRepository.findProviderById(providerId)).thenReturn(provider);

        detailProviderService.deleteProvider(providerId);

        verify(detailProviderRepository, times(1)).deleteProviderById(providerId);
    }

    @Test
    void testDeleteProvider_NotFound() {
        Long providerId = 1L;
        when(detailProviderRepository.findProviderById(providerId)).thenReturn(null);

        detailProviderService.deleteProvider(providerId);

        verify(detailProviderRepository, never()).deleteProviderById(anyLong());
    }

    @Test
    void testAddDetailToProvider() {
        Long providerId = 1L;
        Detail detail = new Detail();
        DetailProvider provider = new DetailProvider();
        when(detailProviderRepository.findProviderById(providerId)).thenReturn(provider);

        detailProviderService.addDetailToProvider(providerId, detail);

        verify(detailProviderRepository, times(1)).save(provider);
    }

    @Test
    void testRemoveDetailFromProvider() {
        Long providerId = 1L;
        Detail detail = new Detail();
        DetailProvider provider = new DetailProvider();
        when(detailProviderRepository.findProviderById(providerId)).thenReturn(provider);

        detailProviderService.removeDetailFromProvider(providerId, detail);

        verify(detailProviderRepository, times(1)).save(provider);
    }

    @Test
    void testGetProviderDetails() {
        Long providerId = 1L;
        Set<Detail> details = Set.of(new Detail());
        when(detailProviderRepository.getProviderDetails(providerId)).thenReturn(details);

        var result = detailProviderService.getProviderDetails(providerId);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(detailProviderRepository, times(1)).getProviderDetails(providerId);
    }

    @Test
    void testGetProvider() {
        Long providerId = 1L;
        DetailProvider provider = new DetailProvider();
        when(detailProviderRepository.findProviderById(providerId)).thenReturn(provider);

        var result = detailProviderService.getProvider(providerId);

        assertNotNull(result);
        assertEquals(provider, result);
        verify(detailProviderRepository, times(1)).findProviderById(providerId);
    }

    @Test
    void testGetProviderByName() {
        String name = "Test Provider";
        DetailProvider provider = new DetailProvider();
        when(detailProviderRepository.findByName(name)).thenReturn(provider);

        var result = detailProviderService.getProviderByName(name);

        assertNotNull(result);
        assertEquals(provider, result);
        verify(detailProviderRepository, times(1)).findByName(name);
    }
}