
package com.dietiEstates.backend.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;


@Entity(name = "Agency")
@Table(name = "agency")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@ToString(exclude = "administrator")
public class Agency 
{
    @Id
    private Long agencyId;
    
    @NonNull
    @Column(nullable = false, 
            updatable = true)
    private String name;

    @NonNull
    @Column(nullable = false, 
            updatable = true)
    private String businessName;
    
    @NonNull
    @Column(nullable = false, 
            updatable = true)
    private String vatNumber;


    
    @MapsId
    @OneToOne(fetch = FetchType.LAZY,
              cascade = {},
              orphanRemoval = false)
    @JoinColumn(name = "admin_id",
                nullable = false,
                updatable = true,
                foreignKey = @ForeignKey(name = "agency_to_admin_fk"))
    private Administrator administrator;
}