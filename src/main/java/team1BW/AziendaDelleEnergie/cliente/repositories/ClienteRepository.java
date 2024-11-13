package team1BW.AziendaDelleEnergie.cliente.repositories;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import team1BW.AziendaDelleEnergie.cliente.entities.Cliente;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>, JpaSpecificationExecutor<Cliente> {
    Optional<Cliente> findByPartitaIva(String partitaIva);

    Optional<Cliente> findByEmail(String email);

    Optional<Cliente> findByPec(String pec);

    Optional<Cliente> findByEmailContatto(String emailContatto);

    List<Cliente> findBydataInserimento(LocalDate dataInserimento);

    List<Cliente> findBydataUltimoContatto(LocalDate dataUltimoContatto);
    //filtro clienti

}


