package it.uniroma3.siw.r3tour.spring.controller;

import it.uniroma3.siw.r3tour.spring.service.CredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired
    protected CredentialsService credentialsService;

    @GetMapping("/account")
    public String getUserAccountPage(HttpSession httpSession){
        httpSession.setAttribute("role", this.credentialsService.getCredentialsAuthenticated().getRuolo());
        return "account";
    }
}
