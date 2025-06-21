package com.example.springmodulithexample.produit.dto;

public record ProduitDto(Long id, String nom, double prix, String description, int quantiteEnStock) {
}