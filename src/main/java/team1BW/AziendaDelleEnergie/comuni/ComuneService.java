package team1BW.AziendaDelleEnergie.comuni;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team1BW.AziendaDelleEnergie.exceptions.NotFoundException;

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

    public Comune trovaComune(String nomeComune){

       if(comuneRepository.findByNome(nomeComune).isEmpty()){
           throw new NotFoundException("Il comune " + nomeComune + " non è stato trovato");
       }
       return comuneRepository.findByNome(nomeComune).getFirst();
    }

}
