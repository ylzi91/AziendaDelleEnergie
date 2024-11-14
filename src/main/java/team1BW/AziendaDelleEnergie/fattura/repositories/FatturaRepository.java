package team1BW.AziendaDelleEnergie.fattura.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import team1BW.AziendaDelleEnergie.fattura.entities.Fattura;
import team1BW.AziendaDelleEnergie.fattura.enums.StatoFattura;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FatturaRepository extends JpaRepository<Fattura, Long> {

    @Query("SELECT f FROM Fattura f WHERE " +
            "(CAST(:clienteId AS long) IS NULL OR f.cliente.id = :clienteId) AND " +
            "(:stato IS NULL OR f.stato = :stato) AND " +
            "(COALESCE(:data, f.dataFattura) = f.dataFattura) AND " +
            "(:anno IS NULL OR EXTRACT(YEAR FROM f.dataFattura) = :anno) AND " +
            "(:importoMin IS NULL OR f.importo >= :importoMin) AND " +
            "(:importoMax IS NULL OR f.importo <= :importoMax)")
    List<Fattura> filtroFatture(
            @Param("clienteId") Long clienteId,
            @Param("stato") StatoFattura stato,
            @Param("data") LocalDate data,
            @Param("anno") Integer anno,
            @Param("importoMin") Double importoMin,
            @Param("importoMax") Double importoMax
    );
}