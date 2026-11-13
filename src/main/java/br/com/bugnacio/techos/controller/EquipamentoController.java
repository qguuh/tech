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
 
import br.com.bugnacio.techos.model.Equipamento;
import br.com.bugnacio.techos.model.TipoEquipamento;
import br.com.bugnacio.techos.repository.ClienteRepository;
import br.com.bugnacio.techos.repository.EquipamentoRepository;
import jakarta.validation.Valid;
 
@Controller
@RequestMapping("/equipamentos")
public class EquipamentoController {
 
    private final EquipamentoRepository repo;
    private final ClienteRepository clienteRepo; // Necessário para carregar a lista de clientes no formulário
 
    public EquipamentoController(EquipamentoRepository repo, ClienteRepository clienteRepo) {
        this.repo = repo;
        this.clienteRepo = clienteRepo;
    }
 
    @GetMapping
    public String listar(
            @RequestParam(value = "busca", required = false) String busca,
            Model model) {
        
        if (busca != null && !busca.isBlank()) {
            model.addAttribute("equipamentos", repo.findByModeloContainingIgnoreCase(busca));
        } else {
            model.addAttribute("equipamentos", repo.findAll(Sort.by("modelo")));
        }
        model.addAttribute("busca", busca);
        return "equipamentos/lista";
    }
 
    // Get /equipamentos/novo: mostra o formulario para criar um novo equipamento.
    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("equipamento", new Equipamento());
        carregarCombos(model); // Carrega listas de clientes e tipos de equipamento
        return "equipamentos/form";
    }
 
    // =============================================================================================
    // SALVAR
 
    @PostMapping("/salvar")
    public String salvar(
            @Valid Equipamento equipamento,
            BindingResult result,
            Model model,
            RedirectAttributes redirect) {
 
        // Verifica se é um cadastro novo.
        boolean novoCadastro = equipamento.getId() == null;
 
        // Verifica se o número de série informado já está sendo utilizado.
        boolean jaExiste;
 
        if (novoCadastro) {
            // Cadastro novo: Verifica se já existe algum equipamento com esse número
            jaExiste = repo.existsByNumeroSerie(equipamento.getNumeroSerie());
        } else {
            // Atualização: Verifica se outro equipamento utiliza esse número
            jaExiste = repo.existsByNumeroSerieAndIdNot(
                    equipamento.getNumeroSerie(),
                    equipamento.getId());
        }
 
        // Se o número já estiver cadastrado, adiciona uma mensagem de erro
        if (jaExiste) {
            result.rejectValue(
                    "numeroSerie",
                    "duplicado",
                    "Já existe um equipamento com esse número de série.");
        }
 
        // Verifica se ocorreu algum erro de validação
        if (result.hasErrors()) {
            carregarCombos(model); // Recarrega os combos antes de voltar para a tela
            return "equipamentos/form";
        }
 
        // Salva no banco de dados
        repo.save(equipamento);
 
        // Define a mensagem de acordo com a operação realizada.
        if (novoCadastro) {
            redirect.addFlashAttribute(
                    "mensagem",
                    "Equipamento cadastrado com sucesso!");
        } else {
            redirect.addFlashAttribute(
                    "mensagem",
                    "Equipamento atualizado com sucesso!");
        }
 
        return "redirect:/equipamentos";
    }
 
    // =============================================================================================
    // EXCLUIR
 
    @PostMapping("/{id}/excluir")
    public String deletar(@PathVariable Long id, RedirectAttributes redirect) {
        repo.deleteById(id);
        redirect.addFlashAttribute("mensagem", "Equipamento deletado com sucesso!");
        return "redirect:/equipamentos";
    }
 
    // =============================================================================================
    // EDITAR
 
    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes redirect) {
        Equipamento equipamento = repo.findById(id).orElse(null);
 
        if (equipamento == null) {
            redirect.addFlashAttribute("mensagem", "Equipamento não encontrado.");
            return "redirect:/equipamentos";
        }
 
        model.addAttribute("equipamento", equipamento);
        carregarCombos(model);
        return "equipamentos/form";
    }
 
    // =============================================================================================
    // MÉTODOS AUXILIARES
 
    private void carregarCombos(Model model) {
        model.addAttribute("clientes", clienteRepo.findAll(Sort.by("nome")));
        model.addAttribute("tipos", TipoEquipamento.values());
    }
}
 