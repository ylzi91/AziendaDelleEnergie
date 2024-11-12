package team1BW.AziendaDelleEnergie.cliente.entities;

import jakarta.persistence.*;
import lombok.*;
import team1BW.AziendaDelleEnergie.cliente.enums.TipoCliente;
import team1BW.AziendaDelleEnergie.indirizzi.entities.Indirizzo;

import java.time.LocalDate;

@Entity
@Table(name = "clienti")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private long id;
    private String nomeCliente;
    private String ragioneSociale;
    private String partitaIva;
    private String email;
    private LocalDate dataInserimento;
    private LocalDate dataUltimoContatto;
    private double fatturatoAnnuale;
    private String pec;
    private String telefono;
    private String emailContatto;
    private String nomeContatto;
    private String cognomeContatto;
    private String telefonoContatto;
    private String logoAziendale;
    @OneToOne
    private Indirizzo indirizzoSedeLegale;
    @OneToOne
    private Indirizzo indirizzoSedeOperativa;
    @Enumerated(EnumType.STRING)
    private TipoCliente tipoCliente;

    public Cliente(String nomeCliente, String ragioneSociale, String partitaIva, String email,
                   LocalDate dataInserimento, LocalDate dataUltimoContatto, double fatturatoAnnuale,
                   String pec, String telefono, String emailContatto, String nomeContatto,
                   String cognomeContatto, String telefonoContatto, String logoAziendale,
                   TipoCliente tipoCliente, Indirizzo indirizzoSedeLegale) {
        this.nomeCliente = nomeCliente;
        this.ragioneSociale = ragioneSociale;
        this.partitaIva = partitaIva;
        this.email = email;
        this.dataInserimento = dataInserimento;
        this.dataUltimoContatto = dataUltimoContatto;
        this.fatturatoAnnuale = fatturatoAnnuale;
        this.pec = pec;
        this.telefono = telefono;
        this.emailContatto = emailContatto;
        this.nomeContatto = nomeContatto;
        this.cognomeContatto = cognomeContatto;
        this.telefonoContatto = telefonoContatto;
        this.logoAziendale = logoAziendale;
        this.tipoCliente = tipoCliente;
        this.indirizzoSedeLegale = indirizzoSedeLegale;
    }
}
