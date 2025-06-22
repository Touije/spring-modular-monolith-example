package com.example.springmodulithexample.acheteur.dto;

import lombok.Data;

@Data
public class AchatRequestDTO {
    private Long acheteurId;
    private Long produitId;
    private int quantite;
} 