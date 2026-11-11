package br.com.bugnacio.techos.model;
 
import jakarta.persistence.Entity; // 1. Este import resolve o erro vermelho!
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;  // Import para o relacionamento
import jakarta.persistence.ManyToOne; // Import para a chave estrangeira
 
@Entity
public class Equipamento {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String tipo;
    private String marca;
    private String modelo;
    private String numeroSerie;
 
    // 2. AQUI ESTÁ O RELACIONAMENTO (Muitos equipamentos para 1 Cliente)
    @ManyToOne
    @JoinColumn(name = "cliente_id") // Cria a coluna cliente_id no banco de dados
    private Cliente cliente;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
 
    
    
}
