package carservicecrm.services;

import carservicecrm.models.Logo;
import carservicecrm.repositories.LogoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class LogoServiceTest {

    @Mock
    private LogoRepository logoRepository;

    @InjectMocks
    private LogoService logoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveLogo() {
        Logo logo = new Logo();

        when(logoRepository.save(any(Logo.class))).thenReturn(logo);

        Logo savedLogo = logoService.saveLogo(logo);

        assertNotNull(savedLogo);
        verify(logoRepository, times(1)).deleteAll();
        verify(logoRepository, times(1)).save(logo);
    }

    @Test
    void testGetLogo() {
        Long logoId = 1L;
        Logo logo = new Logo();

        when(logoRepository.findById(logoId)).thenReturn(Optional.of(logo));

        Optional<Logo> foundLogo = logoService.getLogo(logoId);

        assertTrue(foundLogo.isPresent());
        assertEquals(logo, foundLogo.get());
        verify(logoRepository, times(1)).findById(logoId);
    }

    @Test
    void testEvict() {
        logoService.evict();
    }

    @Test
    void testGetCurrentLogo_NoLogos() {
        when(logoRepository.findAll()).thenReturn(List.of());

        Logo currentLogo = logoService.getCurrentLogo();

        assertNull(currentLogo);
        verify(logoRepository, times(1)).findAll();
    }

    @Test
    void testGetCurrentLogo_WithLogos() {
        Logo logo = new Logo();
        when(logoRepository.findAll()).thenReturn(List.of(logo));

        Logo currentLogo = logoService.getCurrentLogo();

        assertNotNull(currentLogo);
        assertEquals(logo, currentLogo);
        verify(logoRepository, times(1)).findAll();
    }
}