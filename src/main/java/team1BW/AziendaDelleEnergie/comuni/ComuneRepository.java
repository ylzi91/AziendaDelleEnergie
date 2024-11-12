package team1BW.AziendaDelleEnergie.comuni;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComuneRepository extends JpaRepository<Comune, Long> {

    List<Comune> findByNome(String nomeComune);
}
