package com.example.springmodulithexample.commande.dto;

import com.example.springmodulithexample.commande.domain.StatutCommande;

import java.time.LocalDateTime;

public record CommandeDetailsDto(
        Long commandeId,
        StatutCommande statut,
        LocalDateTime dateCommande,
        int quantite,
        String nomAcheteur,
        String emailAcheteur,
        String telephoneAcheteur,
        String nomProduit
) {} 