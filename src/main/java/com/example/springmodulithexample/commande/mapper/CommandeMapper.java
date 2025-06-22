package com.example.springmodulithexample.commande.mapper;

import com.example.springmodulithexample.acheteur.domain.Acheteur;
import com.example.springmodulithexample.acheteur.mapper.AcheteurMapper;
import com.example.springmodulithexample.commande.domain.Commande;
import com.example.springmodulithexample.commande.dto.CommandeDetailsResponseDTO;
import com.example.springmodulithexample.produit.domain.Produit;
import com.example.springmodulithexample.produit.mapper.ProduitMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {AcheteurMapper.class, ProduitMapper.class})
public interface CommandeMapper {

    @Mapping(source = "commande.id", target = "id")
    @Mapping(source = "commande.dateCommande", target = "dateCommande")
    @Mapping(source = "commande.statut", target = "statut")
    @Mapping(source = "commande.quantite", target = "quantite")
    @Mapping(source = "acheteur", target = "acheteur")
    @Mapping(source = "produit", target = "produit")
    CommandeDetailsResponseDTO toDetailsDto(Commande commande, Acheteur acheteur, Produit produit);
}