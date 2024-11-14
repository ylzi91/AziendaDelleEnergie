package team1BW.AziendaDelleEnergie.ruoliUtente.payloads;

import jakarta.validation.constraints.NotEmpty;

public record NewRuoloDTO(
        @NotEmpty(message = "Inserire un nome per il ruolo!")
        String nome) {
}
