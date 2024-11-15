package team1BW.AziendaDelleEnergie.cliente.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import team1BW.AziendaDelleEnergie.cliente.entities.Cliente;
import team1BW.AziendaDelleEnergie.cliente.payloads.NewClienteDTO;
import team1BW.AziendaDelleEnergie.cliente.services.ClienteService;
import team1BW.AziendaDelleEnergie.exceptions.BadRequestException;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/clienti")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    //-----------------------------ADMIN--------------------------
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente saveCliente(@RequestBody @Validated NewClienteDTO newClienteDTO, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String message = validationResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).collect(Collectors.joining(". "));
            throw new BadRequestException(message);
        }
        return clienteService.saveClient(newClienteDTO);
    }

    @PutMapping("/{clienteId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Cliente findByIdAndUpdate(@PathVariable Long clienteId, @RequestBody @Validated NewClienteDTO body,
                                     BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            validationResult.getAllErrors().forEach(System.out::println);
            throw new BadRequestException("Ci sono stati errori nel payload cliente!");
        }
        return this.clienteService.findClientByIdAndUpdate(clienteId, body);
    }

    @DeleteMapping("/{clienteId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable Long clienteId) {
        this.clienteService.findClientByIdAndDelete(clienteId);
    }

    //-----------------------------UTENTE----------------------------------------

    @GetMapping
    public Page<Cliente> getAllClients(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size,
                                       @RequestParam(defaultValue = "id") String sortBy,
                                       @RequestParam(defaultValue = "ASC") String direction) {
        return this.clienteService.findAllClient(page, size, sortBy, direction);
    }

    @GetMapping("/{clienteId}")
    public Cliente findById(@PathVariable Long clienteId) {
        return this.clienteService.findClientById(clienteId);
    }

//----------------------------------Filtro---------------------------

    @GetMapping("/filterclients")
    public Page<Cliente> filtro(
            @RequestParam(required = false) Double fatturatoAnnuale,
            @RequestParam(required = false) String dataInserimento,
            @RequestParam(required = false) String dataUltimoContatto,
            @RequestParam(required = false) String nomeCliente,
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String direction) {
        System.out.println("data ins: " + dataUltimoContatto);

        return clienteService.filterClients(fatturatoAnnuale, dataInserimento, dataUltimoContatto, nomeCliente, page, size, sortBy, direction);
    }
}




