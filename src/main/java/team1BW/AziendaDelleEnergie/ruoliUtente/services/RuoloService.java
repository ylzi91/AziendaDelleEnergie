package team1BW.AziendaDelleEnergie.ruoliUtente.services;

import jakarta.transaction.Transactional;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import team1BW.AziendaDelleEnergie.exceptions.BadRequestException;
import team1BW.AziendaDelleEnergie.exceptions.NotFoundException;
import team1BW.AziendaDelleEnergie.ruoliUtente.entities.Ruolo;
import team1BW.AziendaDelleEnergie.ruoliUtente.repositories.RuoloRepository;
import team1BW.AziendaDelleEnergie.utente.entities.Utente;
import team1BW.AziendaDelleEnergie.utente.services.UtenteService;

import java.util.List;

@Service
public class RuoloService {
    @Autowired
    private RuoloRepository ruoloRepository;

    @Autowired
    @Lazy
    private UtenteService utenteService;

    public Ruolo cercaRuoloPerNome(String nome) {
        return ruoloRepository.findByNome(nome).orElseThrow(() -> new NotFoundException
                ("Il ruolo con il nome " + nome + " non è stato trovato!"));
    }

    public Ruolo creaRuolo(Ruolo ruolo) {
        if (ruoloRepository.findByNome(ruolo.getNome()).isPresent()) {
            throw new BadRequestException("Il ruolo inserita è già esistente!");
        }
        return ruoloRepository.save(ruolo);
    }

    public Page<Ruolo> findAllRuoli(int page, int size, String sortBy, String direction) {
        if (size > 100)
            size = 100;
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return this.ruoloRepository.findAll(pageable);
    }

    @Transactional
    public void findRuoloAndDelete(String nome) {
        Ruolo foundR = ruoloRepository.findByNome(nome).orElseThrow(() ->
                new NotFoundException("Il ruolo " + nome + " non è stato trovato!"));

        List<Utente> utentiConRuoli = utenteService.findAllByRuoliUtente(foundR);
        for (Utente utente : utentiConRuoli) {
            utente.getRuoli().remove(foundR);
            utenteService.saveUR(utente);
        }
        ruoloRepository.delete(foundR);
    }

}
