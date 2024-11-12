package team1BW.AziendaDelleEnergie.fattura.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import team1BW.AziendaDelleEnergie.cliente.services.ClienteService;
import team1BW.AziendaDelleEnergie.exceptions.NotFoundException;
import team1BW.AziendaDelleEnergie.fattura.entities.Fattura;
import team1BW.AziendaDelleEnergie.fattura.payloads.CreateFatturaDTO;
import team1BW.AziendaDelleEnergie.fattura.repositories.FatturaRepository;

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

    public Fattura createFattura(CreateFatturaDTO dto) {
        Fattura newFattura = new Fattura();
        newFattura.setDataFattura(dto.dataFattura());
        newFattura.setImporto(dto.importo());
        newFattura.setNumeroFattura(dto.numeroFattura());
        newFattura.setStato(dto.stato());
        newFattura.setCliente(clienteService.searchByPIva(dto.pIva()));
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
}
