package it.uniroma3.siw.r3tour.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
}
