package team1BW.AziendaDelleEnergie.ruoliUtente.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import team1BW.AziendaDelleEnergie.ruoliUtente.entities.Ruolo;
import team1BW.AziendaDelleEnergie.ruoliUtente.services.RuoloService;
import team1BW.AziendaDelleEnergie.utente.entities.Utente;
import team1BW.AziendaDelleEnergie.utente.services.UtenteService;

@RestController
@RequestMapping("/ruoli")
public class RuoloController {

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private RuoloService ruoloService;

    @PostMapping("/creaRuolo")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Ruolo creaRuolo(@RequestBody @Validated Ruolo ruolo) {
        return ruoloService.creaRuolo(ruolo);
    }

    @PostMapping("/addRuolo/{utenteId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Utente addRuolo(@PathVariable Long utenteId, @RequestBody @Validated Ruolo ruolo) {
        return utenteService.addRuolo(utenteId, ruolo);
    }

    @DeleteMapping("/deleteRuolo/{utenteId}/{nomeRuolo}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Utente deleteRuolo(@PathVariable Long utenteId, @PathVariable String nomeRuolo) {
        return utenteService.deleteRuolo(utenteId, nomeRuolo);
    }

}
