package com.example.springmodulithexample.commande.api;

import com.example.springmodulithexample.commande.dto.CommandeDto;
import com.example.springmodulithexample.commande.mapper.CommandeMapper;
import com.example.springmodulithexample.commande.service.CommandeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/commandes")
public class CommandeController {

    private final CommandeService commandeService;
    private final CommandeMapper commandeMapper;

    public CommandeController(CommandeService commandeService, CommandeMapper commandeMapper) {
        this.commandeService = commandeService;
        this.commandeMapper = commandeMapper;
    }

    @GetMapping
    public List<CommandeDto> getCommandes() {
        return commandeService.getCommandes().stream()
                .map(commandeMapper::toDto)
                .toList();
    }
} 