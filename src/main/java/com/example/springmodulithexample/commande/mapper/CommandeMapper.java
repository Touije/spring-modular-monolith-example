package com.example.springmodulithexample.commande.mapper;

import com.example.springmodulithexample.acheteur.domain.Acheteur;
import com.example.springmodulithexample.acheteur.repository.AcheteurRepository;
import com.example.springmodulithexample.commande.domain.Commande;
import com.example.springmodulithexample.commande.dto.CommandeDetailsDto;
import com.example.springmodulithexample.produit.domain.Produit;
import com.example.springmodulithexample.produit.repository.ProduitRepository;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class CommandeMapper {

    @Autowired
    private AcheteurRepository acheteurRepository;
    @Autowired
    private ProduitRepository produitRepository;

    @Mapping(source = "id", target = "commandeId")
    @Mapping(source = "commande.acheteurId", target = "nomAcheteur", qualifiedByName = "idToAcheteurNom")
    @Mapping(source = "commande.acheteurId", target = "emailAcheteur", qualifiedByName = "idToAcheteurEmail")
    @Mapping(source = "commande.acheteurId", target = "telephoneAcheteur", qualifiedByName = "idToAcheteurTelephone")
    @Mapping(source = "commande.produitId", target = "nomProduit", qualifiedByName = "idToProduitNom")
    public abstract CommandeDetailsDto toDetailsDto(Commande commande);

    @Named("idToAcheteurNom")
    String idToAcheteurNom(Long id) {
        return acheteurRepository.findById(id).map(Acheteur::getNom).orElse("N/A");
    }

    @Named("idToAcheteurEmail")
    String idToAcheteurEmail(Long id) {
        return acheteurRepository.findById(id).map(Acheteur::getEmail).orElse("N/A");
    }

    @Named("idToAcheteurTelephone")
    String idToAcheteurTelephone(Long id) {
        return acheteurRepository.findById(id).map(Acheteur::getTelephone).orElse("N/A");
    }

    @Named("idToProduitNom")
    String idToProduitNom(Long id) {
        return produitRepository.findById(id).map(Produit::getNom).orElse("N/A");
    }
} 