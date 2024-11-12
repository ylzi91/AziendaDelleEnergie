package team1BW.AziendaDelleEnergie.utente.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import team1BW.AziendaDelleEnergie.utente.payloads.UtenteCreateDTO;
import team1BW.AziendaDelleEnergie.utente.payloads.UtenteDTO;
import team1BW.AziendaDelleEnergie.utente.payloads.UtenteResponseDTO;
import team1BW.AziendaDelleEnergie.utente.services.UtenteService;

@RestController
@RequestMapping("/utenti")
public class UtenteController {

    private final UtenteService utenteService;

    public UtenteController(UtenteService utenteService) {
        this.utenteService = utenteService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UtenteCreateDTO utenteCreateDTO) {
        utenteService.registerUser(utenteCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Utente registrato con successo");
    }

    @GetMapping("/{id}")
    public ResponseEntity<UtenteResponseDTO> getUserById(@PathVariable Long id) {
        UtenteResponseDTO user = utenteService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<UtenteResponseDTO> updateUser(@PathVariable Long id, @RequestBody UtenteDTO utenteDTO) {
        UtenteResponseDTO updatedUser = utenteService.updateUser(id, utenteDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        utenteService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
