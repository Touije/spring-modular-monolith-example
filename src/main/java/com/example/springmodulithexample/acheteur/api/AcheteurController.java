package com.example.springmodulithexample.acheteur.api;

import com.example.springmodulithexample.acheteur.dto.AchatRequest;
import com.example.springmodulithexample.acheteur.dto.AcheteurDto;
import com.example.springmodulithexample.acheteur.dto.CreationAcheteurRequest;
import com.example.springmodulithexample.acheteur.service.AcheteurMapper;
import com.example.springmodulithexample.acheteur.service.AcheteurService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/acheteurs")
public class AcheteurController {

    private final AcheteurService acheteurService;
    private final AcheteurMapper acheteurMapper;

    public AcheteurController(AcheteurService acheteurService, AcheteurMapper acheteurMapper) {
        this.acheteurService = acheteurService;
        this.acheteurMapper = acheteurMapper;
    }

    @PostMapping
    public AcheteurDto createAcheteur(@RequestBody CreationAcheteurRequest acheteur) {
        return acheteurMapper.toDto(acheteurService.createAcheteur(acheteur));
    }

    @GetMapping
    public List<AcheteurDto> getAcheteurs() {
        return acheteurService.getAcheteurs().stream()
                .map(acheteurMapper::toDto)
                .toList();
    }

    @PostMapping("/achat")
    public void effectuerAchat(@RequestBody AchatRequest achatRequest) {
        acheteurService.effectuerAchat(achatRequest);
    }
} 