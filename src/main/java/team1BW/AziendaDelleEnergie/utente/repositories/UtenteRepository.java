package team1BW.AziendaDelleEnergie.utente.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team1BW.AziendaDelleEnergie.utente.entities.Utente;

import java.util.Optional;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, Long> {

    Optional<Utente> findByUsername(String username);

    boolean existsByEmail(String email);
}