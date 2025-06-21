package com.example.springmodulithexample.acheteur.dto;

import lombok.Data;

@Data
public class AchatRequest {
    private Long acheteurId;
    private Long produitId;
} 