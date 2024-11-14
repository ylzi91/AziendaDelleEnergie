package team1BW.AziendaDelleEnergie.ruoliUtente.configuration;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import team1BW.AziendaDelleEnergie.ruoliUtente.entities.Ruolo;
import team1BW.AziendaDelleEnergie.ruoliUtente.repositories.RuoloRepository;

@Component
public class RuoloConfig {

    @Autowired
    private RuoloRepository ruoloRepository;

    // Inizializziamo i ruoli USER e ADMIN all'avvio
    @PostConstruct
    private void ruoloInit() {
        if (ruoloRepository.findByNome("USER").isEmpty()) {
            Ruolo userRuolo = new Ruolo();
            userRuolo.setNome("USER");
            ruoloRepository.save(userRuolo);
        }
        if (ruoloRepository.findByNome("ADMIN").isEmpty()) {
            Ruolo adminRuolo = new Ruolo();
            adminRuolo.setNome("ADMIN");
            ruoloRepository.save(adminRuolo);
        }
    }
}
