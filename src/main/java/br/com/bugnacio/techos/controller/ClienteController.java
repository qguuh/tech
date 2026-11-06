package br.com.bugnacio.techos.controller;
 
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
 
import br.com.bugnacio.techos.model.Cliente;
import br.com.bugnacio.techos.repository.ClienteRepository;

 
 
// @Controller: bean do Spring, a recepcao dos pedidos de clientes.
@Controller
// @RequestMapping na classe: o comeco do endereco para TODOS os metodos daqui.
@RequestMapping("/clientes")
public class ClienteController {
 
    // O controller precisa do respository para buscar os clientes no banco de dados.
    private final ClienteRepository repo;
 
    // Injeção de dependencia: o Spring vai criar o repository e passar para o controller.
    public ClienteController(ClienteRepository repo) {
        this.repo = repo;
    }
 
    // @GetMapping vazio = o proprio "/clientes".
    // Endereco completo: http://localhost:8080/clientes
    @GetMapping
    public String listar(Model model) {
 
        // A tela clientes/lista espera uma lista chamada "clientes".
        // Ainda nao temos banco, entao mandamos uma lista VAZIA (List.of()).
        // A tela mostra o "estado vazio". Na aula 5 essa lista vem do banco.
        model.addAttribute("clientes", repo.findAll());
 
        // SEM @ResponseBody: o texto devolvido e o nome da tela.
        // Abre: src/main/resources/templates/clientes/lista.html
        return "clientes/lista";
    }
 
    // Get /clientes/novo: mostra o formulario para criar um novo cliente.
    @GetMapping("/novo")
    public String novo(Model model) {
 
        model.addAttribute("cliente", new Cliente());
       
        return "clientes/form";
    }

    @PostMapping("/salvar")
    public String salvar(Cliente cliente, RedirectAttributes redirect) {
 
     repo.save(cliente);

     redirect.addFlashAttribute ("mensagem", "Cliente salvo com sucesso!");
 
     return "redirect:/clientes";
    }

    @PostMapping("{id}/excluir")
    public String deletar(@PathVariable Long id, RedirectAttributes redirect) {
 
    repo.deleteById(id);
 
    redirect.addFlashAttribute("mensagem", "Cliente excluído com sucesso!");
 
    return "redirect:/clientes";
    }

    @GetMapping("{id}/editar")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes redirect) {
 
    Cliente cliente = repo.findById(id).orElse(null);
 
    if (cliente == null) {
        redirect.addFlashAttribute("mensagem", "Cliente não encontrado.");
        return "redirect:/clientes";
    }
 
    model.addAttribute("cliente", cliente);

    redirect.addFlashAttribute("mensagem", "Cliente editado com sucesso!");
 
    return "clientes/form";
}
    
   
}
 