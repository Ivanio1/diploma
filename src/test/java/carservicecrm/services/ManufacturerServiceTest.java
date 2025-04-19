package carservicecrm.services;

import carservicecrm.models.Manufacturer;
import carservicecrm.repositories.ManufacturerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ManufacturerServiceTest {

    @Mock
    private ManufacturerRepository manufacturerRepository;

    @InjectMocks
    private ManufacturerService manufacturerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveManufacturer_Success() {
        Manufacturer manufacturer = new Manufacturer();

        boolean result = manufacturerService.saveManufacturer(manufacturer);

        assertTrue(result);
        verify(manufacturerRepository, times(1)).save(manufacturer);
    }

    @Test
    void testSaveManufacturer_Failure() {
        Manufacturer manufacturer = new Manufacturer();

        doThrow(new RuntimeException("Database error")).when(manufacturerRepository).save(any(Manufacturer.class));

        boolean result = manufacturerService.saveManufacturer(manufacturer);

        assertFalse(result);
        verify(manufacturerRepository, times(1)).save(manufacturer);
    }

   @Test
    void testList() {
        Manufacturer manufacturer1 = new Manufacturer();
        Manufacturer manufacturer2 = new Manufacturer();
        List<Manufacturer> expectedManufacturers = Arrays.asList(manufacturer1, manufacturer2);

        when(manufacturerRepository.findAllManufacturers()).thenReturn(expectedManufacturers);

        List<Manufacturer> actualManufacturers = manufacturerService.list();

        assertEquals(expectedManufacturers.size(), actualManufacturers.size());
        assertTrue(actualManufacturers.containsAll(expectedManufacturers));
        verify(manufacturerRepository, times(1)).findAllManufacturers();
    }
}
