package it.uniroma3.siw.r3tour.spring.controller;

import it.uniroma3.siw.r3tour.spring.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MainController {

    /**
     * Il metodo viene utilizzato per il reindirizzamento alla pagina principale del sito.
     * @return index.html
     */
    @GetMapping("/")
    public String getIndex() {
        return "index";
    }

    /**
     * Il metodo viene utilizzato per il reindirizzamento alla pagina ' Chi siamo ' del sito.
     * @return chisiamo.html
     */
    @GetMapping("/chisiamo")
    public String getChiSiamo(){
        return "chisiamo";
    }

    /**
     * Il metodo viene utilizzato per il reindirizzamento alla pagina dei termini e condizioni del sito.
     * @return terms.html
     */
    @GetMapping("/terms")
    public String getTermini(){
        return "terms";
    }

    /*
    @PostMapping("/submit/message")
    public String contactUs(RedirectAttributes redirectAttributes,
                            @RequestParam("email") String email, @RequestParam("message") String message){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setFrom(email);
        simpleMailMessage.setText(message);
        simpleMailMessage.setTo("r3.cate@gmail.com");
        this.emailSenderService.sendEmail(simpleMailMessage);
        redirectAttributes.addFlashAttribute("successmsg", "Il messaggio è stato inviato con successo! Ti risponderemo il prima possibile.");
        return "redirect:/chisiamo";
    }*/
}
