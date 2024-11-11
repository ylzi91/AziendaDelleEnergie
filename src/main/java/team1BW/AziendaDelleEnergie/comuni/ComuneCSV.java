package team1BW.AziendaDelleEnergie.comuni;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
@ToString
public class ComuneCSV {
    @JsonProperty("Codice Provincia (Storico)(1)")
    private String codProv;
    @JsonProperty("Progressivo del Comune (2)")
    private String progressivo;
    @JsonProperty("Denominazione in italiano")
    private String denominazione;
    @JsonProperty("Provincia")
    private String nomeProvincia;

}
