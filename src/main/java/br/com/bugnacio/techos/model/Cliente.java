package br.com.bugnacio.techos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

// @Entity: essa classe representa uma tabela no banco de dados.
@Entity
public class Cliente {
    // @id: o campo que representa a chave primaria da tabela.
    // @GeneratedValue: o valor da chave primaria e gerado automaticamente.
@Id 
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

// Cada atributo vira uma coluna na tabela. O nome da coluna e o mesmo do atributo.
private String nome;
private String telefone;
private String email;

// Construtor padrao (sem argumentos) necessario para o JPA.
public Cliente() {

}

public Long getId() {
    return id;
}

public void setId(Long id) {
    this.id = id;
}

public String getNome() {
    return nome;
}

public void setNome(String nome) {
    this.nome = nome;
}

public String getTelefone() {
    return telefone;
}

public void setTelefone(String telefone) {
    this.telefone = telefone;
}

public String getEmail() {
    return email;
}

public void setEmail(String email) {
    this.email = email;
}

}