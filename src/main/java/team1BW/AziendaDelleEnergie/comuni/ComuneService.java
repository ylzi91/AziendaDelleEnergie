package team1BW.AziendaDelleEnergie.comuni;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComuneService {
    @Autowired ComuneRepository comuneRepository;

    @Autowired
    CSVServiceComune csvServiceComune;

    public void saveAllComuni(String nomefile){
        List<Comune> comuni = csvServiceComune.readComuniFromCsv(nomefile);
        comuneRepository.saveAll(comuni);
    }

}
