package com.example.springmodulithexample.produit.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StockDecrementeEvent {
    private Long acheteurId;
    private String nomAcheteur;
    private String emailAcheteur;
    private Long produitId;
    private String nomProduit;
    private double prixProduit;
    private int quantite;
} 