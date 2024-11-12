package team1BW.AziendaDelleEnergie.fattura.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import team1BW.AziendaDelleEnergie.cliente.entities.Cliente;
import team1BW.AziendaDelleEnergie.fattura.enums.StatoFattura;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "fatture")
public class Fattura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dataFattura;

    private double importo;

    @Column(unique = true, nullable = false)
    private Long numeroFattura;

    @Enumerated(EnumType.STRING)
    private StatoFattura stato;
    @ManyToOne
    private Cliente cliente;


}
