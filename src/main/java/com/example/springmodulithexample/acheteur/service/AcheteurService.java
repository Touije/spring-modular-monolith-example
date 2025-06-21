package com.example.springmodulithexample.acheteur.service;

import com.example.springmodulithexample.acheteur.domain.Acheteur;
import com.example.springmodulithexample.acheteur.dto.AchatRequest;
import com.example.springmodulithexample.acheteur.dto.CreationAcheteurRequest;
import com.example.springmodulithexample.acheteur.events.AchatEffectueEvent;
import com.example.springmodulithexample.acheteur.mapper.AcheteurMapper;
import com.example.springmodulithexample.acheteur.repository.AcheteurRepository;
import com.example.springmodulithexample.produit.service.ProduitService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class AcheteurService {

    private final ApplicationEventPublisher events;
    private final AcheteurRepository acheteurRepository;
    private final ProduitService produitService;
    private final AcheteurMapper acheteurMapper;

    public AcheteurService(ApplicationEventPublisher events, AcheteurRepository acheteurRepository, ProduitService produitService, AcheteurMapper acheteurMapper) {
        this.events = events;
        this.acheteurRepository = acheteurRepository;
        this.produitService = produitService;
        this.acheteurMapper = acheteurMapper;
    }

    public void effectuerAchat(AchatRequest achatRequest) {
        // 1. Validation de la requête d'entrée
        if (achatRequest.quantite() <= 0) {
            throw new IllegalArgumentException("La quantité achetée doit être supérieure à zéro.");
        }

        // 2. Validation de l'existence des entités
        if (!acheteurRepository.existsById(achatRequest.acheteurId())) {
            throw new IllegalArgumentException("Acheteur non trouvé avec l'ID: " + achatRequest.acheteurId());
        }
        if (!produitService.existsById(achatRequest.produitId())) {
            throw new IllegalArgumentException("Produit non trouvé avec l'ID: " + achatRequest.produitId());
        }

        // 3. Logique métier principale : vérification du stock et décrémentation.
        // Cette méthode lève une exception si le stock est insuffisant.
        produitService.decrementerStock(achatRequest.produitId(), achatRequest.quantite());

        // 4. Publication de l'événement pour les actions secondaires (création de commande, etc.)
        events.publishEvent(new AchatEffectueEvent(achatRequest.acheteurId(), achatRequest.produitId(), achatRequest.quantite()));
    }

    public Acheteur createAcheteur(CreationAcheteurRequest request) {
        Acheteur acheteur = new Acheteur();
        acheteur.setNom(request.nom());
        acheteur.setEmail(request.email());
        acheteur.setAdresse(request.adresse());
        return acheteurRepository.save(acheteur);
    }

    @Transactional(readOnly = true)
    public List<Acheteur> getAcheteurs() {
        return StreamSupport.stream(acheteurRepository.findAll().spliterator(), false)
                .toList();
    }
} 