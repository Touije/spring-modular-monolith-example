package com.example.springmodulithexample.commande.mapper;

import com.example.springmodulithexample.commande.domain.Commande;
import com.example.springmodulithexample.commande.dto.CommandeDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommandeMapper {

    CommandeDto toDto(Commande commande);
} 