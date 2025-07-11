package com.example.springmodulithexample.acheteur.service;

import com.example.springmodulithexample.acheteur.domain.Acheteur;
import com.example.springmodulithexample.acheteur.dto.AchatRequestDTO;
import com.example.springmodulithexample.acheteur.dto.AcheteurUpdateRequestDTO;
import com.example.springmodulithexample.acheteur.dto.CreationAcheteurRequestDTO;
import com.example.springmodulithexample.acheteur.events.AchatEffectueEvent;
import com.example.springmodulithexample.acheteur.repository.AcheteurRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.core.KafkaTemplate;
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
    private final KafkaTemplate<String, AchatEffectueEvent> achatEffectueKafkaTemplate;


    public AcheteurServiceImpl(AcheteurRepository acheteurRepository, ApplicationEventPublisher events, KafkaTemplate<String, AchatEffectueEvent> achatEffectueKafkaTemplate) {
        this.acheteurRepository = acheteurRepository;
        this.events = events;
        this.achatEffectueKafkaTemplate = achatEffectueKafkaTemplate;
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
    public Acheteur getAcheteurById(Long id) {
        return acheteurRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Acheteur non trouvé avec l'id: " + id));
    }

    @Override
    public Acheteur updateAcheteur(Long id, AcheteurUpdateRequestDTO request) {
        Acheteur acheteur = getAcheteurById(id);
        acheteur.setNom(request.getNom());
        acheteur.setEmail(request.getEmail());
        acheteur.setAdresse(request.getAdresse());
        acheteur.setTelephone(request.getTelephone());
        return acheteurRepository.save(acheteur);
    }

    @Override
    public void deleteAcheteur(Long id) {
        if (!acheteurRepository.existsById(id)) {
            throw new NoSuchElementException("Acheteur non trouvé avec l'id: " + id);
        }
        acheteurRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void effectuerAchat(AchatRequestDTO achatRequest) {
        log.info("Tentative d'achat pour l'acheteur {} et le produit {}", achatRequest.getAcheteurId(), achatRequest.getProduitId());

        if (achatRequest.getQuantite() <= 0) {
            throw new IllegalArgumentException("La quantité achetée doit être supérieure à zéro.");
        }

        Acheteur acheteur = acheteurRepository.findById(achatRequest.getAcheteurId())
                .orElseThrow(() -> new NoSuchElementException("Acheteur non trouvé avec l'id: " + achatRequest.getAcheteurId()));

        AchatEffectueEvent event = new AchatEffectueEvent(
            acheteur.getId(),
            acheteur.getNom(),
            acheteur.getEmail(),
            achatRequest.getProduitId(),
            achatRequest.getQuantite()
        );
        log.info("Validation réussie, publication de l'événement d'achat enrichi sur Kafka.");
        achatEffectueKafkaTemplate.send("achat-effectue", event);
    }
} 