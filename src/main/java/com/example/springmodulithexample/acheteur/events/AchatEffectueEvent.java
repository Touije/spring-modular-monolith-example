package com.example.springmodulithexample.acheteur.events;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AchatEffectueEvent {
    private final Long acheteurId;
    private final Long produitId;
    private final int quantite;
} 