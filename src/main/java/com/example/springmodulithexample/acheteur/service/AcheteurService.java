package com.example.springmodulithexample.acheteur.service;

import com.example.springmodulithexample.acheteur.domain.Acheteur;
import com.example.springmodulithexample.acheteur.dto.AchatRequest;
import com.example.springmodulithexample.acheteur.dto.CreationAcheteurRequest;
import com.example.springmodulithexample.acheteur.events.AchatEffectueEvent;
import com.example.springmodulithexample.acheteur.repository.AcheteurRepository;
import com.example.springmodulithexample.produit.repository.ProduitRepository;
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
    private final ProduitRepository produitRepository;
    private final AcheteurMapper acheteurMapper;

    public AcheteurService(ApplicationEventPublisher events, AcheteurRepository acheteurRepository, ProduitRepository produitRepository, AcheteurMapper acheteurMapper) {
        this.events = events;
        this.acheteurRepository = acheteurRepository;
        this.produitRepository = produitRepository;
        this.acheteurMapper = acheteurMapper;
    }

    public void effectuerAchat(AchatRequest achatRequest) {
        if (!acheteurRepository.existsById(achatRequest.getAcheteurId())) {
            throw new IllegalArgumentException("Acheteur non trouvé avec l'ID: " + achatRequest.getAcheteurId());
        }

        if (!produitRepository.existsById(achatRequest.getProduitId())) {
            throw new IllegalArgumentException("Produit non trouvé avec l'ID: " + achatRequest.getProduitId());
        }

        events.publishEvent(new AchatEffectueEvent(achatRequest.getAcheteurId(), achatRequest.getProduitId()));
    }

    public Acheteur createAcheteur(CreationAcheteurRequest request) {
        Acheteur acheteur = new Acheteur();
        acheteur.setNom(request.nom());
        return acheteurRepository.save(acheteur);
    }

    @Transactional(readOnly = true)
    public List<Acheteur> getAcheteurs() {
        return StreamSupport.stream(acheteurRepository.findAll().spliterator(), false)
                .toList();
    }
} 