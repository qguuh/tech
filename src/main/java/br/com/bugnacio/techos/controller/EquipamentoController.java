package br.com.bugnacio.techos.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller 
public class EquipamentoController {

    @GetMapping("/equipamentos")
    public String listar(Model model) {
        model.addAttribute("equipamentos", List.of());
        return "equipamentos/lista";
    }


}
