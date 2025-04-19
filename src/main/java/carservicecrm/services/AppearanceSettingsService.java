package carservicecrm.services;

import carservicecrm.models.AppearanceSettings;
import carservicecrm.repositories.AppearanceSettingsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AppearanceSettingsService {

    private final AppearanceSettingsRepository settingsRepository;

    @Cacheable("settings")
    public AppearanceSettings getSettings() {
        return settingsRepository.findAll().stream().findFirst().orElse(null);
    }

    @CacheEvict("settings")
    public void evict() {
    }

    public void saveSettings(AppearanceSettings settings) {
        AppearanceSettings existingSettings;
        try {
            existingSettings = settingsRepository.findAll().stream().findFirst().orElse(null);
        } catch (Exception e) {
            existingSettings = null;
        }
        if (existingSettings == null) {
            settingsRepository.save(settings);
        } else {
            existingSettings.setSiteName(settings.getSiteName());
            existingSettings.setTitleColor(settings.getTitleColor());
            existingSettings.setTdColor(settings.getTdColor());
            existingSettings.setTextColor(settings.getTextColor());
            existingSettings.setTdHoverColor(settings.getTdHoverColor());
            existingSettings.setFontSelect(settings.getFontSelect());
            String siteImage = settings.getImage();
            if (siteImage != null && !siteImage.isEmpty()) {
                existingSettings.setImage(siteImage);
            }
            settingsRepository.save(existingSettings);
        }

    }
}
