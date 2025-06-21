package com.example.springmodulithexample.commande.api;

import com.example.springmodulithexample.commande.dto.ChangementStatutRequest;
import com.example.springmodulithexample.commande.dto.CommandeDetailsDto;
import com.example.springmodulithexample.commande.service.CommandeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/commandes")
public class CommandeController {

    private final CommandeService commandeService;

    public CommandeController(CommandeService commandeService) {
        this.commandeService = commandeService;
    }

    @GetMapping
    public List<CommandeDetailsDto> getCommandes() {
        return commandeService.getCommandesDetails();
    }

    @PutMapping("/{commandeId}/statut")
    public CommandeDetailsDto changerStatutCommande(@PathVariable Long commandeId, @RequestBody ChangementStatutRequest request) {
        return commandeService.changerStatutCommande(commandeId, request.nouveauStatut());
    }
} 