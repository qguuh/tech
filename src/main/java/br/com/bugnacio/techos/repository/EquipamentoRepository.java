package br.com.bugnacio.techos.repository;
 
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.bugnacio.techos.model.Equipamento;
 
@Repository
public interface EquipamentoRepository extends JpaRepository<Equipamento, Long> {
    
    // Método para verificar duplicidade num cadastro novo
    boolean existsByNumeroSerie(String numeroSerie);
    
    // Método para verificar duplicidade numa edição (ignora o próprio equipamento)
    boolean existsByNumeroSerieAndIdNot(String numeroSerie, Long id);

    List<Equipamento> findByModeloContainingIgnoreCase(String modelo);
    
}