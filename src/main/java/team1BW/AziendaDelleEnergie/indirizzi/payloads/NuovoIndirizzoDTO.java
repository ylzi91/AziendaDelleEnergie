package team1BW.AziendaDelleEnergie.indirizzi.payloads;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record NuovoIndirizzoDTO(@NotEmpty(message = "Il nome della via è obbligatorio!")
                                @Size(min = 2, max = 40, message = "Il nome della via deve essere compreso tra 2 e 40 caratteri!")
                                String via,
                                @NotEmpty(message = "Il numero civico è obbligatorio!")
                                @Size(min = 1, max = 6, message = "Il numero civico deve essere compreso tra 2 e 6 caratteri!")
                                String civico,
                                // IL CIVICO PIU LUNGO D'ITALIA E' A ROMA --> 14115 QUINDI 5 CIFRE + EVENTUALE A/B/C ETC...
                                @NotEmpty(message = "La località è obbligatoria!")
                                @Size(min = 2, max = 40, message = "La localita inserita deve essere compresa tra 2 e 40")
                                String localita,
                                @NotEmpty(message = "Il CAP è obbligatorio!")
                                @Size(min = 5, max = 5, message = "Il CAP deve essere di 5 cifre")
                                int cap) {
                                // CAP 45100
}
