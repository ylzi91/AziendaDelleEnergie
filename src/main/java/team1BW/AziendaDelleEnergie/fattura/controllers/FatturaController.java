package team1BW.AziendaDelleEnergie.fattura.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import team1BW.AziendaDelleEnergie.fattura.entities.Fattura;
import team1BW.AziendaDelleEnergie.fattura.payloads.CreateFatturaDTO;
import team1BW.AziendaDelleEnergie.fattura.services.FatturaService;

@RestController
@RequestMapping("/fatture")
public class FatturaController {

    @Autowired
    private FatturaService fatturaService;

    @GetMapping
    public Page<Fattura> getAllFatture(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "dataFattura") String sortBy
    ) {
        return fatturaService.findAll(page, size, sortBy);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Fattura createFattura(@Valid @RequestBody CreateFatturaDTO dto) {
        return fatturaService.createFattura(dto);
    }

    @GetMapping("/{id}")
    public Fattura getFatturaById(@PathVariable Long id) {
        return fatturaService.findById(id);
    }

    @PutMapping("/{id}")
    public Fattura updateFattura(@PathVariable Long id, @Valid @RequestBody CreateFatturaDTO dto) {
        return fatturaService.updateFattura(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFattura(@PathVariable Long id) {
        fatturaService.deleteFattura(id);
    }
}
