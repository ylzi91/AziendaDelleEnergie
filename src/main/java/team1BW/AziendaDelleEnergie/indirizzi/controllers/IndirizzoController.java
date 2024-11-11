package team1BW.AziendaDelleEnergie.indirizzi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import team1BW.AziendaDelleEnergie.indirizzi.entities.Indirizzo;
import team1BW.AziendaDelleEnergie.indirizzi.exceptions.BadRequestException;
import team1BW.AziendaDelleEnergie.indirizzi.payloads.NuovoIndirizzoDTO;
import team1BW.AziendaDelleEnergie.indirizzi.services.IndirizzoService;

@RestController
@RequestMapping("/indirizzi")
public class IndirizzoController {
    @Autowired
    private IndirizzoService indirizzoService;

    // VISUALIZZA TUTTI GLI INDIRIZZI
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<Indirizzo> findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
                                   @RequestParam(defaultValue = "id") String sortBy) {
        return this.indirizzoService.findAll(page, size, sortBy);
    }

    // TORNA UN INDIRIZZO SPECIFICO
    @GetMapping("/{indirizzoId}")
    public Indirizzo findById(@PathVariable Long indirizzoId) { return this.indirizzoService.findById(indirizzoId);}

    // TORNA UN INDIRIZZO PER MODIFICARLO
    @PutMapping("/{indirizzoId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Indirizzo findByIdAndUpdate(@PathVariable Long indirizzoId, @RequestBody @Validated NuovoIndirizzoDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            validationResult.getAllErrors().forEach(System.out::println);
            throw new BadRequestException("Ci sono stati errori nel payload!");
        }
        return this.indirizzoService.findByIdAndUpdate(indirizzoId, body);
    }

    // TORNA UN INDIRIZZO ED ELIMINALO
    @DeleteMapping("/{indirizzoId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable Long indirizzoId) {this.indirizzoService.findByIdAndDelete(indirizzoId);}


}
