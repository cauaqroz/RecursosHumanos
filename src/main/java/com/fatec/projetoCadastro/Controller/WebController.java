package com.fatec.projetoCadastro.Controller;

import org.springframework.web.bind.annotation.GetMapping;

public class WebController {
    
    @GetMapping("/")
    public String index() {
        return "index";
    }
}
