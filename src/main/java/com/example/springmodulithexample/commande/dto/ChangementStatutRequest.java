package com.example.springmodulithexample.commande.dto;

import com.example.springmodulithexample.commande.domain.StatutCommande;

public record ChangementStatutRequest(StatutCommande nouveauStatut) {
} 