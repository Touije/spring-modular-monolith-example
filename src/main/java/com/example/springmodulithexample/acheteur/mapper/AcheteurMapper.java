package com.example.springmodulithexample.acheteur.mapper;

import com.example.springmodulithexample.acheteur.domain.Acheteur;
import com.example.springmodulithexample.acheteur.dto.AcheteurResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AcheteurMapper {

    AcheteurResponseDTO toDto(Acheteur acheteur);
} 