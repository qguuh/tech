package br.com.bugnacio.techos.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import br.com.bugnacio.techos.repository.ClienteRepository;



@Controller 
public class ClienteController {

// O controller precisa do repository para bscar os clientes no banco de dados
private final ClienteRepository repo;

// Injeção de dependência: o string vai criar o repository e passar para o controller
public ClienteController(ClienteRepository repo) {
    this.repo = repo;
}

@GetMapping("/clientes")
public String listar(Model model) {
    model.addAttribute("clientes", repo.findAll());
    return "clientes/lista";
}

}
