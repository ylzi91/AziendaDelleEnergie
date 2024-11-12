package team1BW.AziendaDelleEnergie.utente.payloads;

public record UtenteResponseDTO(
        Long id,
        String nome,
        String cognome,
        String username,
        String email,
        String avatar
) {
}
