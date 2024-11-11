package team1BW.AziendaDelleEnergie.utente.payloads;

import team1BW.AziendaDelleEnergie.utente.enums.Ruolo;

public record UtenteCreateDTO(
        String nome,
        String cognome,
        String username,
        String email,
        String password,
        String avatar,
        Ruolo ruolo
) {
}
