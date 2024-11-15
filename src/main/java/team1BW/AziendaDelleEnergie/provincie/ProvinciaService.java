package team1BW.AziendaDelleEnergie.provincie;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProvinciaService {
    @Autowired
    ProvinciaRepository provinciaRepository;
    @Autowired
    CSVServiceProvince csvServiceProvince;

    public void saveAllProvincie(String nomeFile){
        List<Provincia> provincie = csvServiceProvince.readProvincieFromCsv(nomeFile);
        provinciaRepository.saveAll(provincie);
    }

    public Provincia findByProvincia(String nomeProvincia){
        return provinciaRepository.findById(nomeProvincia).orElseThrow(NoSuchElementException::new);
    }

    public Provincia findByProvinciaAndUpdate(String nomeProvincia, String newName){
        Provincia found = findByProvincia(nomeProvincia);
        found.setProvincia(newName);
        return provinciaRepository.save(found);
    }

    public void saveProvincia(Provincia provincia){
        provinciaRepository.save(provincia);
    }

    public void findByProvinciaAndDelete(String nomeProvincia){
        Provincia found = findByProvincia(nomeProvincia);
        provinciaRepository.delete(found);
    }



}
