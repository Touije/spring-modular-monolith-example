package com.example.springmodulithexample.produit.service;

import com.example.springmodulithexample.produit.domain.Produit;
import com.example.springmodulithexample.produit.dto.CreationProduitRequestDTO;
import com.example.springmodulithexample.produit.repository.ProduitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class ProduitServiceImpl implements ProduitServiceInterface {

    private final ProduitRepository produitRepository;

    public ProduitServiceImpl(ProduitRepository produitRepository) {
        this.produitRepository = produitRepository;
    }

    @Override
    public Produit createProduit(CreationProduitRequestDTO request) {
        Produit produit = new Produit();
        produit.setNom(request.getNom());
        produit.setPrix(request.getPrix());
        produit.setDescription(request.getDescription());
        produit.setQuantiteEnStock(request.getQuantiteEnStock());
        return produitRepository.save(produit);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Produit> getProduits() {
        return StreamSupport.stream(produitRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Produit> findProduitById(Long id) {
        return produitRepository.findById(id);
    }

    @Override
    public void decrementerStock(Long produitId, int quantite) {
        Produit produit = produitRepository.findById(produitId)
                .orElseThrow(() -> new IllegalArgumentException("Produit non trouvé avec l'ID: " + produitId));

        if (produit.getQuantiteEnStock() < quantite) {
            throw new IllegalStateException("Stock insuffisant pour le produit: " + produit.getNom());
        }

        produit.setQuantiteEnStock(produit.getQuantiteEnStock() - quantite);
        produitRepository.save(produit);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return produitRepository.existsById(id);
    }
} 