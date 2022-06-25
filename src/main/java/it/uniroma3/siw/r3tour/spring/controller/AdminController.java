package it.uniroma3.siw.r3tour.spring.controller;

import it.uniroma3.siw.r3tour.spring.service.CredentialsService;
import it.uniroma3.siw.r3tour.spring.service.DestinazioneService;
import it.uniroma3.siw.r3tour.spring.service.ReferenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @Autowired
    protected CredentialsService credentialsService;

    @Autowired
    protected DestinazioneService destinazioneService;

    @Autowired
    protected ReferenteService referenteService;

    @GetMapping("/dashboard")
    public String getDashboardPage(Model model) {
        model.addAttribute("utentiRegistrati", this.credentialsService.findAllUtentiRegistrati());
        model.addAttribute("destinazioni", this.destinazioneService.findAllDestinazioni());
        model.addAttribute("referenti", this.referenteService.findAllReferenti());
        return "admin/controlpanel";
    }
}
