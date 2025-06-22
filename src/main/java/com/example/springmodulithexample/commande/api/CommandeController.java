package com.example.springmodulithexample.commande.api;

import com.example.springmodulithexample.commande.dto.ChangementStatutRequestDTO;
import com.example.springmodulithexample.commande.dto.CommandeDetailsResponseDTO;
import com.example.springmodulithexample.commande.service.CommandeServiceInterface;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/commandes")
public class CommandeController {

    private final CommandeServiceInterface commandeService;

    public CommandeController(CommandeServiceInterface commandeService) {
        this.commandeService = commandeService;
    }

    @GetMapping
    public List<CommandeDetailsResponseDTO> getAllCommandes() {
        return commandeService.getAllCommandesWithDetails();
    }

    @GetMapping("/{id}")
    public CommandeDetailsResponseDTO getCommandeById(@PathVariable Long id) {
        return commandeService.getCommandeById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCommande(@PathVariable Long id) {
        commandeService.deleteCommande(id);
    }

    @PutMapping("/{commandeId}/statut")
    public void changerStatutCommande(@PathVariable Long commandeId, @RequestBody ChangementStatutRequestDTO request) {
        commandeService.changerStatut(commandeId, request.getNouveauStatut());
    }
}