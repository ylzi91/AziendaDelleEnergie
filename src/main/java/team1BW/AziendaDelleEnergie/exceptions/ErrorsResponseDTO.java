package team1BW.AziendaDelleEnergie.exceptions;

import java.time.LocalDateTime;

public record ErrorsResponseDTO(String message, LocalDateTime timestamp) {
}