package com.example.springmodulithexample.commande.mapper;

import com.example.springmodulithexample.commande.domain.Commande;
import com.example.springmodulithexample.commande.dto.CommandeDetailsResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommandeMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "dateCommande", target = "dateCommande")
    @Mapping(source = "statut", target = "statut")
    @Mapping(source = "quantite", target = "quantite")
    @Mapping(source = "acheteurId", target = "acheteurId")
    @Mapping(source = "nomAcheteur", target = "nomAcheteur")
    @Mapping(source = "emailAcheteur", target = "emailAcheteur")
    @Mapping(source = "produitId", target = "produitId")
    @Mapping(source = "nomProduit", target = "nomProduit")
    @Mapping(source = "prixProduit", target = "prixProduit")
    CommandeDetailsResponseDTO toDetailsDto(Commande commande);
}