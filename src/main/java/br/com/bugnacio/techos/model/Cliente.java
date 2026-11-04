package br.com.bugnacio.techos.model;

import org.aspectj.lang.annotation.AfterReturning;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Cliente {

@Id 
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

// cada atributo vira uma coluna na tabela. O nome da coluna e o mesmo do atributo
private String nome;
private String telefone;
private String email;

// Construtor padrão (sem argumentos) necessário para o JPA
public Cliente() {

    

}

    // Getters e Setters

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
