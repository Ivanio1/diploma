package carservicecrm.controllers;


import carservicecrm.aspects.AddAppearanceSettings;
import carservicecrm.aspects.AddLogo;
import carservicecrm.models.Purchase;
import carservicecrm.repositories.PurchaseRepository;
import carservicecrm.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.time.temporal.IsoFields;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AnalyticsController {
    private final UserService userService;
    private final PurchaseRepository purchaseRepository;

    @GetMapping("/admin/analytics/monthly")
    @AddAppearanceSettings
    @AddLogo
    public String getAnalytics(Model model, Principal principal) {
        List<Purchase> orders = purchaseRepository.findAll();

        int[] monthlyCounts = new int[12];
        for (Purchase order : orders) {
            int month = order.getCreatedat().getMonthValue() - 1;
            monthlyCounts[month]++;
        }
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        model.addAttribute("monthlyCounts", monthlyCounts);
        return "monthly";
    }

    @GetMapping("/admin/analytics/weekly")
    @AddAppearanceSettings
    @AddLogo
    public String getWeeklyAnalytics(Model model, Principal principal) {
        List<Purchase> orders = purchaseRepository.findAll();

        int[] weeklyCounts = new int[53];
        for (Purchase order : orders) {
            int week = order.getCreatedat().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR) - 1;
            weeklyCounts[week]++;
        }
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        model.addAttribute("weeklyCounts", weeklyCounts);
        return "weekly";
    }

    @GetMapping("/admin/analytics/daily")
    @AddAppearanceSettings
    @AddLogo
    public String getDailyAnalytics(Model model, Principal principal) {
        List<Purchase> orders = purchaseRepository.findAll();

        int[] dailyCounts = new int[31];
        for (Purchase order : orders) {
            int day = order.getCreatedat().getDayOfMonth() - 1;
            dailyCounts[day]++;
        }

        model.addAttribute("user", userService.getUserByPrincipal(principal));
        model.addAttribute("dailyCounts", dailyCounts);
        return "daily";
    }

}
