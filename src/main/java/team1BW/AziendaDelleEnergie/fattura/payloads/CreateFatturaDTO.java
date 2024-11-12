package team1BW.AziendaDelleEnergie.fattura.payloads;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import team1BW.AziendaDelleEnergie.fattura.enums.StatoFattura;

import java.time.LocalDate;

public record CreateFatturaDTO(
        @NotNull(message = "La data della fattura è obbligatoria")
        LocalDate dataFattura,

        @Positive(message = "L'importo deve essere un valore positivo")
        double importo,

        @NotNull(message = "Il numero della fattura è obbligatorio")
        Long numeroFattura,

        @NotNull(message = "Lo stato della fattura è obbligatorio")
        StatoFattura stato,

        String pIva

) {
}
