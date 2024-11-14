package team1BW.AziendaDelleEnergie.utente.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import team1BW.AziendaDelleEnergie.exceptions.BadRequestException;
import team1BW.AziendaDelleEnergie.utente.entities.Utente;
import team1BW.AziendaDelleEnergie.utente.payloads.LoginRequestDTO;
import team1BW.AziendaDelleEnergie.utente.payloads.LoginResponseDTO;
import team1BW.AziendaDelleEnergie.utente.payloads.UtenteDTO;
import team1BW.AziendaDelleEnergie.utente.services.AuthService;
import team1BW.AziendaDelleEnergie.utente.services.UtenteService;
import team1BW.AziendaDelleEnergie.utente.tools.MailgunSender;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private MailgunSender mailgunSender;

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO body) {
        return new LoginResponseDTO(this.authService.checkCredentialsAndGenerateToken(body));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Utente save(@RequestBody @Validated UtenteDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String message = validationResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono stati errori nel payload! " + message);
        }

        utenteService.findByEmail(body.email()).ifPresent(existingUser -> {
            throw new BadRequestException("Email " + body.email() + " già in uso!");
        });

        Utente newUser = this.utenteService.saveUtente(body);

        try {
            mailgunSender.sendRegistrationEmail(newUser);
        } catch (Exception e) {
            System.err.println("Errore nell'invio dell'email di benvenuto a " + newUser.getEmail() + ": " + e.getMessage());
        }

        return newUser;
    }
}
