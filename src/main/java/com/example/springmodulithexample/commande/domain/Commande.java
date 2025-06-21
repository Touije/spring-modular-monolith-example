package com.example.springmodulithexample.commande.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long acheteurId;

    private Long produitId;

    private int quantite;

    private LocalDateTime dateCommande;

    public Commande(Long acheteurId, Long produitId, int quantite) {
        this.acheteurId = acheteurId;
        this.produitId = produitId;
        this.quantite = quantite;
        this.dateCommande = LocalDateTime.now();
    }
} 