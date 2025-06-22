package com.example.springmodulithexample.commande.dto;

import com.example.springmodulithexample.acheteur.dto.AcheteurResponseDTO;
import com.example.springmodulithexample.commande.domain.StatutCommande;
import com.example.springmodulithexample.produit.dto.ProduitResponseDTO;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommandeDetailsResponseDTO {
    private Long id;
    private LocalDateTime dateCommande;
    private StatutCommande statut;
    private int quantite;
    private AcheteurResponseDTO acheteur;
    private ProduitResponseDTO produit;
} 