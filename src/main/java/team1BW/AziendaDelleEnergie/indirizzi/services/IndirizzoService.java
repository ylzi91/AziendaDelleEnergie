package team1BW.AziendaDelleEnergie.indirizzi.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import team1BW.AziendaDelleEnergie.comuni.Comune;
import team1BW.AziendaDelleEnergie.comuni.ComuneService;
import team1BW.AziendaDelleEnergie.exceptions.NotFoundException;
import team1BW.AziendaDelleEnergie.indirizzi.entities.Indirizzo;
import team1BW.AziendaDelleEnergie.indirizzi.payloads.NuovoIndirizzoDTO;
import team1BW.AziendaDelleEnergie.indirizzi.repositories.IndirizzoRepository;

@Service
public class IndirizzoService {
    @Autowired
    private IndirizzoRepository indirizzoRepository;
    @Autowired
    private ComuneService comuneService;

    //@Autowired
    //private PasswordEncoder bcrypt;


    // SAVE NUOVO INDIRIZZO
    public Indirizzo save(NuovoIndirizzoDTO body) {
        Comune foundComune = comuneService.trovaComune(body.nomeComune());
        Indirizzo newIndirizzo = new Indirizzo(body.cap(), body.civico(), body.localita(), body.via(), foundComune);
        return this.indirizzoRepository.save(newIndirizzo);
    }

    // FIND ALL filtrato dalla size
    public Page<Indirizzo> findAll(int page, int size, String sortBy) {
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.indirizzoRepository.findAll(pageable);
    }

    // FIND BY ID
    public Indirizzo findById(Long indirizzoId) {
        return this.indirizzoRepository.findById(indirizzoId).orElseThrow(() -> new NotFoundException(indirizzoId));
    }

    // FIND BY ID AND UPDATE
    public Indirizzo findByIdAndUpdate(Long indirizzoId, NuovoIndirizzoDTO body) {
        Indirizzo found = this.findById(indirizzoId);
        // controlli ?
        found.setCap(body.cap());
        found.setCivico(body.civico());
        found.setLocalita(body.localita());
        found.setVia(body.via());
        return this.indirizzoRepository.save(found);
    }

    // FIND AND DELETE
    @Transactional
    public void findByIdAndDelete(Long indirizzoId) {
        Indirizzo found = this.findById(indirizzoId);
        this.indirizzoRepository.delete(found);
    }

}
