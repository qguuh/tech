package br.com.bugnacio.techos.controller;
 
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.bugnacio.techos.model.Equipamento;
import br.com.bugnacio.techos.model.TipoEquipamento;
import br.com.bugnacio.techos.repository.ClienteRepository;
import br.com.bugnacio.techos.repository.EquipamentoRepository;
import jakarta.validation.Valid;
 
@Controller
@RequestMapping("/equipamentos")
public class EquipamentoController {
 
    // 1. Recebendo os dois repositories exigidos
    private final EquipamentoRepository equipamentoRepository;
    private final ClienteRepository clienteRepository;
 
    // Injeção de dependência pelo construtor (recomendado pelo Spring)
    public EquipamentoController(EquipamentoRepository equipamentoRepository, ClienteRepository clienteRepository) {
        this.equipamentoRepository = equipamentoRepository;
        this.clienteRepository = clienteRepository;
    }
 
    // 2. Método listar()
    @GetMapping
    public String listar(Model model) {
        // Consulta todos os equipamentos e envia para a tela
        model.addAttribute("equipamentos", equipamentoRepository.findAll());
        return "equipamentos/lista";
    }
 
    // 3. Método novo()
    @GetMapping("/novo")
    public String novo(Model model) {
        // Envia um objeto vazio para o formulário
        model.addAttribute("equipamento", new Equipamento());
        
        // Chama o método auxiliar para preencher as listas de seleção (clientes e tipos)
        carregarCombos(model);
        
        return "equipamentos/form";
    }
 
    // 4. Método salvar()
    @PostMapping("/salvar")
    public String salvar(@Valid Equipamento equipamento, BindingResult result, Model model, RedirectAttributes attributes) {
        
        // Verifica se ocorreram erros de validação (ex: campos em branco)
        if (result.hasErrors()) {
            // A PEGADINHA: Recarrega as listas antes de devolver a página de erro
            carregarCombos(model);
            return "equipamentos/form"; // Retorna para o form mostrando os erros
        }
 
        // Se estiver tudo certo, salva no banco
        equipamentoRepository.save(equipamento);
        
        // Envia a mensagem de sucesso que aparecerá na listagem
        attributes.addFlashAttribute("mensagem", "Equipamento salvo com sucesso!");
        
        // Redireciona para a rota /equipamentos (método listar)
        return "redirect:/equipamentos";
    }
 
    // 5. Método auxiliar carregarCombos() (Dica do Professor)
    private void carregarCombos(Model model) {
        // Carrega os clientes organizados em ordem alfabética pelo nome
        model.addAttribute("clientes", clienteRepository.findAll(Sort.by("nome")));
        
        // Recupera as opções do Enum (Computador, Celular, etc.)
        model.addAttribute("tipos", TipoEquipamento.values());
    }

        // Método para Editar
    @GetMapping("/{id}/editar")
    public String editar(@PathVariable("id") Long id, Model model) {
        // Busca o equipamento no banco de dados pelo ID
        Equipamento equipamento = equipamentoRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        
        // Envia o equipamento encontrado para preencher o formulário
        model.addAttribute("equipamento", equipamento);
        
        // Recarrega as listas de clientes e tipos para os campos de seleção
        carregarCombos(model);
        
        return "equipamentos/form";
    }
 
    // Método para Excluir
    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes redirect) {
        // Exclui o equipamento do banco de dados
        equipamentoRepository.deleteById(id);
        
        // Envia a mensagem de sucesso
        redirect.addFlashAttribute("mensagem", "Equipamento excluído com sucesso!");
        
        return "redirect:/equipamentos";
    }

}