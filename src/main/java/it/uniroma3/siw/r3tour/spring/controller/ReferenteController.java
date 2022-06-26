package it.uniroma3.siw.r3tour.spring.controller;

import it.uniroma3.siw.r3tour.spring.model.Referente;
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

import javax.validation.Valid;

@Controller
public class ReferenteController {

    @Autowired
    protected ReferenteService referenteService;

    @Autowired
    protected DestinazioneService destinazioneService;

    /**
     *
     * @param model
     * @return
     */
    @GetMapping("/add/new/referente")
    public String getFormReferente(Model model){
        model.addAttribute("referente", new Referente());
        model.addAttribute("destinazioni", this.destinazioneService.findAllDestinazioni());
        model.addAttribute("referenti", this.referenteService.findAllReferenti());
        return "admin/cp-referenti";
    }

    /**
     * Questo metodo gestisce l'invio al server dei dati per il salvataggio dell'oggetto REFERENTE
     * @param referente
     * @param bindingResult
     * @param redirectAttributes per inviare al redirect un messaggio in caso di successo
     * @return
     */
    @PostMapping("/new/referente")
    public String addNewReferente(@Valid @ModelAttribute("referente") Referente referente,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes){
        if(!bindingResult.hasErrors()){
            this.referenteService.inserisci(referente);
            redirectAttributes.addFlashAttribute("successmsg", "Il referente è stato aggiunto con successo!");
            return "redirect:/dashboard";
        }
        return "admin/cp-referenti";
    }
}
