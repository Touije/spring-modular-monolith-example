package com.example.springmodulithexample.commande.service;

import com.example.springmodulithexample.acheteur.domain.Acheteur;
import com.example.springmodulithexample.acheteur.events.AchatEffectueEvent;
import com.example.springmodulithexample.acheteur.repository.AcheteurRepository;
import com.example.springmodulithexample.commande.domain.Commande;
import com.example.springmodulithexample.commande.domain.StatutCommande;
import com.example.springmodulithexample.commande.dto.CommandeDetailsResponseDTO;
import com.example.springmodulithexample.commande.mapper.CommandeMapper;
import com.example.springmodulithexample.commande.repository.CommandeRepository;
import com.example.springmodulithexample.produit.domain.Produit;
import com.example.springmodulithexample.produit.repository.ProduitRepository;
import com.example.springmodulithexample.produit.events.StockDecrementeEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class CommandeServiceImpl implements CommandeServiceInterface {

    private final CommandeRepository commandeRepository;
    private final AcheteurRepository acheteurRepository;
    private final ProduitRepository produitRepository;
    private final CommandeMapper commandeMapper;

    public CommandeServiceImpl(CommandeRepository commandeRepository, AcheteurRepository acheteurRepository, ProduitRepository produitRepository, CommandeMapper commandeMapper) {
        this.commandeRepository = commandeRepository;
        this.acheteurRepository = acheteurRepository;
        this.produitRepository = produitRepository;
        this.commandeMapper = commandeMapper;
    }

    @EventListener
    public void onAchatEffectue(AchatEffectueEvent event) {
        creerCommande(event.getAcheteurId(), event.getProduitId(), event.getQuantite());
    }

    @KafkaListener(topics = "stock-decremente", groupId = "modulith-group", containerFactory = "stockDecrementeKafkaListenerContainerFactory")
    public void consommerStockDecremente(StockDecrementeEvent event) {
        creerCommande(event);
    }

    @Override
    public void creerCommande(Long acheteurId, Long produitId, int quantite) {
        Commande commande = new Commande();
        commande.setAcheteurId(acheteurId);
        commande.setProduitId(produitId);
        commande.setQuantite(quantite);
        commande.setStatut(StatutCommande.EN_COURS);
        commande.setDateCommande(LocalDateTime.now());
        commandeRepository.save(commande);
    }

    public void creerCommande(StockDecrementeEvent event) {
        Commande commande = new Commande();
        commande.setAcheteurId(event.getAcheteurId());
        commande.setNomAcheteur(event.getNomAcheteur());
        commande.setEmailAcheteur(event.getEmailAcheteur());
        commande.setProduitId(event.getProduitId());
        commande.setNomProduit(event.getNomProduit());
        commande.setPrixProduit(event.getPrixProduit());
        commande.setQuantite(event.getQuantite());
        commande.setStatut(StatutCommande.EN_COURS);
        commande.setDateCommande(java.time.LocalDateTime.now());
        commandeRepository.save(commande);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommandeDetailsResponseDTO> getAllCommandesWithDetails() {
        return StreamSupport.stream(commandeRepository.findAll().spliterator(), false)
                .map(commandeMapper::toDetailsDto)
                .collect(Collectors.toList());
    }

    @Override
    public CommandeDetailsResponseDTO getCommandeById(Long id) {
        Commande commande = commandeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Commande non trouvée avec l'id: " + id));
        return commandeMapper.toDetailsDto(commande);
    }

    @Override
    public void deleteCommande(Long id) {
        if (!commandeRepository.existsById(id)) {
            throw new NoSuchElementException("Commande non trouvée avec l'id: " + id);
        }
        commandeRepository.deleteById(id);
    }

    @Override
    public void changerStatut(Long commandeId, StatutCommande nouveauStatut) {
        Commande commande = commandeRepository.findById(commandeId)
                .orElseThrow(() -> new NoSuchElementException("Commande non trouvée avec l'id: " + commandeId));
        commande.setStatut(nouveauStatut);
        commandeRepository.save(commande);
    }
}