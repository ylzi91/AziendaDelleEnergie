package team1BW.AziendaDelleEnergie.ruoliUtente.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team1BW.AziendaDelleEnergie.exceptions.BadRequestException;
import team1BW.AziendaDelleEnergie.ruoliUtente.entities.Ruolo;
import team1BW.AziendaDelleEnergie.ruoliUtente.repositories.RuoloRepository;

@Service
public class RuoloService {
    @Autowired
    private RuoloRepository ruoloRepository;

    public Ruolo creaRuolo(Ruolo ruolo) {
        if (ruoloRepository.findByNome(ruolo.getNome()).isPresent()) {
            throw new BadRequestException("Il ruolo inserita è già esistente!");
        }
        return ruoloRepository.save(ruolo);
    }


}
