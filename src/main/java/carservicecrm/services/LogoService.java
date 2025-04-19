package carservicecrm.services;

import carservicecrm.models.Logo;
import carservicecrm.repositories.LogoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class LogoService {

    private final LogoRepository logoRepository;

    public Logo saveLogo(Logo logo) {
        logoRepository.deleteAll();
        return logoRepository.save(logo);
    }

    public Optional<Logo> getLogo(Long id) {
        return logoRepository.findById(id);
    }

    @CacheEvict("logo")
    public void evict() {
    }

    @Cacheable("logo")
    public Logo getCurrentLogo() {
        return logoRepository.findAll().stream().findFirst().orElse(null);
    }
}