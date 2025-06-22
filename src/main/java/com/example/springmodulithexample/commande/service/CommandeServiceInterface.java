package com.example.springmodulithexample.commande.service;

import com.example.springmodulithexample.commande.domain.StatutCommande;
import com.example.springmodulithexample.commande.dto.CommandeDetailsResponseDTO;

import java.util.List;

public interface CommandeServiceInterface {
    void creerCommande(Long acheteurId, Long produitId, int quantite);
    List<CommandeDetailsResponseDTO> getAllCommandesWithDetails();
    void changerStatut(Long commandeId, StatutCommande nouveauStatut);
} 