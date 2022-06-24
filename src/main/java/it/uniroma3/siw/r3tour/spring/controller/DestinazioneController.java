package it.uniroma3.siw.r3tour.spring.controller;

import it.uniroma3.siw.r3tour.spring.service.DestinazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DestinazioneController {

    @Autowired
    protected DestinazioneService destinazioneService;

    @GetMapping("/destinazioni")
    public String getPageDestinazioni(Model model) {
        model.addAttribute("destinazioni", this.destinazioneService.findAllDestinazioni());
        return "destinazioni";
    }
}
