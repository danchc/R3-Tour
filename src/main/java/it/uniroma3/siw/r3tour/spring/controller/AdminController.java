package it.uniroma3.siw.r3tour.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/dashboard")
    public String getDashboardPage() {
        return "admin/controlpanel";
    }
}
