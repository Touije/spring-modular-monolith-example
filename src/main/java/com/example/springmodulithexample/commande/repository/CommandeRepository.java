package com.example.springmodulithexample.commande.repository;

import com.example.springmodulithexample.commande.domain.Commande;
import org.springframework.data.repository.CrudRepository;

public interface CommandeRepository extends CrudRepository<Commande, Long> {
} 