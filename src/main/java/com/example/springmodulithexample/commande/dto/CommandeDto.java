package com.example.springmodulithexample.commande.dto;

import java.time.LocalDateTime;

public record CommandeDto(
    Long id,
    Long acheteurId,
    Long produitId,
    LocalDateTime dateCommande
) {
}
