package team1BW.AziendaDelleEnergie.provincie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvinciaRepository extends JpaRepository <Provincia, String> {

}
