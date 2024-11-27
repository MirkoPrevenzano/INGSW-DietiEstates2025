package com.example.datatier.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="agente_immobiliare")
public class AgenteImmobiliare {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    
    @ManyToOne
    @JoinColumn(name="id_amministratore", referencedColumnName="id")
    private Amministratore amministratore;

    public AgenteImmobiliare(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public Amministratore getAmministratore() {
        return amministratore;
    }
    public void setAmministratore(Amministratore amministratore) {
        this.amministratore = amministratore;
    }

}
