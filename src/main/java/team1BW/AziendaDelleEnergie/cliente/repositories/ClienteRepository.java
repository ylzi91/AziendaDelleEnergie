package team1BW.AziendaDelleEnergie.cliente.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import team1BW.AziendaDelleEnergie.cliente.entities.Cliente;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByPartitaIva(String partitaIva);

    Optional<Cliente> findByEmail(String email);

    Optional<Cliente> findByPec(String pec);

    Optional<Cliente> findByEmailContatto(String emailContatto);

    //filtro clienti
    @Query("SELECT c FROM Cliente c WHERE " +
            "(:fatturatoAnnuale IS NULL OR c.fatturatoAnnuale = :fatturatoAnnuale) AND" +
            "(:dataInserimento IS NULL OR c.dataInserimento = :dataInserimento) AND " +
            "(:dataUltimoContatto IS NULL OR c.dataUltimoContatto = :dataUltimoContatto) AND" +
            "(:nomeCliente IS NULL OR LOWER(c.nomeCliente) LIKE LOWER (CONCAT('%', :nomeCliente, '%')))")
    List<Cliente> filtroClienti(
            @Param("fatturatoAnnuale") Double fatturatoAnnuale,
            @Param("dataInserimento") LocalDate dataInserimento,
            @Param("dataUltimoContatto") LocalDate dataUltimoContatto,
            @Param("nomeCliente") String nomeCliente);
}
