package com.example.springmodulithexample.produit.api;

import com.example.springmodulithexample.produit.dto.CreationProduitRequest;
import com.example.springmodulithexample.produit.dto.ProduitDto;
import com.example.springmodulithexample.produit.mapper.ProduitMapper;
import com.example.springmodulithexample.produit.service.ProduitService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produits")
public class ProduitController {

    private final ProduitService produitService;
    private final ProduitMapper produitMapper;

    public ProduitController(ProduitService produitService, ProduitMapper produitMapper) {
        this.produitService = produitService;
        this.produitMapper = produitMapper;
    }

    @PostMapping
    public ProduitDto createProduit(@RequestBody CreationProduitRequest request) {
        return produitMapper.toDto(produitService.createProduit(request));
    }

    @GetMapping
    public List<ProduitDto> getProduits() {
        return produitService.getProduits().stream()
                .map(produitMapper::toDto)
                .toList();
    }
} 