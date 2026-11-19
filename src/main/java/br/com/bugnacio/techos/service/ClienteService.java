package br.com.bugnacio.techos.service;

import br.com.bugnacio.techos.exception.RegraDeNegocioException;
import br.com.bugnacio.techos.repository.ClienteRepository;
import br.com.bugnacio.techos.repository.EquipamentoRepository;


// @Service informa ao Spring que esta Classe contém regras de negócio.
public class ClienteService {

    private final ClienteRepository clienteRepo;
    private final EquipamentoRepository equipRepo;

    public ClienteService (ClienteRepository clienteRepo, EquipamentoRepository equipRepo) {

        // Guarda o repositório de clientes no atributo da classe
        this.clienteRepo = clienteRepo;

        // Guarda o repositório do equipamento no atributo da classe
        this.equipRepo = equipRepo;
    }

    // metodo responsável por excluir o cliente pelo seu ID
    public void excluir (Long id) {

        // Antes de excluir o cliene, ele verifica se existen equipamentos cadastrados relacionados a ele

        if (equipRepo.existsByClienteId(id)) {

            // se existir algum equipamento vinculado ao cliente lança uma exceção que a exclusão seja realizada
            throw new RegraDeNegocioException("Este cliente tem equipamentos cadastrados");
        }

        // se o cliente não possuir equipamento vinculado permite realizar a exclusão pelo ID informado
        clienteRepo.deleteById(id);
    }

}
