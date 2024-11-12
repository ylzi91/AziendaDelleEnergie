package team1BW.AziendaDelleEnergie.indirizzi.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import team1BW.AziendaDelleEnergie.comuni.Comune;


@Entity
@Table(name="indirizzi")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Indirizzo {
    @Id
    @GeneratedValue
    private long id;
    private String via;
    private String civico;
    private String localita;
    private int cap;
    @ManyToOne
    private Comune comune;



    // costruttore personalizzato
    public Indirizzo(int cap, String civico, String localita, String via, Comune comune) {
        this.cap = cap;
        this.civico = civico;
        this.localita = localita;
        this.via = via;
        this.comune = comune;
    }

}
