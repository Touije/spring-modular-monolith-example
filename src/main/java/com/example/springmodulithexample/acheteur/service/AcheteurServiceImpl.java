package com.example.springmodulithexample.acheteur.service;

import com.example.springmodulithexample.acheteur.domain.Acheteur;
import com.example.springmodulithexample.acheteur.dto.AchatRequestDTO;
import com.example.springmodulithexample.acheteur.dto.CreationAcheteurRequestDTO;
import com.example.springmodulithexample.acheteur.events.AchatEffectueEvent;
import com.example.springmodulithexample.acheteur.repository.AcheteurRepository;
import com.example.springmodulithexample.produit.service.ProduitServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
public class AcheteurServiceImpl implements AcheteurServiceInterface {

    private final AcheteurRepository acheteurRepository;
    private final ApplicationEventPublisher events;
    private final ProduitServiceInterface produitService;


    public AcheteurServiceImpl(AcheteurRepository acheteurRepository, ApplicationEventPublisher events, ProduitServiceInterface produitService) {
        this.acheteurRepository = acheteurRepository;
        this.events = events;
        this.produitService = produitService;
    }


    @Override
    @Transactional
    public Acheteur createAcheteur(CreationAcheteurRequestDTO request) {
        Acheteur acheteur = new Acheteur();
        acheteur.setNom(request.getNom());
        acheteur.setEmail(request.getEmail());
        acheteur.setAdresse(request.getAdresse());
        acheteur.setTelephone(request.getTelephone());
        return acheteurRepository.save(acheteur);
    }

    @Override
    public List<Acheteur> getAcheteurs() {
        return StreamSupport.stream(acheteurRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void effectuerAchat(AchatRequestDTO achatRequest) {
        log.info("Tentative d'achat pour l'acheteur {} et le produit {}", achatRequest.getAcheteurId(), achatRequest.getProduitId());

        if (achatRequest.getQuantite() <= 0) {
            throw new IllegalArgumentException("La quantité achetée doit être supérieure à zéro.");
        }

        acheteurRepository.findById(achatRequest.getAcheteurId())
                .orElseThrow(() -> new NoSuchElementException("Acheteur non trouvé avec l'id: " + achatRequest.getAcheteurId()));

        produitService.decrementerStock(achatRequest.getProduitId(), achatRequest.getQuantite());


        log.info("Validation réussie, publication de l'événement d'achat.");
        events.publishEvent(new AchatEffectueEvent(achatRequest.getAcheteurId(), achatRequest.getProduitId(), achatRequest.getQuantite()));
    }
} 