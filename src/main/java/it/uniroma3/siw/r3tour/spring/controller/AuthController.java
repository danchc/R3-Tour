package it.uniroma3.siw.r3tour.spring.controller;

import it.uniroma3.siw.r3tour.spring.controller.validator.CredentialsValidator;
import it.uniroma3.siw.r3tour.spring.controller.validator.UserValidator;
import it.uniroma3.siw.r3tour.spring.model.Credentials;
import it.uniroma3.siw.r3tour.spring.model.User;
import it.uniroma3.siw.r3tour.spring.service.CredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;


@Controller
public class AuthController {

    @Autowired
    protected CredentialsService credentialsService;

    @Autowired
    protected CredentialsValidator credentialsValidator;

    @Autowired
    protected UserValidator userValidator;

    /**
     * Questo metodo gestisce il reindirizzamento alla pagina di registrazione.
     * @param model
     * @return register-user.html
     */
    @GetMapping("/signup")
    public String getRegisterForm(Model model) {
        model.addAttribute("credentials", new Credentials());
        model.addAttribute("user", new User());
        return "register-user";
    }

    /**
     * Questo metodo gestisce il reindirizzamento alla pagina di login.
     * @return login.html
     */
    @GetMapping("/signin")
    public String getLoginPage(){
        return "login";
    }

    /**
     * Questo metodo gestisce l'invio al server dei dati appena inseriti nel form.
     * @param credentials le credenziali
     * @param bindingResult
     * @param user oggetto utente
     * @return register-user se ci sono errori, sennò success
     */
    @PostMapping("/signup")
    public String register(@Valid @ModelAttribute("credentials") Credentials credentials,
                              @Valid @ModelAttribute("user") User user, BindingResult bindingResult){
        this.credentialsValidator.validate(credentials, bindingResult);
        this.userValidator.validate(user, bindingResult);

        if(bindingResult.hasErrors()){
            return "redirect:/register-user";
        }

        credentials.setUser(user);
        this.credentialsService.inserisci(credentials);
        return "success";
    }
}
