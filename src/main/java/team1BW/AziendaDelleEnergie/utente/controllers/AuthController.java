package team1BW.AziendaDelleEnergie.utente.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team1BW.AziendaDelleEnergie.utente.payloads.LoginRequestDTO;
import team1BW.AziendaDelleEnergie.utente.payloads.LoginResponseDTO;
import team1BW.AziendaDelleEnergie.utente.services.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        LoginResponseDTO response = authService.authenticateUser(loginRequestDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/validate")
    public ResponseEntity<String> validateToken(@RequestHeader("Authorization") String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            if (authService.validateToken(token)) {
                return ResponseEntity.ok("Token valido");
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token non valido");
    }
}
