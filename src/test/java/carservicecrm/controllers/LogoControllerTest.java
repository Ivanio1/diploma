package carservicecrm.controllers;

import carservicecrm.models.Logo;
import carservicecrm.services.LogoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class LogoControllerTest {

    @Mock
    private LogoService logoService;

    @InjectMocks
    private LogoController logoController;

    private MultipartFile file;
    private Logo logo;

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        logo = new Logo();
        logo.setFilename("testLogo.png");
        logo.setData("testData".getBytes());

        file = mock(MultipartFile.class);
        when(file.getOriginalFilename()).thenReturn("testLogo.png");
        when(file.getBytes()).thenReturn("testData".getBytes());
    }

    @Test
    void handleLogoUpload_Success() throws IOException {
        String result = logoController.handleLogoUpload(file);

        verify(logoService).saveLogo(any(Logo.class));
        verify(logoService).evict();
        assertEquals("redirect:/", result);
    }

    @Test
    void handleLogoUpload_Exception() throws IOException {
        when(file.getBytes()).thenThrow(new IOException("File error"));

        try {
            logoController.handleLogoUpload(file);
        } catch (IOException e) {
            assertEquals("File error", e.getMessage());
        }

        verify(logoService, never()).saveLogo(any(Logo.class));
    }

    @Test
    void getLogoById_Success() throws IOException {
        when(logoService.getCurrentLogo()).thenReturn(logo);

        ResponseEntity<?> response = logoController.getLogoById();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("testLogo.png", response.getHeaders().getFirst("fileName"));
    }
}