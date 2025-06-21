package com.example.springmodulithexample.produit.service;

import com.example.springmodulithexample.produit.domain.Produit;
import com.example.springmodulithexample.produit.dto.ProduitDto;
import org.springframework.stereotype.Component;

@Component
public class ProduitMapper {

    public ProduitDto toDto(Produit produit) {
        return new ProduitDto(produit.getId(), produit.getNom(), produit.getPrix());
    }
} 