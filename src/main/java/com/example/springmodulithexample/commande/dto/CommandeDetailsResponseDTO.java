package com.example.springmodulithexample.commande.dto;

import com.example.springmodulithexample.commande.domain.StatutCommande;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CommandeDetailsResponseDTO {
    private Long id;
    private LocalDateTime dateCommande;
    private StatutCommande statut;
    private int quantite;
    private Long acheteurId;
    private String nomAcheteur;
    private String emailAcheteur;
    private Long produitId;
    private String nomProduit;
    private double prixProduit;
} 