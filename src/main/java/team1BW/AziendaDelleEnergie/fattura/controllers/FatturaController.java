package team1BW.AziendaDelleEnergie.fattura.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import team1BW.AziendaDelleEnergie.fattura.entities.Fattura;
import team1BW.AziendaDelleEnergie.fattura.enums.StatoFattura;
import team1BW.AziendaDelleEnergie.fattura.payloads.CreateFatturaDTO;
import team1BW.AziendaDelleEnergie.fattura.services.FatturaService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/fatture")
public class FatturaController {

    @Autowired
    private FatturaService fatturaService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<Fattura> getAllFatture(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "dataFattura") String sortBy
    ) {
        return fatturaService.findAll(page, size, sortBy);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Fattura createFattura(@Valid @RequestBody CreateFatturaDTO dto) {
        return fatturaService.createFattura(dto);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Fattura getFatturaById(@PathVariable Long id) {
        return fatturaService.findById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Fattura updateFattura(@PathVariable Long id, @Valid @RequestBody CreateFatturaDTO dto) {
        return fatturaService.updateFattura(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFattura(@PathVariable Long id) {
        fatturaService.deleteFattura(id);
    }

    @GetMapping("/filtro")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Fattura> filtroFatture(
            @RequestParam(required = false) Long clienteId,
            @RequestParam(required = false) StatoFattura stato,
            @RequestParam(required = false) LocalDate data,
            @RequestParam(required = false) Integer anno,
            @RequestParam(required = false) Double importoMin,
            @RequestParam(required = false) Double importoMax
    ) {
        return fatturaService.filtroFatture(clienteId, stato, data, anno, importoMin, importoMax);
    }
}
