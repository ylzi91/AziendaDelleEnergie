package team1BW.AziendaDelleEnergie.fattura.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import team1BW.AziendaDelleEnergie.cliente.services.ClienteService;
import team1BW.AziendaDelleEnergie.exceptions.BadRequestException;
import team1BW.AziendaDelleEnergie.exceptions.NotFoundException;
import team1BW.AziendaDelleEnergie.fattura.entities.Fattura;
import team1BW.AziendaDelleEnergie.fattura.enums.StatoFattura;
import team1BW.AziendaDelleEnergie.fattura.payloads.CreateFatturaDTO;
import team1BW.AziendaDelleEnergie.fattura.repositories.FatturaRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class FatturaService {

    @Autowired
    private FatturaRepository fatturaRepository;

    @Autowired
    private ClienteService clienteService;

    public Page<Fattura> findAll(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return fatturaRepository.findAll(pageable);
    }

    public Fattura createFattura(CreateFatturaDTO body) {
        fatturaRepository.findByNumeroFattura(body.numeroFattura()).ifPresent(existingFattura -> {
            throw new BadRequestException("Numero fattura " + body.numeroFattura() + " già in uso!");
        });
        Fattura newFattura = new Fattura();
        newFattura.setDataFattura(body.dataFattura());
        newFattura.setImporto(body.importo());

        newFattura.setStato(body.stato());
        newFattura.setCliente(clienteService.searchByPIva(body.pIva()));
        return fatturaRepository.save(newFattura);
    }

    public Fattura findById(Long id) {
        return fatturaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Fattura non trovata"));
    }

    public Fattura updateFattura(Long id, CreateFatturaDTO dto) {
        Fattura fattura = findById(id);
        fattura.setDataFattura(dto.dataFattura());
        fattura.setImporto(dto.importo());
        fattura.setNumeroFattura(dto.numeroFattura());
        fattura.setStato(dto.stato());
        return fatturaRepository.save(fattura);
    }

    public void deleteFattura(Long id) {
        fatturaRepository.deleteById(id);
    }

    public List<Fattura> filtroFatture(Long clienteId, StatoFattura stato, LocalDate data, Integer anno, Double importoMin, Double importoMax) {
        return fatturaRepository.filtroFatture(clienteId, stato, data, anno, importoMin, importoMax);
    }
}
