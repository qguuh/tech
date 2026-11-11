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
            @RequestParam(value = "busca", required = false) String busca,
            Model model) {
        if (busca != null && !busca.isBlank()) {
            model.addAttribute("clientes", repo.findByNomeContainingIgnoreCase(busca));
        } else {
            model.addAttribute("clientes", repo.findAll(Sort.by("nome")));
        }
        model.addAttribute("busca", busca);
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

    // Recebe os dados enviados pelo formulário, valida e salva no banco.
    @PostMapping("/salvar")
    public String salvar(
            @Valid Cliente cliente,
            BindingResult result,
            RedirectAttributes redirect) {

        // Verifica se é um cadastro novo.
        // Se o ID for null, significa que ainda não existe registro no banco.
        boolean novoCadastro = cliente.getId() == null;

        // Verifica se o telefone informado já está sendo utilizado.
        boolean jaExiste;

        if (novoCadastro) {

            // Cadastro novo:
            // Verifica se já existe algum cliente com esse telefone.
            jaExiste = repo.existsByTelefone(cliente.getTelefone());

        } else {

            // Atualização:
            // Verifica se outro cliente utiliza esse telefone.
            // O próprio cliente que está sendo editado é desconsiderado pelo ID.
            jaExiste = repo.existsByTelefoneAndIdNot(
                    cliente.getTelefone(),
                    cliente.getId());
        }

        // Se o telefone já estiver cadastrado,
        // adiciona uma mensagem de erro no campo telefone.
        if (jaExiste) {
            result.rejectValue(
                    "telefone",
                    "duplicado",
                    "Já existe um cliente com esse telefone.");
        }

        // Verifica se ocorreu algum erro de validação.
        // Pode ser um campo obrigatório vazio, e-mail inválido
        // ou telefone duplicado, por exemplo.
        if (result.hasErrors()) {

            // Retorna ao formulário para o usuário corrigir os dados.
            // Não salva e não redireciona para outra página.
            return "clientes/form";
        }

        // Se chegou até aqui, significa que os dados estão válidos.
        // O Spring Data JPA identifica se deve inserir ou atualizar
        // conforme o ID recebido.
        repo.save(cliente);

        // Define a mensagem de acordo com a operação realizada.
        if (novoCadastro) {

            // Mensagem exibida quando um novo cliente é cadastrado.
            redirect.addFlashAttribute(
                    "mensagem",
                    "Cliente cadastrado com sucesso!");

        } else {

            // Mensagem exibida quando um cliente existente é atualizado.
            redirect.addFlashAttribute(
                    "mensagem",
                    "Cliente atualizado com sucesso!");
        }

        // Redireciona para a listagem de clientes.
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
 
 
 