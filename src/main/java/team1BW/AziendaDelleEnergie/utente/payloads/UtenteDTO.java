package team1BW.AziendaDelleEnergie.utente.payloads;

public record UtenteDTO(
        String nome,
        String cognome,
        String username,
        String email,
        String password,
        String avatar
) {
    
}
