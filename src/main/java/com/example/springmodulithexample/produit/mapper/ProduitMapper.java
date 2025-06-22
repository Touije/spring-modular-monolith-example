package com.example.springmodulithexample.produit.mapper;

import com.example.springmodulithexample.produit.domain.Produit;
import com.example.springmodulithexample.produit.dto.ProduitResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProduitMapper {

    ProduitResponseDTO toDto(Produit produit);
} 