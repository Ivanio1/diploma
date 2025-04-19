package carservicecrm.services;

import carservicecrm.models.AppearanceSettings;
import carservicecrm.repositories.AppearanceSettingsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AppearanceSettingsServiceTest {

    @Mock
    private AppearanceSettingsRepository settingsRepository;

    @InjectMocks
    private AppearanceSettingsService appearanceSettingsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetSettings_ShouldReturnNull_WhenNoSettingsFound() {
        when(settingsRepository.findAll()).thenReturn(Collections.emptyList());

        AppearanceSettings result = appearanceSettingsService.getSettings();

        assertNull(result);
        verify(settingsRepository).findAll();
    }

    @Test
    void testGetSettings_ShouldReturnSettings_WhenSettingsExist() {
        AppearanceSettings settings = new AppearanceSettings();
        settings.setSiteName("Test Site");

        when(settingsRepository.findAll()).thenReturn(Collections.singletonList(settings));

        AppearanceSettings result = appearanceSettingsService.getSettings();

        assertNotNull(result);
        assertEquals("Test Site", result.getSiteName());
        verify(settingsRepository).findAll();
    }

    @Test
    void testEvict_ShouldCallEvictWhenCalled() {
        appearanceSettingsService.evict();
    }

    @Test
    void testSaveSettings_ShouldSaveNewSettings_WhenNoExistingSettings() {
        AppearanceSettings newSettings = new AppearanceSettings();
        newSettings.setSiteName("New Site");

        when(settingsRepository.findAll()).thenReturn(Collections.emptyList());

        appearanceSettingsService.saveSettings(newSettings);

        verify(settingsRepository).save(newSettings);
    }

    @Test
    void testSaveSettings_ShouldUpdateExistingSettings_WhenSettingsExist() {
        AppearanceSettings existingSettings = new AppearanceSettings();
        existingSettings.setSiteName("Old Site");

        when(settingsRepository.findAll()).thenReturn(Collections.singletonList(existingSettings));

        AppearanceSettings newSettings = new AppearanceSettings();
        newSettings.setSiteName("Updated Site");
        newSettings.setTitleColor("red");
        newSettings.setImage("Image");

        appearanceSettingsService.saveSettings(newSettings);

        assertEquals("Updated Site", existingSettings.getSiteName());
        assertEquals("red", existingSettings.getTitleColor());
        verify(settingsRepository).save(any(AppearanceSettings.class));
    }

    @Test
    void testSaveSettings_ShouldHandleExceptionGracefully() {
        when(settingsRepository.findAll()).thenThrow(new RuntimeException("Database error"));

        AppearanceSettings newSettings = new AppearanceSettings();
        newSettings.setSiteName("Site After Error");

        assertDoesNotThrow(() -> appearanceSettingsService.saveSettings(newSettings));
        verify(settingsRepository).save(newSettings);
    }
}