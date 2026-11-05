package br.com.bugnacio.techos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.bugnacio.techos.model.Cliente;

// Interface (Não é classe): Não escrevemos o corpo da classe, o spring Data JPA ja faz isso para nós
// A interface JpaRepository já possui métodos para salvar, buscar, atualizar e deletar registros no banco de dados
public interface ClienteRepository extends JpaRepository <Cliente, Long> {

}
