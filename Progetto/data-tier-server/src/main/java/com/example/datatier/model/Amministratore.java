package com.example.datatier.model;

import java.util.List;

import jakarta.persistence.*;


@Entity
@Table(name = "amministratore")
public class Amministratore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String nomeAgenzia;

    @ManyToOne
    @JoinColumn(name = "id_responsabile")
    private Amministratore responsabile;

    @OneToMany(mappedBy = "responsabile")
    private List<Amministratore> gestori;

    public Amministratore() {
    }

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

    public String getNomeAgenzia() {
        return nomeAgenzia;
    }

    public void setNomeAgenzia(String nomeAgenzia) {
        this.nomeAgenzia = nomeAgenzia;
    }

    public List<Amministratore> getGestori() {
        return gestori;
    }
    public void setGestori(List<Amministratore> gestori) {
        this.gestori = gestori;
    }

    public Amministratore getResponsabile() {
        return responsabile;
    }
    public void setResponsabile(Amministratore responsabile) {
        this.responsabile = responsabile;
    }
}
