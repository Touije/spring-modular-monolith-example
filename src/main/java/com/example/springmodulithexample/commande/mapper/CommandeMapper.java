package com.example.springmodulithexample.commande.mapper;

import com.example.springmodulithexample.commande.domain.Commande;
import com.example.springmodulithexample.commande.dto.CommandeDto;
import org.springframework.stereotype.Component;

@Component
public class CommandeMapper {

    public CommandeDto toDto(Commande commande) {
        return new CommandeDto(
            commande.getId(),
            commande.getAcheteurId(),
            commande.getProduitId(),
            commande.getDateCommande()
        );
    }
} 