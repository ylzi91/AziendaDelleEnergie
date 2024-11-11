package team1BW.AziendaDelleEnergie.comuni;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import team1BW.AziendaDelleEnergie.provincie.Provincia;

@Entity
@Table(name = "comuni")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Comune {
    @Id
    @GeneratedValue
    private Long id;
    @JsonProperty("Denominazione in italiano")
    private String nome;

    @JsonProperty("Provincia")
    @ManyToOne
    private Provincia provincia;

    public Comune(String nome, Provincia provincia) {
        this.nome = nome;
        this.provincia = provincia;
    }
}
