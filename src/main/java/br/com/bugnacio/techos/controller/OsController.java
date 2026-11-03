package br.com.bugnacio.techos.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller 
public class OsController {

    @GetMapping("/os")
    public String listar(Model model) {
        model.addAttribute("ordens", List.of());
        return "os/lista";
    }

}
