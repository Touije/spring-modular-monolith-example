package com.example.springmodulithexample.acheteur.dto;

import lombok.Data;

@Data
public class AcheteurResponseDTO {
    private Long id;
    private String nom;
    private String email;
    private String adresse;
    private String telephone;
} 