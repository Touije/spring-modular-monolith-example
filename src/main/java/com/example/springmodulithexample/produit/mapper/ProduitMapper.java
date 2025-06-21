package com.example.springmodulithexample.produit.mapper;

import com.example.springmodulithexample.produit.domain.Produit;
import com.example.springmodulithexample.produit.dto.ProduitDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProduitMapper {

    ProduitDto toDto(Produit produit);
} 