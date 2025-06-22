package com.example.springmodulithexample.produit.service;

import com.example.springmodulithexample.produit.domain.Produit;
import com.example.springmodulithexample.produit.dto.CreationProduitRequestDTO;

import java.util.List;
import java.util.Optional;

public interface ProduitServiceInterface {
    Produit createProduit(CreationProduitRequestDTO request);
    List<Produit> getProduits();
    Optional<Produit> findProduitById(Long id);
    void decrementerStock(Long produitId, int quantite);
    boolean existsById(Long id);
} 