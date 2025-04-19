package carservicecrm.controllers;

import carservicecrm.models.WorkerRequest;
import carservicecrm.services.UserService;
import carservicecrm.services.WorkerRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('ROLE_MANUFACTURER','ROLE_ADMIN')")
public class ManufacturerController {
    private final WorkerRequestService workerRequestService;
    private final UserService userService;


    @PostMapping("/manufacturer/add/request")
    public String saveRequest(@RequestParam("email") String email, @RequestParam String questionText) {
        WorkerRequest workerRequest = new WorkerRequest();
        workerRequest.setRequestText(questionText);
        workerRequest.setManufacturer(userService.getUserByEmail(email).getEmployee().getManufacturer());
        workerRequestService.save(workerRequest);
        return "redirect:/profile";
    }
}
