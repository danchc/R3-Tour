package it.uniroma3.siw.r3tour.spring.controller;

import it.uniroma3.siw.r3tour.spring.controller.validator.CredentialsValidator;
import it.uniroma3.siw.r3tour.spring.controller.validator.UserValidator;
import it.uniroma3.siw.r3tour.spring.model.ConfirmationToken;
import it.uniroma3.siw.r3tour.spring.model.Credentials;
import it.uniroma3.siw.r3tour.spring.model.User;
import it.uniroma3.siw.r3tour.spring.repository.ConfirmationTokenRepository;
import it.uniroma3.siw.r3tour.spring.service.CredentialsService;
import it.uniroma3.siw.r3tour.spring.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;


@Controller
public class AuthController {

    @Autowired
    protected CredentialsService credentialsService;

    @Autowired
    protected CredentialsValidator credentialsValidator;

    @Autowired
    protected UserValidator userValidator;

    @Autowired
    protected ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    protected EmailSenderService emailSenderService;

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
    @GetMapping("/login")
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
        //creo il token per la conferma dell'email
        ConfirmationToken confirmationToken = new ConfirmationToken(credentials);
        this.confirmationTokenRepository.save(confirmationToken);
        //invio l'email di conferma
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(credentials.getEmail());
        simpleMailMessage.setSubject("Conferma la registrazione!");
        simpleMailMessage.setFrom("r3.cate@gmail.com");
        simpleMailMessage.setText("Per confermare clicca : "
                + "http://localhost:8081/confirm-account?token="
                + confirmationToken.getConfirmationToken());
        emailSenderService.sendEmail(simpleMailMessage);
        return "success";
    }

    /**
     * Questo metodo gestisce il GET e il POST per quanto riguarda la conferma dell'account appena creato.
     * @param model
     * @param confirmationToken il token per la conferma
     * @return la pagina di successo, pagina di errore se ci sono errori
     */
    @GetMapping("/confirm-account")
    @PostMapping("/confirm-account")
    public String confirmEmail(Model model,
                               @RequestParam("token") String confirmationToken){
        ConfirmationToken token = this.confirmationTokenRepository.findByConfirmationToken(confirmationToken);
        if(token != null) {
            Credentials credentials = this.credentialsService.findByEmail(token.getCredentials().getEmail());
            credentials.setEnabled(true);
            this.credentialsService.inserisci(credentials);
            return "success";
        } else {
            return "error";
        }
    }

    @GetMapping("/default")
    public String getDefault(Model model,
                             HttpSession session) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Credentials credentials = credentialsService.findCredentialsByUsername(userDetails.getUsername());
        session.setAttribute("role", credentials.getRuolo());
        return "redirect:/";
    }

    /* gestisce il logout dell'utente corrente */
    @GetMapping("/logout")
    public String getLoggedOut() {
        return "index";
    }
}