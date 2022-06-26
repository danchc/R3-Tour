package it.uniroma3.siw.r3tour.spring.controller;

import it.uniroma3.siw.r3tour.spring.model.Pacchetto;
import it.uniroma3.siw.r3tour.spring.service.DestinazioneService;
import it.uniroma3.siw.r3tour.spring.service.PacchettoService;
import it.uniroma3.siw.r3tour.spring.service.ReferenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class PacchettoController {

    @Autowired
    protected PacchettoService pacchettoService;

    @Autowired
    protected DestinazioneService destinazioneService;

    @Autowired
    protected ReferenteService referenteService;

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

    /**
     *
     * @param model
     * @return
     */
    @GetMapping("/add/new/pacchetto")
    public String getFormPacchetto(Model model) {
        model.addAttribute("pacchetti", this.pacchettoService.findAllPacchetti());
        model.addAttribute("destinazioni", this.destinazioneService.findAllDestinazioni());
        model.addAttribute("referenti", this.referenteService.findAllReferenti());
        model.addAttribute("pacchetto", new Pacchetto());
        return "admin/cp-pacchetti";
    }

    @PostMapping("/new/pacchetto")
    public String addNewPacchetto(@Valid @ModelAttribute("pacchetto") Pacchetto pacchetto,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes, Model model){
        if(!bindingResult.hasErrors()){
            this.pacchettoService.inserisci(pacchetto);
            redirectAttributes.addFlashAttribute("successmsg",
                    "Il pacchetto è stato aggiunto correttamente.");
            return "redirect:/dashboard";
        }

        model.addAttribute("pacchetti", this.pacchettoService.findAllPacchetti());
        model.addAttribute("destinazioni", this.destinazioneService.findAllDestinazioni());
        model.addAttribute("referenti", this.referenteService.findAllReferenti());
        return "admin/cp-pacchetti";
    }
}
