package com.example.springmodulithexample.commande.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long acheteurId;
    private String nomAcheteur;
    private String emailAcheteur;
    private Long produitId;
    private String nomProduit;
    private double prixProduit;
    private int quantite;

    @Enumerated(EnumType.STRING)
    private StatutCommande statut;

    private LocalDateTime dateCommande;

    public Commande(Long acheteurId, Long produitId, int quantite) {
        this.acheteurId = acheteurId;
        this.produitId = produitId;
        this.quantite = quantite;
        this.statut = StatutCommande.EN_COURS;
        this.dateCommande = LocalDateTime.now();
    }
} 