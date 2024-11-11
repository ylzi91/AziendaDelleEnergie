package team1BW.AziendaDelleEnergie.provincie;


import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.stereotype.Service;
import team1BW.AziendaDelleEnergie.provincie.Provincia;

import java.io.File;
import java.util.Collections;
import java.util.List;

@Service
public class CSVServiceProvince {

    public List<Provincia> readProvincieFromCsv(String nomeFile){
        try {
            CsvMapper mapper = new CsvMapper();
            CsvSchema schema = CsvSchema.emptySchema().withHeader().withColumnSeparator(';');
            File file = new File(nomeFile);
            MappingIterator<Provincia> it = mapper.readerFor(Provincia.class).with(schema).readValues(file);
            return it.readAll();
        }
        catch (Exception e){
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
