package com.example.springmodulithexample.produit.dto;

import lombok.Data;

@Data
public class ProduitResponseDTO {
    private Long id;
    private String nom;
    private String description;
    private double prix;
    private int quantiteEnStock;
}