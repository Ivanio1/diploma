package carservicecrm.aspects;

import carservicecrm.models.Logo;
import carservicecrm.services.LogoService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@Aspect
@Component
public class LogoAspect {

    @Autowired
    private LogoService logoService;

    @Around("@annotation(AddLogo)")
    public Object addLogo(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        Model model = null;

        for (Object arg : args) {
            if (arg instanceof Model) {
                model = (Model) arg;
                break;
            }
        }

        if (model != null) {
            Logo logo = logoService.getCurrentLogo();
            model.addAttribute("logo", logo);
        }

        return joinPoint.proceed();
    }
}