package team1BW.AziendaDelleEnergie.ruoliUtente.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team1BW.AziendaDelleEnergie.ruoliUtente.entities.Ruolo;

import java.util.Optional;

@Repository
public interface RuoloRepository extends JpaRepository<Ruolo, Long> {
    Optional<Ruolo> findByNome(String nome);
}
