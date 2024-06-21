package com.taxePro.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
public class Recu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @OneToMany(mappedBy = "recu", cascade = CascadeType.ALL)
    private List<RecuProduit> recuProduits;

    private double TotalTaxes ;
    private double TotalPrice ;
    private LocalDateTime pucharseTime ;
}
