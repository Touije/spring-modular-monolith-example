package com.example.springmodulithexample.acheteur.api;

import com.example.springmodulithexample.acheteur.dto.AchatRequestDTO;
import com.example.springmodulithexample.acheteur.dto.AcheteurResponseDTO;
import com.example.springmodulithexample.acheteur.dto.CreationAcheteurRequestDTO;
import com.example.springmodulithexample.acheteur.mapper.AcheteurMapper;
import com.example.springmodulithexample.acheteur.service.AcheteurServiceInterface;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/acheteurs")
public class AcheteurController {

    private final AcheteurServiceInterface acheteurService;
    private final AcheteurMapper acheteurMapper;

    public AcheteurController(AcheteurServiceInterface acheteurService, AcheteurMapper acheteurMapper) {
        this.acheteurService = acheteurService;
        this.acheteurMapper = acheteurMapper;
    }

    @PostMapping
    public AcheteurResponseDTO createAcheteur(@RequestBody CreationAcheteurRequestDTO acheteur) {
        return acheteurMapper.toDto(acheteurService.createAcheteur(acheteur));
    }

    @GetMapping
    public List<AcheteurResponseDTO> getAcheteurs() {
        return acheteurService.getAcheteurs().stream()
                .map(acheteurMapper::toDto)
                .toList();
    }

    @PostMapping("/achat")
    public void effectuerAchat(@RequestBody AchatRequestDTO achatRequest) {
        acheteurService.effectuerAchat(achatRequest);
    }
} 