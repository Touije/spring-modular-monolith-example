package com.example.springmodulithexample.commande.dto;

import com.example.springmodulithexample.commande.domain.StatutCommande;
import lombok.Data;

@Data
public class ChangementStatutRequestDTO {
    private StatutCommande nouveauStatut;
}