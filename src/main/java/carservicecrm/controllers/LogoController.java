package carservicecrm.controllers;

import carservicecrm.models.Logo;
import carservicecrm.services.LogoService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class LogoController {

    private final LogoService logoService;

    @PostMapping("/logo")
    public String handleLogoUpload(@RequestParam("file") MultipartFile file) throws IOException {
        Logo logo = new Logo();
        logo.setFilename(file.getOriginalFilename());
        logo.setData(file.getBytes());
        logoService.saveLogo(logo);
        logoService.evict();
        return "redirect:/";
    }

    @GetMapping("/logo")
    public ResponseEntity<?> getLogoById() {
        Logo logo = logoService.getCurrentLogo();
        return ResponseEntity.ok()
                .header("fileName", logo.getFilename())
                .body(new InputStreamResource(new ByteArrayInputStream(logo.getData())));
    }

}