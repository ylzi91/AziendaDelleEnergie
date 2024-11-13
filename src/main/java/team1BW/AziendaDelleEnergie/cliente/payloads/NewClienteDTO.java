package team1BW.AziendaDelleEnergie.cliente.payloads;

import jakarta.validation.constraints.*;
import team1BW.AziendaDelleEnergie.cliente.enums.TipoCliente;
import team1BW.AziendaDelleEnergie.indirizzi.entities.Indirizzo;

import java.time.LocalDate;

public record NewClienteDTO(
        @NotEmpty(message = "Il nome cliente è obbligatorio!")
        @Size(min = 2, max = 100, message = "Il nome cliente deve essere compresa tra 2 e 100 caratteri!")
        String nomeCliente,

        @NotEmpty(message = "La ragione sociale è obbligatoria!")
        @Size(min = 2, max = 100, message = "La ragione sociale deve essere compresa tra 2 e 100 caratteri!")
        String ragioneSociale,

        @NotEmpty(message = "La partita IVA è obbligatoria!")
        @Pattern(regexp = "^[0-9]{11}$", message = "La partita IVA deve avere 11 cifre!")
        String partitaIva,

        @NotEmpty(message = "L'email è obbligatoria!")
        @Email(message = "L'email inserita non è valida!")
        String email,

        @PositiveOrZero(message = "Inserire un numero positivo o zero!")
        double fatturatoAnnuale,

        @NotEmpty(message = "La PEC è obbligatoria!")
        @Email(message = "La PEC deve essere in un formato valido!")
        String pec,

        @NotEmpty(message = "Il numero di telefono è obbligatorio!")
        String telefono,

        @NotEmpty(message = "L'email del contatto è obbligatoria!")
        @Email(message = "L'email del contatto deve essere in un formato valido!")
        String emailContatto,

        @NotEmpty(message = "Il nome del contatto è obbligatorio!")
        @Size(min = 2, max = 30, message = "Il nome del contatto deve essere compreso tra 2 e 30 caratteri!")
        String nomeContatto,

        @NotEmpty(message = "Il cognome del contatto è obbligatorio!")
        @Size(min = 2, max = 30, message = "Il cognome del contatto deve essere compreso tra 2 e 30 caratteri!")
        String cognomeContatto,

        @NotEmpty(message = "Il telefono del contatto è obbligatorio!")
        String telefonoContatto,

        String logoAziendale,

        @NotNull(message = "Inserire un tipo cliente!")
        TipoCliente tipoCliente,

        LocalDate dataUltimoContatto,

        @NotEmpty(message = "La via è obbligatoria")
        String via,

        @NotEmpty(message = "Il cap è obbligatorio")
        Integer cap,

        @NotEmpty(message = "Il civico è obbligatorio")
        String civico,

        @NotEmpty(message = "La località è obbligatoria")
        String localita,

        @NotEmpty(message = "Il nome del comune è obbligatorio")
        String nomeComune,

        String viaOp,

        Integer capOp,

        String civicoOp,

        String localitaOp,

        String nomeComuneOp

) {
}
