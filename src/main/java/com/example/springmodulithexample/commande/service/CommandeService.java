package com.example.springmodulithexample.commande.service;

import com.example.springmodulithexample.acheteur.events.AchatEffectueEvent;
import com.example.springmodulithexample.commande.domain.Commande;
import com.example.springmodulithexample.commande.mapper.CommandeMapper;
import com.example.springmodulithexample.commande.repository.CommandeRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class CommandeService {

    private final CommandeRepository commandeRepository;
    private final CommandeMapper commandeMapper;

    public CommandeService(CommandeRepository commandeRepository, CommandeMapper commandeMapper) {
        this.commandeRepository = commandeRepository;
        this.commandeMapper = commandeMapper;
    }

    @EventListener
    public void onAchatEffectue(AchatEffectueEvent event) {
        // Here, we could use the APIs of other modules to get product/buyer details
        // before creating the order, to make it more robust.
        Commande commande = new Commande(event.getAcheteurId(), event.getProduitId(), event.getQuantite());
        commandeRepository.save(commande);
    }

    @Transactional(readOnly = true)
    public List<Commande> getCommandes() {
        return StreamSupport.stream(commandeRepository.findAll().spliterator(), false)
                .toList();
    }
} 