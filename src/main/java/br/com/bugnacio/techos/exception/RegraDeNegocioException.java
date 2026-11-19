package br.com.bugnacio.techos.exception;

// Classe responsavel por criar uma exceção personalizado para representar erros relacionados as regras de negócios do sistema

// construtor
public class RegraDeNegocioException extends RuntimeException {

    public RegraDeNegocioException (String mensagem) {

    super(mensagem);

    }
}
