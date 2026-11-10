package br.com.bugnacio.techos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// @Entity: essa classe representa uma tabela no banco de dados.
@Entity
public class Cliente {
    // @id: o campo que representa a chave primaria da tabela.
    // @GeneratedValue: o valor da chave primaria e gerado automaticamente.
@Id 
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

//@notBlank: o nome do cliente não pode ser vazio
//@size: tamanho. message: o texto que aparece ao lado do campo

// Cada atributo vira uma coluna na tabela. O nome da coluna e o mesmo do atributo.
private String nome;
@NotBlank (message = "O nome do cliente é obrigatório")
@Size (max = 100, message = "O nome do cliente não pode ter mais de 100 caracteres")

private String telefone;
@NotBlank (message = "Informe o telefone do cliente")
@Size (max = 15, message = "o número de telefone não pode ter mais de 15 caracteres")


private String email;
@Email (message = "Informe um email válido")

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