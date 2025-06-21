package com.example.springmodulithexample.produit.service;

import com.example.springmodulithexample.produit.domain.Produit;
import com.example.springmodulithexample.produit.dto.CreationProduitRequest;
import com.example.springmodulithexample.produit.mapper.ProduitMapper;
import com.example.springmodulithexample.produit.repository.ProduitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.StreamSupport;
import java.util.List;

@Service
@Transactional
public class ProduitService {

    private final ProduitRepository produitRepository;
    private final ProduitMapper produitMapper;

    public ProduitService(ProduitRepository produitRepository, ProduitMapper produitMapper) {
        this.produitRepository = produitRepository;
        this.produitMapper = produitMapper;
    }

    public Produit createProduit(CreationProduitRequest request) {
        Produit produit = new Produit();
        produit.setNom(request.nom());
        produit.setPrix(request.prix());
        produit.setDescription(request.description());
        produit.setQuantiteEnStock(request.quantiteEnStock());
        return produitRepository.save(produit);
    }

    @Transactional(readOnly = true)
    public List<Produit> getProduits() {
        return StreamSupport.stream(produitRepository.findAll().spliterator(), false)
                .toList();
    }

    public boolean existsById(Long produitId) {
        return produitRepository.existsById(produitId);
    }

    public void decrementerStock(Long produitId, int quantiteDemandee) {
        Produit produit = produitRepository.findById(produitId)
                .orElseThrow(() -> new IllegalArgumentException("Produit non trouvé pour la mise à jour du stock: " + produitId));

        if (produit.getQuantiteEnStock() < quantiteDemandee) {
            throw new IllegalStateException("Stock insuffisant pour le produit: " + produit.getNom());
        }

        produit.setQuantiteEnStock(produit.getQuantiteEnStock() - quantiteDemandee);
        produitRepository.save(produit);
    }
} 