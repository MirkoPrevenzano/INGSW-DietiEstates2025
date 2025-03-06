package com.example.datatier.model;

import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@Entity
@Table(name = "administrator")
public class Administrator implements User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String agencyName;

    //riferimento responsabile e gruppo di manager di cui eventualmente è responsabile
    @ManyToOne
    @JoinColumn(name = "id_responsible")
    private Administrator responsible;

    @OneToMany(mappedBy = "responsible")
    private List<Administrator> managers;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        throw new UnsupportedOperationException("Unimplemented method 'getAuthorities'");
    }

    public void addManager(Administrator newAdministrator) {
       managers.add(newAdministrator);
    }

    
}
