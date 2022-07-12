package it.uniroma3.siw.r3tour.spring.controller;

import it.uniroma3.siw.r3tour.spring.controller.validator.ReferenteValidator;
import it.uniroma3.siw.r3tour.spring.model.Referente;
import it.uniroma3.siw.r3tour.spring.service.DestinazioneService;
import it.uniroma3.siw.r3tour.spring.service.ReferenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class ReferenteController {

    @Autowired
    protected ReferenteService referenteService;

    @Autowired
    protected DestinazioneService destinazioneService;

    @Autowired
    protected ReferenteValidator referenteValidator;

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
                                  RedirectAttributes redirectAttributes, Model model){

        /* controllo se ci sono errori */
        this.referenteValidator.validate(referente, bindingResult);

        if(!bindingResult.hasErrors()){
            this.referenteService.inserisci(referente);
            redirectAttributes.addFlashAttribute("successmsg",
                    "Il referente " + referente.getNome() + " " + referente.getCognome() + " è stato aggiunto con successo!");
            return "redirect:/dashboard";
        }

        model.addAttribute("destinazioni", this.destinazioneService.findAllDestinazioni());
        model.addAttribute("referenti", this.referenteService.findAllReferenti());
        return "admin/cp-referenti";
    }

    /**
     *
     * @param id del referente da eliminare
     * @param redirectAttributes
     * @return
     */
    @GetMapping("/delete/referente/{id}")
    public String deleteReferente(@PathVariable("id") Long id,
                                  RedirectAttributes redirectAttributes){
        Referente referente = this.referenteService.findReferenteById(id);
        this.referenteService.deleteReferente(referente);
        redirectAttributes.addFlashAttribute("successmsg",
                "Il referente " + referente.getNome() + " " + referente.getCognome() + " è stato eliminato con successo!");
        return "redirect:/dashboard";
    }

    /**
     * Il metodo gestisce il reindirizzamento alla pagina per la modifica del referente.
     * @param id
     * @param model
     * @return admin/cp-referenti-update.html
     */
    @GetMapping("/update/referente/{id}")
    public String getUpdateReferente(@PathVariable("id") Long id,
                                   Model model){
        model.addAttribute("referente", this.referenteService.findReferenteById(id));
        model.addAttribute("destinazioni", this.destinazioneService.findAllDestinazioni());
        model.addAttribute("referenti", this.referenteService.findAllReferenti());
        return "admin/cp-referenti-update";
    }

    /**
     * Il metodo viene utilizzato per aggiornare i dati di un certo referente già esistente
     * all'interno del database.
     * @param referente
     * @param bindingResult
     * @param redirectAttributes
     * @param model
     * @return dashboard se non ci sono errori, admin/cp-referenti-update.html altrimenti
     */
    @PostMapping("/update/referente")
    public String updateReferente(@Valid @ModelAttribute("referente") Referente referente,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes, Model model){

        /* controllo se ci sono errori */
        this.referenteValidator.validate(referente, bindingResult);

        if(!bindingResult.hasErrors()){
            this.referenteService.inserisci(referente);
            redirectAttributes.addFlashAttribute("successmsg",
                    "Il referente " + referente.getNome() + " "
                            + referente.getCognome() + " è stato aggiornato con successo!");
            return "redirect:/dashboard";
        }

        model.addAttribute("destinazioni", this.destinazioneService.findAllDestinazioni());
        model.addAttribute("referenti", this.referenteService.findAllReferenti());
        return "admin/cp-referenti-update";
    }

}
