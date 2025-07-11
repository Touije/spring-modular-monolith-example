package com.example.springmodulithexample.acheteur.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AchatEffectueEvent {
    private Long acheteurId;
    private String nomAcheteur;
    private String emailAcheteur;
    private Long produitId;
    private int quantite;
} 