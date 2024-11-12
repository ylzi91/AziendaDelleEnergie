package team1BW.AziendaDelleEnergie.cliente.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import team1BW.AziendaDelleEnergie.cliente.entities.Cliente;
import team1BW.AziendaDelleEnergie.cliente.payloads.NewClienteDTO;
import team1BW.AziendaDelleEnergie.cliente.repositories.ClienteRepository;
import team1BW.AziendaDelleEnergie.comuni.ComuneService;
import team1BW.AziendaDelleEnergie.exceptions.BadRequestException;
import team1BW.AziendaDelleEnergie.exceptions.NotFoundException;
import team1BW.AziendaDelleEnergie.indirizzi.entities.Indirizzo;
import team1BW.AziendaDelleEnergie.indirizzi.payloads.NuovoIndirizzoDTO;
import team1BW.AziendaDelleEnergie.indirizzi.services.IndirizzoService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClienteService {
    @Autowired
    IndirizzoService indirizzoService;
    @Autowired
    ComuneService comuneService;

    @Autowired
    private ClienteRepository clienteRepository;

    //------------------------------saveClient--------------------------------------------
    public Cliente saveClient(NewClienteDTO body) {
        this.clienteRepository.findByPartitaIva(body.partitaIva()).ifPresent(cliente -> {
            throw new BadRequestException("Partita IVA " + body.partitaIva() + " già in uso!");
        });
        this.clienteRepository.findByEmail(body.email()).ifPresent(cliente -> {
            throw new BadRequestException("Email " + body.email() + " già in uso!");
        });
        this.clienteRepository.findByPec(body.pec()).ifPresent(cliente -> {
            throw new BadRequestException("Indirizzo Pec " + body.pec() + " già in uso!");
        });
        this.clienteRepository.findByEmailContatto(body.emailContatto()).ifPresent(cliente -> {
            throw new BadRequestException("Email Contatto " + body.emailContatto() + " già in uso! Inserire un'altra email contatto!");
        });

        NuovoIndirizzoDTO nuovoIndirizzoDTO = new NuovoIndirizzoDTO(body.via(), body.civico(), body.localita(), body.cap(), body.nomeComune());
        Cliente newCliente = new Cliente(
                body.nomeCliente(),
                body.ragioneSociale(), body.partitaIva(), body.email(), LocalDate.now(),
                body.dataUltimoContatto(), body.fatturatoAnnuale(), body.pec(),
                body.telefono(), body.emailContatto(), body.nomeContatto(), body.cognomeContatto(),
                body.telefonoContatto(), body.logoAziendale(), body.tipoCliente(), indirizzoService.save(nuovoIndirizzoDTO)
        );
        return this.clienteRepository.save(newCliente);


    }

    //--------------------------------------FindAllClient & Order-----------------------------------------
    public Page<Cliente> findAllClient(int page, int size, String sortBy, String direction) {
        if (size > 100)
            size = 100;
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return this.clienteRepository.findAll(pageable);
    }

    //------------------------------------findClientById-----------------------------------------
    public Cliente findClientById(Long clienteId) {
        return this.clienteRepository.findById(clienteId).orElseThrow(() -> new NotFoundException(clienteId));
    }


    //------------------------------------findClientByIdAndUpdate-----------------------------------------
    public Cliente findClientByIdAndUpdate(Long clienteId, NewClienteDTO body) {
        Cliente clienteFound = this.findClientById(clienteId);
        if (!clienteFound.getPartitaIva().equals(body.partitaIva())) {
            this.clienteRepository.findByPartitaIva(body.partitaIva()).ifPresent(
                    cliente -> {
                        throw new BadRequestException("Partita Iva " + body.partitaIva() + " già in uso!");
                    });
        }
        if (!clienteFound.getEmail().equals(body.email())) {
            this.clienteRepository.findByEmail(body.email()).ifPresent(cliente -> {
                throw new BadRequestException("Email " + body.email() + " già in uso!");
            });
        }
        if (!clienteFound.getPec().equals(body.pec())) {
            this.clienteRepository.findByPec(body.pec()).ifPresent(cliente -> {
                throw new BadRequestException("Indirizzo Pec " + body.pec() + " già in uso!");
            });
        }

        if (!clienteFound.getEmailContatto().equals(body.emailContatto())) {
            this.clienteRepository.findByEmailContatto(body.emailContatto()).ifPresent(cliente -> {
                throw new BadRequestException("Email Contatto " + body.emailContatto() +
                        " già in uso! Inserire un'altra email contatto!");
            });
        }

        clienteFound.setNomeCliente(body.nomeCliente());
        clienteFound.setRagioneSociale(body.ragioneSociale());
        clienteFound.setPartitaIva(body.partitaIva());
        clienteFound.setEmail(body.email());
        clienteFound.setFatturatoAnnuale(body.fatturatoAnnuale());
        clienteFound.setPec(body.pec());
        clienteFound.setTelefono(body.telefono());
        clienteFound.setEmailContatto(body.emailContatto());
        clienteFound.setNomeContatto(body.nomeContatto());
        clienteFound.setCognomeContatto(body.cognomeContatto());
        clienteFound.setTelefonoContatto(body.telefonoContatto());
        clienteFound.setLogoAziendale(body.logoAziendale());
        clienteFound.setTipoCliente(body.tipoCliente());

        //aggiorno la data dell'ultimo contatto solo se la fornisco

        if (body.dataUltimoContatto() != null) {
            clienteFound.setDataUltimoContatto(body.dataUltimoContatto());
        }
        return this.clienteRepository.save(clienteFound);
    }

    //------------------------------------findClientByIdAndDelete-----------------------------------------
    public void findClientByIdAndDelete(Long clienteId) {
        Cliente clienteFound = this.findClientById(clienteId);
        this.clienteRepository.delete(clienteFound);
    }

    //------------------------------------filterClients------------------------------------------------
    public List<Cliente> filterClients(Double fatturatoAnnuale, LocalDate dataInserimento,
                                       LocalDate dataUltimoContatto, String nomeCliente) {
        List<Cliente> clienti = clienteRepository.filtroClienti(
                fatturatoAnnuale, dataInserimento, dataUltimoContatto, nomeCliente);

        if (clienti.isEmpty()) {
            List<String> criterifiltro = new ArrayList<>();
            if (fatturatoAnnuale != null) criterifiltro.add("fatturato annuale: " + fatturatoAnnuale);
            if (dataInserimento != null) criterifiltro.add("data di inserimento: " + dataInserimento);
            if (dataUltimoContatto != null) criterifiltro.add("data dell'ultimo contatto: " + dataUltimoContatto);
            if (nomeCliente != null) criterifiltro.add("nome cliente: '" + nomeCliente + "'");

            String messaggioErrore = "Nessun cliente trovato con questi criteri di ricerca: " + String.join(", ", criterifiltro) + ".";
            throw new NotFoundException(messaggioErrore);
        }

        return clienti;
    }

    public Cliente searchByPIva(String pIva){
        Cliente found = clienteRepository.findByPartitaIva(pIva).orElseThrow(() -> new NotFoundException("Paritita iva numero: " + pIva + " non trovata"));
        return found;
    }

}

