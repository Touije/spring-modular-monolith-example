package com.example.springmodulithexample.acheteur.service;

import com.example.springmodulithexample.acheteur.domain.Acheteur;
import com.example.springmodulithexample.acheteur.dto.AcheteurDto;
import org.springframework.stereotype.Component;

@Component
public class AcheteurMapper {

    public AcheteurDto toDto(Acheteur acheteur) {
        return new AcheteurDto(acheteur.getId(), acheteur.getNom());
    }
} 