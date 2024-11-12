package team1BW.AziendaDelleEnergie.fattura.payloads;

import team1BW.AziendaDelleEnergie.fattura.enums.StatoFattura;

import java.time.LocalDate;

public record FatturaDTO(
        Long id,
        LocalDate dataFattura,
        double importo,
        Long numeroFattura,
        StatoFattura stato
) {
}
