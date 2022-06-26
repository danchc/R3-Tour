package it.uniroma3.siw.r3tour.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String getIndex() {
        return "index";
    }

    @GetMapping("/chisiamo")
    public String getChiSiamo(){
        return "chisiamo";
    }
}
