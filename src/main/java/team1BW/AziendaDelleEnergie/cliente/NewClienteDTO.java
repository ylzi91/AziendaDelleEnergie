package team1BW.AziendaDelleEnergie.cliente;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record NewClienteDTO(
        @NotEmpty(message = "La ragione sociale è obbligatoria!")
        @Size(min = 2, max = 100, message = "La ragione sociale deve essere compresa tra 2 e 100 caratteri!")
        String ragioneSociale,

        @NotEmpty(message = "La partita IVA è obbligatoria!")
        @Pattern(regexp = "^[0-9]{11}$", message = "La partita IVA deve avere 11 cifre!")
        String partitaIva,

        @NotEmpty(message = "L'email è obbligatoria!")
        @Email(message = "L'email inserita non è valida!")
        String email,

        @NotNull(message = "La data di inserimento è obbligatoria!")
        LocalDate dataInserimento,

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
        TipoCliente tipoCliente
) {
}
