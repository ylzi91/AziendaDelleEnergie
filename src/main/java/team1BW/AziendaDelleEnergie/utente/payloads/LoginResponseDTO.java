package team1BW.AziendaDelleEnergie.utente.payloads;

public record LoginResponseDTO(
        String token,
        Long userId,
        String username,
        String nome,
        String cognome,
        String ruolo
) {
}
