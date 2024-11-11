package team1BW.AziendaDelleEnergie.comuni;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team1BW.AziendaDelleEnergie.provincie.Provincia;
import team1BW.AziendaDelleEnergie.provincie.ProvinciaService;

import java.io.File;
import java.util.*;


@Service
public class CSVServiceComune {
    @Autowired
    private ProvinciaService provinciaService;

    public List<Comune> readComuniFromCsv(String nomeFile) {
        try {
            CsvMapper mapper = new CsvMapper();
            CsvSchema inputSchema = CsvSchema.builder()
                    .addColumn("Codice Provincia (Storico)(1)")
                    .addColumn("Progressivo del Comune (2)")
                    .addColumn("Denominazione in italiano")
                    .addColumn("Provincia")
                    .setSkipFirstDataRow(true)
                    .setColumnSeparator(';')
                    .build();
            File file = new File(nomeFile);
            List<Comune> comuni = new ArrayList<>();
            MappingIterator<ComuneCSV> it = mapper.readerFor(ComuneCSV.class).with(inputSchema).readValues(file);
            List<ComuneCSV> rows = it.readAll();

            for (ComuneCSV row : rows) {
                System.out.println("Riga letta: " + row.getDenominazione() + ", " + row.getNomeProvincia());
                String nomeProvincia = row.getNomeProvincia();
                if(nomeProvincia.equals("Verbano-Cusio-Ossola")){
                    nomeProvincia = "Verbania";
                }
                if(nomeProvincia.equals("Valle d'Aosta/Vallée d'Aoste")){
                    nomeProvincia = "Aosta";
                }
                if(nomeProvincia.equals("Monza e della Brianza")){
                    nomeProvincia = "Monza-Brianza";
                }
                if(nomeProvincia.equals("Bolzano/Bozen")){
                    nomeProvincia = "Bolzano";
                }
                if(nomeProvincia.equals("La Spezia")){
                    nomeProvincia = "La-Spezia";
                }
                if(nomeProvincia.equals("Reggio nell'Emilia")){
                    nomeProvincia = "Reggio-Emilia";
                }
                if(nomeProvincia.equals("Forlì-Cesena")){
                    nomeProvincia = "Forli-Cesena";
                }
                if(nomeProvincia.equals("Pesaro e Urbino")){
                    nomeProvincia = "Pesaro-Urbino";
                }
                if(nomeProvincia.equals("Ascoli Piceno")){
                    nomeProvincia = "Ascoli-Piceno";
                }
                if(nomeProvincia.equals("Reggio Calabria")){
                    nomeProvincia = "Reggio-Calabria";
                }
                if(nomeProvincia.equals("Vibo Valentia")){
                    nomeProvincia = "Vibo-Valentia";
                }
                if(nomeProvincia.equals("Sud Sardegna")){
                    provinciaService.saveProvincia(new Provincia("Sud Sardegna", "SU", "Sardegna"));
                }
                Provincia foundProvincia = provinciaService.findByProvincia(nomeProvincia);
                comuni.add(new Comune(row.getDenominazione(), foundProvincia));
            }
            return comuni;
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

}
