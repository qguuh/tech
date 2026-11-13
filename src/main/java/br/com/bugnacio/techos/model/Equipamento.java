package br.com.bugnacio.techos.model;
 
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
 
@Entity
public class Equipamento {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // Configuração do Enum
    @Enumerated(EnumType.STRING)
    @NotBlank (message = "Informe o tipo do equipamento")
    private TipoEquipamento tipo;

    @NotBlank (message = "Informe a marca do equipamento")
    private String marca;

    @NotBlank (message = "Informe o modelo do equipamento")
    private String modelo;
    
    @Column (unique = true)
    @NotBlank (message = "Informe o número de série do equipamento")
    private String numeroSerie;
 
    // Relacionamento com Cliente
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    
 
    // --- GETTERS E SETTERS ---
 
    public Long getId() {
        return id;
    }
 
    public void setId(Long id) {
        this.id = id;
    }
 
    public TipoEquipamento getTipo() {
        return tipo;
    }
 
    public void setTipo(TipoEquipamento tipo) {
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

    public boolean existsByNumeroSerie(String numeroSerie) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean existsByNumeroSerieAndIdNot(String numeroSerie, Long id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}