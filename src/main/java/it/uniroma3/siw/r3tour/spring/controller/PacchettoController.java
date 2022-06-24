package it.uniroma3.siw.r3tour.spring.controller;

import it.uniroma3.siw.r3tour.spring.service.PacchettoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PacchettoController {

    @Autowired
    protected PacchettoService pacchettoService;

    /**
     * Questo metodo ci reindirizza alla pagina dei pacchetti con la lista dei
     * pacchetti salvati nel database.
     * @param model
     * @return pacchetti.html
     */
    @GetMapping("/pacchetti")
    public String getPagePacchetti(Model model){
        model.addAttribute("pacchettiTotali",
                this.pacchettoService.findAllPacchetti());

        return "pacchetti";
    }
}
