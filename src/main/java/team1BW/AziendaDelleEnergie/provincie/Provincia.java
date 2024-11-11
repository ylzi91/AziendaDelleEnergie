package team1BW.AziendaDelleEnergie.provincie;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "provincie")
@Getter
@Setter
@NoArgsConstructor
public class Provincia {

    @Id
    @JsonProperty("Provincia")
    private String provincia;

    @JsonProperty("Sigla")
    private String sigla;

    @JsonProperty("Regione")
    private String regione;


    public Provincia(String provincia, String sigla, String regione) {
        this.provincia = provincia;
        this.sigla = sigla;
        this.regione = regione;
    }
}
