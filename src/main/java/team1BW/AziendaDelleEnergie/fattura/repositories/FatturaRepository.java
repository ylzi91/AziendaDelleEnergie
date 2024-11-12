package team1BW.AziendaDelleEnergie.fattura.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team1BW.AziendaDelleEnergie.fattura.entities.Fattura;

@Repository
public interface FatturaRepository extends JpaRepository<Fattura, Long> {
}
