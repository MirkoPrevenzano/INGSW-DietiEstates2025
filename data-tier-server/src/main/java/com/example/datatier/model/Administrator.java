package com.example.datatier.model;

import java.util.List;


import jakarta.persistence.*;


@Entity
@Table(name = "administrator")
public class Administrator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String agencyName;

    @ManyToOne
    @JoinColumn(name = "id_responsible")
    private Administrator responsible;

    @OneToMany(mappedBy = "responsible")
    private List<Administrator> managers;

    public Administrator() {
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

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public List<Administrator> getManagers() {
        return managers;
    }
    public void setManagers(List<Administrator> managers) {
        this.managers = managers;
    }
    public void addManager(Administrator administrator){
        managers.add(administrator);
    }

    public Administrator getResponsible() {
        return responsible;
    }
    public void setResponsible(Administrator responsible) {
        this.responsible = responsible;
    }
}
