package carservicecrm.controllers;

import carservicecrm.models.Image;
import carservicecrm.repositories.ImageRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class ImageControllerTest {

    @Mock
    private ImageRepository imageRepository;

    @InjectMocks
    private ImageController imageController;

    public ImageControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getImageById_ReturnsImage() {
        Image image = new Image();
        image.setOriginalFileName("testImage.jpg");
        image.setContentType("image/jpeg");
        image.setSize(1024L);
        image.setBytes(new byte[1024]);

        when(imageRepository.findById(anyLong())).thenReturn(Optional.of(image));

        ResponseEntity<?> response = imageController.getImageById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("testImage.jpg", response.getHeaders().getFirst("fileName"));
        assertEquals("image/jpeg", response.getHeaders().getContentType().toString());
        assertEquals(1024, response.getHeaders().getContentLength());
        assertEquals(InputStreamResource.class, response.getBody().getClass());
    }

    @Test
    void getImageById_ImageNotFound_Returns404() {
        when(imageRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<?> response = imageController.getImageById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}