package carservicecrm.models;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LogoTest {

    private Logo logo;

    @BeforeEach
    public void setUp() {
        logo = new Logo();
    }

    @Test
    public void testLogoFields() {
        Long id = 1L;
        String filename = "logo.png";
        byte[] data = new byte[]{1, 2, 3, 4, 5};

        logo.setId(id);
        logo.setFilename(filename);
        logo.setData(data);

        assertEquals(id, logo.getId());
        assertEquals(filename, logo.getFilename());
        assertArrayEquals(data, logo.getData());
    }

    @Test
    public void testLogoEmptyFields() {
        assertNull(logo.getId());
        assertNull(logo.getFilename());
        assertNull(logo.getData());
    }

    @Test
    public void testLogoDataNull() {
        logo.setData(null);
        assertNull(logo.getData());
    }
}