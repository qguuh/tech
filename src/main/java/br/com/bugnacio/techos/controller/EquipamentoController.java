package br.com.bugnacio.techos.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.bugnacio.techos.model.Equipamento;
import br.com.bugnacio.techos.repository.EquipamentoRepository;

@Controller 
@RequestMapping ("/equipamentos")
public class EquipamentoController {

    private final EquipamentoRepository repo;
 
    public EquipamentoController(EquipamentoRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/equipamentos")
    public String listar(Model model) {
        model.addAttribute("equipamentos", List.of());
        return "equipamentos/lista";
    }

    @GetMapping("/novo")
 
    public String novo(Model model) {
        model.addAttribute("equipamento", new Equipamento());
 
        return "equipamentos/form";
    }



}
