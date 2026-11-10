package br.com.bugnacio.techos.controller;
 
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
 
import br.com.bugnacio.techos.model.Cliente;
import br.com.bugnacio.techos.repository.ClienteRepository;
import jakarta.validation.Valid;
 
 
 
@Controller
@RequestMapping ("/clientes")
 
 
 
public class ClienteController {
    private final ClienteRepository repo;
 
    public ClienteController(ClienteRepository repo) {
        this.repo = repo;
    }
 
 
@GetMapping
    public String listar(
        @RequestParam(value = "buscar", required = false) String busca,
        Model model){
                       
        if (busca != null && !busca.isBlank()) {
            model.addAttribute("clientes", repo.findByNomeContainingIgnoreCase(busca));
        } else {
            model.addAttribute("clientes", repo.findAll(Sort.by("nome")));
        }
 
        model.addAttribute("busaca", busca);
        return "clientes/lista";
    }
 
    //Get /cliente/novo: mostra o formulario para criar um novo cliente.
    @GetMapping("/novo")
 
    public String novo(Model model) {
        //A tela clientes/form espera um objeto chamado "cliente".
        //mandamos um cliente VAZIO (new Cliente()).
        //A tela mostra o "estado vazio". Na aula 5 esse cliente vem do banco.
        model.addAttribute("cliente", new Cliente());
 
 
        return "clientes/form";
    }
 
 
    // =============================================================================================
    // SALVAR
 
 
 
    @PostMapping("/salvar")
    public String salvar(@Valid Cliente cliente,
                         BindingResult result,
                         RedirectAttributes redirect) {
                            
        boolean jaExiste;
        if (cliente.getId() == null) {
           
            jaExiste = repo.existsByTelefone(cliente.getTelefone());
        } else {
           
            jaExiste = repo.existsByTelefoneAndIdNot(cliente.getTelefone(), cliente.getId());
        } 
        if (jaExiste) {
            result.rejectValue("Telefone", "duplicado", "Já existente umcliente com esse telefone.");
        }

        if (result.hasErrors()) {
            return "clientes/form";
        }
 
        repo.save(cliente);
        redirect.addFlashAttribute("mensagem", "Cliente salvo com sucesso");
        return "redirect:/clientes";
    }
 
 
    // =============================================================================================
    // EXCLUIR
 
@PostMapping("/{id}/excluir")
public String deletar(@PathVariable Long id, RedirectAttributes redirect) {
 
    repo.deleteById(id);
 
    redirect.addFlashAttribute("mensagem", "Cliente deletado com sucesso!");
 
    return "redirect:/clientes";
}
 
 
    // =============================================================================================
    // EDITAR
 
  @GetMapping("{id}/editar")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes redirect) {
 
    Cliente cliente = repo.findById(id).orElse(null);
 
    if (cliente == null) {
        redirect.addFlashAttribute("mensagem", "Cliente não encontrado.");
        return "redirect:/clientes";
    }
 
    model.addAttribute("cliente", cliente);
 
    return "clientes/form";
}
 
 
 
 
 
}
 
 
 