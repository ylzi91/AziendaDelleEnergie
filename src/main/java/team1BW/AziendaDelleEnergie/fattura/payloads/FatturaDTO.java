package team1BW.AziendaDelleEnergie.fattura.payloads;

import team1BW.AziendaDelleEnergie.fattura.enums.StatoFattura;

import java.time.LocalDate;

public record FatturaDTO(
        Long id,
        LocalDate dataFattura,
        double importo,
        Long numeroFattura,
        StatoFattura stato
        // aggiungere l id del cliente cosi da farlo spuntare quando si vorra visualizzare una fattura
) {
}
