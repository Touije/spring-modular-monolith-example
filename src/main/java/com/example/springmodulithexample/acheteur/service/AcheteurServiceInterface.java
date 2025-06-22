package com.example.springmodulithexample.acheteur.service;

import com.example.springmodulithexample.acheteur.domain.Acheteur;
import com.example.springmodulithexample.acheteur.dto.AchatRequestDTO;
import com.example.springmodulithexample.acheteur.dto.AcheteurUpdateRequestDTO;
import com.example.springmodulithexample.acheteur.dto.CreationAcheteurRequestDTO;

import java.util.List;

public interface AcheteurServiceInterface {
    Acheteur createAcheteur(CreationAcheteurRequestDTO request);
    List<Acheteur> getAcheteurs();
    Acheteur getAcheteurById(Long id);
    Acheteur updateAcheteur(Long id, AcheteurUpdateRequestDTO request);
    void deleteAcheteur(Long id);
    void effectuerAchat(AchatRequestDTO achatRequest);
} 