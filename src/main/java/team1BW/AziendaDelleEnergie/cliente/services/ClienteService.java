package team1BW.AziendaDelleEnergie.cliente.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
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
import java.util.Date;
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

        NuovoIndirizzoDTO indirizzoLegale;
        NuovoIndirizzoDTO indirizzoOperativo;
        BindingResult validationResult;
        if(body.via() == null || body.civico() == null || body.localita() == null || body.cap() == null || body.nomeComune() == null)
            indirizzoLegale = null;
        else
            indirizzoLegale = new NuovoIndirizzoDTO(body.via(), body.civico(), body.localita(), body.cap(), body.nomeComune());

        if(body.viaOp() == null || body.civicoOp() == null || body.localitaOp() == null || body.capOp() == null || body.nomeComuneOp() == null)
            indirizzoOperativo = null;
        else
            indirizzoOperativo = new NuovoIndirizzoDTO(body.viaOp(), body.civicoOp(), body.localitaOp(), body.capOp(), body.nomeComuneOp());

        Cliente newCliente = new Cliente(
                body.nomeCliente(),
                body.ragioneSociale(),
                body.partitaIva(),
                body.email(),
                LocalDate.now(),
                body.dataUltimoContatto(),
                body.fatturatoAnnuale(),
                body.pec(),
                body.telefono(),
                body.emailContatto(),
                body.nomeContatto(),
                body.cognomeContatto(),
                body.telefonoContatto(),
                body.logoAziendale(),
                body.tipoCliente(),
                indirizzoLegale == null ? null : indirizzoService.save(indirizzoLegale),
                indirizzoOperativo == null ? null : indirizzoService.save(indirizzoOperativo)
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
    public List<Cliente> filterClients(Double fatturatoAnnuale,String dataInserimento,
                                       String dataUltimoContatto, String nomeCliente) {

        List<Cliente> clienti = new ArrayList<>();

        Specification<Cliente> specification = Specification.where(null);

        if(fatturatoAnnuale != null)
            specification = specification.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("fatturatoAnnuale"), fatturatoAnnuale));
        if(dataInserimento != null)
            specification = specification.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("dataInserimento"), LocalDate.parse(dataInserimento)));
        if(dataUltimoContatto != null)
            specification = specification.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("dataUltimoContatto"), LocalDate.parse(dataUltimoContatto)));
        if (nomeCliente != null)
            specification = specification.and((root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("nomeCliente")), "%" + nomeCliente.toLowerCase() + "%"));

        clienti = clienteRepository.findAll(specification);


        if (clienti.isEmpty()) {
            List<String> criterifiltro = new ArrayList<>();
            if(dataInserimento != null) criterifiltro.add("data di inserimento: " + dataInserimento);
            if (fatturatoAnnuale != null) criterifiltro.add("fatturato annuale: " + fatturatoAnnuale);
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

    public List<Cliente> filterFromDataIns(String dataIns){
        return clienteRepository.findBydataInserimento(LocalDate.parse(dataIns));
    }

    public List<Cliente> filterFromDataUltimoContatto(String dataCont){
        return clienteRepository.findBydataUltimoContatto(LocalDate.parse(dataCont));
    }

}

