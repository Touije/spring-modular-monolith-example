package com.example.springmodulithexample.produit.api;

import com.example.springmodulithexample.produit.dto.CreationProduitRequestDTO;
import com.example.springmodulithexample.produit.dto.ProduitResponseDTO;
import com.example.springmodulithexample.produit.mapper.ProduitMapper;
import com.example.springmodulithexample.produit.service.ProduitServiceInterface;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/produits")
public class ProduitController {

    private final ProduitServiceInterface produitService;
    private final ProduitMapper produitMapper;

    public ProduitController(ProduitServiceInterface produitService, ProduitMapper produitMapper) {
        this.produitService = produitService;
        this.produitMapper = produitMapper;
    }

    @PostMapping
    public ProduitResponseDTO createProduit(@RequestBody CreationProduitRequestDTO request) {
        return produitMapper.toDto(produitService.createProduit(request));
    }

    @GetMapping
    public List<ProduitResponseDTO> getProduits() {
        return produitService.getProduits().stream()
                .map(produitMapper::toDto)
                .collect(Collectors.toList());
    }
}