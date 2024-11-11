package team1BW.AziendaDelleEnergie.cliente;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByPartitaIva(String partitaIva);

    Optional<Cliente> findByEmail(String email);

    Optional<Cliente> findByPec(String pec);

    Optional<Cliente> findByEmailContatto(String emailContatto);
    
}
