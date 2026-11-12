package br.com.bugnacio.techos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.bugnacio.techos.model.Equipamento;

public interface EquipamentoRepository extends JpaRepository <Equipamento, Long> {

}
