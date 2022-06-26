package it.uniroma3.siw.r3tour.spring.controller;

import it.uniroma3.siw.r3tour.spring.model.Destinazione;
import it.uniroma3.siw.r3tour.spring.service.ContinenteService;
import it.uniroma3.siw.r3tour.spring.service.DestinazioneService;
import it.uniroma3.siw.r3tour.spring.service.ReferenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class DestinazioneController {

    @Autowired
    protected DestinazioneService destinazioneService;

    @Autowired
    protected ContinenteService continenteService;

    @Autowired
    protected ReferenteService referenteService;

    /**
     *
     * @param model
     * @return
     */
    @GetMapping("/destinazioni")
    public String getPageDestinazioni(Model model) {
        model.addAttribute("destinazioni", this.destinazioneService.findAllDestinazioni());
        return "destinazioni";
    }

    /**
     *
     * @param model
     * @return
     */
    @GetMapping("/add/new/destinazione")
    public String getFormDestinazione(Model model){
        model.addAttribute("continenti", this.continenteService.findAllContinenti());
        model.addAttribute("referenti", this.referenteService.findAllReferenti());
        model.addAttribute("destinazioni", this.destinazioneService.findAllDestinazioni());
        model.addAttribute("destinazione", new Destinazione());
        return "admin/cp-destinazioni";
    }

    /**
     *
     * @param redirectAttributes
     * @param destinazione
     * @param bindingResult
     * @return
     */
    @PostMapping("new/destinazione")
    public String addNewDestinazione(RedirectAttributes redirectAttributes,
                                     @ModelAttribute("destinazione") Destinazione destinazione,
                                     BindingResult bindingResult){
        if(!bindingResult.hasErrors()){
            this.destinazioneService.inserisci(destinazione);
            redirectAttributes.addFlashAttribute("successmsg", "La destinazione è stata aggiunta con successo!");
            return "redirect:/dashboard";
        }
        return "cp-destinazioni";
    }
}
