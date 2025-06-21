package com.example.springmodulithexample.produit.repository;

import com.example.springmodulithexample.produit.domain.Produit;
import org.springframework.data.repository.CrudRepository;

public interface ProduitRepository extends CrudRepository<Produit, Long> {
} 