package com.example.springmodulithexample.acheteur.dto;

public record AchatRequest(Long acheteurId, Long produitId, int quantite) {
} 