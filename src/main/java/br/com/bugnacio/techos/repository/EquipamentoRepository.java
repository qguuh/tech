package br.com.bugnacio.techos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.bugnacio.techos.model.Equipamento;

@Repository
public interface EquipamentoRepository extends JpaRepository<Equipamento, Long> {

    // Utilizado na listagem para a barra de pesquisa
    List<Equipamento> findByModeloContainingIgnoreCase(String modelo);

    // Verifica duplicidade no momento de um NOVO cadastro
    boolean existsByNumeroSerie(String numeroSerie);

    // Verifica duplicidade no momento da EDIÇÃO (Ignora o próprio equipamento)
    boolean existsByNumeroSerieAndIdNot(String numeroSerie, Long id);

}