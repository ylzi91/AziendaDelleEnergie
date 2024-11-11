package team1BW.AziendaDelleEnergie.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


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
    // many Indirizzo - one Comune



    // costruttore personalizzato
    public Indirizzo(int cap, String civico, String localita, String via) {
        this.cap = cap;
        this.civico = civico;
        this.localita = localita;
        this.via = via;
    }

}
