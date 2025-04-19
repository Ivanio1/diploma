package carservicecrm.services;

import carservicecrm.models.Administrator;
import carservicecrm.repositories.AdministratorRepository;
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

class AdministratorServiceTest {

    @Mock
    private AdministratorRepository administratorRepository;

    @InjectMocks
    private AdministratorService administratorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveAdmin_Success() {
        Administrator admin = new Administrator();

        boolean result = administratorService.saveAdmin(admin);

        assertTrue(result);
        verify(administratorRepository, times(1)).save(admin);
    }

    @Test
    void testSaveAdmin_Failure() {
        Administrator admin = new Administrator();

        doThrow(new RuntimeException("Database error")).when(administratorRepository).save(any(Administrator.class));

        boolean result = administratorService.saveAdmin(admin);

        assertFalse(result);
        verify(administratorRepository, times(1)).save(admin);
    }

    @Test
    void testList() {
        Administrator admin1 = new Administrator();
        Administrator admin2 = new Administrator();
        List<Administrator> expectedAdmins = Arrays.asList(admin1, admin2);

        when(administratorRepository.findAllAdmins()).thenReturn(expectedAdmins);

        List<Administrator> actualAdmins = administratorService.list();

        assertEquals(expectedAdmins.size(), actualAdmins.size());
        assertTrue(actualAdmins.containsAll(expectedAdmins));
        verify(administratorRepository, times(1)).findAllAdmins();
    }
}