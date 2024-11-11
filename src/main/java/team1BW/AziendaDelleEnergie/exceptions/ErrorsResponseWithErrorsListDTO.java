package team1BW.AziendaDelleEnergie.exceptions;

import java.time.LocalDateTime;
import java.util.Map;

public record ErrorsResponseWithErrorsListDTO(String message, LocalDateTime timestamp, Map<String, String> errors) {
}