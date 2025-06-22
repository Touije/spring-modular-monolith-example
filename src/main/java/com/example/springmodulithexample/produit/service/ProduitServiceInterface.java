package com.example.springmodulithexample.produit.service;

import com.example.springmodulithexample.produit.domain.Produit;
import com.example.springmodulithexample.produit.dto.CreationProduitRequestDTO;
import com.example.springmodulithexample.produit.dto.ProduitUpdateRequestDTO;

import java.util.List;
import java.util.Optional;

public interface ProduitServiceInterface {
    Produit createProduit(CreationProduitRequestDTO request);
    List<Produit> getProduits();
    Produit getProduitById(Long id);
    Produit updateProduit(Long id, ProduitUpdateRequestDTO request);
    void deleteProduit(Long id);
    Optional<Produit> findProduitById(Long id);
    void decrementerStock(Long produitId, int quantite);
    boolean existsById(Long id);
} 