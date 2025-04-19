package carservicecrm.aspects;

import carservicecrm.models.AppearanceSettings;
import carservicecrm.services.AppearanceSettingsService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.Objects;

@Aspect
@Component
public class AppearanceSettingsAspect {

    @Autowired
    private AppearanceSettingsService appearanceSettingsService;

    @Around("@annotation(AddAppearanceSettings)")
    public Object addAppearanceSettings(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        Model model = null;

        for (Object arg : args) {
            if (arg instanceof Model) {
                model = (Model) arg;
                break;
            }
        }

        if (model != null) {
            AppearanceSettings settings = appearanceSettingsService.getSettings();
            if (settings == null || settings.getSiteName() == null || Objects.equals(settings.getSiteName(), "")) {
                settings = new AppearanceSettings();
                settings.setSiteName("CRM");
            }
            model.addAttribute("appearanceSettings", settings);
        }

        return joinPoint.proceed();
    }
}