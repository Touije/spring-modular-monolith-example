package com.example.springmodulithexample.produit.service;

import com.example.springmodulithexample.produit.domain.Produit;
import com.example.springmodulithexample.produit.dto.CreationProduitRequestDTO;
import com.example.springmodulithexample.produit.dto.ProduitUpdateRequestDTO;
import com.example.springmodulithexample.produit.repository.ProduitRepository;
import com.example.springmodulithexample.acheteur.events.AchatEffectueEvent;
import com.example.springmodulithexample.produit.events.StockDecrementeEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class ProduitServiceImpl implements ProduitServiceInterface {

    private final ProduitRepository produitRepository;
    private final KafkaTemplate<String, StockDecrementeEvent> stockDecrementeKafkaTemplate;

    public ProduitServiceImpl(ProduitRepository produitRepository, KafkaTemplate<String, StockDecrementeEvent> stockDecrementeKafkaTemplate) {
        this.produitRepository = produitRepository;
        this.stockDecrementeKafkaTemplate = stockDecrementeKafkaTemplate;
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
    public Produit getProduitById(Long id) {
        return produitRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Produit non trouvé avec l'id: " + id));
    }

    @Override
    public Produit updateProduit(Long id, ProduitUpdateRequestDTO request) {
        Produit produit = getProduitById(id);
        produit.setNom(request.getNom());
        produit.setDescription(request.getDescription());
        produit.setPrix(request.getPrix());
        produit.setQuantiteEnStock(request.getQuantiteEnStock());
        return produitRepository.save(produit);
    }

    @Override
    public void deleteProduit(Long id) {
        if (!produitRepository.existsById(id)) {
            throw new NoSuchElementException("Produit non trouvé avec l'id: " + id);
        }
        produitRepository.deleteById(id);
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

    @KafkaListener(topics = "achat-effectue", groupId = "modulith-group", containerFactory = "achatEffectueKafkaListenerContainerFactory")
    public void consommerAchatEffectue(AchatEffectueEvent event) {
        Produit produit = produitRepository.findById(event.getProduitId())
                .orElseThrow(() -> new IllegalArgumentException("Produit non trouvé avec l'ID: " + event.getProduitId()));

        if (produit.getQuantiteEnStock() < event.getQuantite()) {
            // Option : publier un événement d'échec ou loguer l'erreur
            return;
        }

        produit.setQuantiteEnStock(produit.getQuantiteEnStock() - event.getQuantite());
        produitRepository.save(produit);

        // Publier l'événement de stock décrémenté enrichi
        StockDecrementeEvent stockEvent = new StockDecrementeEvent(
            event.getAcheteurId(),
            event.getNomAcheteur(),
            event.getEmailAcheteur(),
            produit.getId(),
            produit.getNom(),
            produit.getPrix(),
            event.getQuantite()
        );
        stockDecrementeKafkaTemplate.send("stock-decremente", stockEvent);
    }
} 