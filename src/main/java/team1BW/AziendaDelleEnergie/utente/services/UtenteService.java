package team1BW.AziendaDelleEnergie.utente.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import team1BW.AziendaDelleEnergie.exceptions.BadRequestException;
import team1BW.AziendaDelleEnergie.exceptions.NotFoundException;
import team1BW.AziendaDelleEnergie.ruoliUtente.entities.Ruolo;
import team1BW.AziendaDelleEnergie.ruoliUtente.repositories.RuoloRepository;
import team1BW.AziendaDelleEnergie.utente.entities.Utente;
import team1BW.AziendaDelleEnergie.utente.payloads.UtenteDTO;
import team1BW.AziendaDelleEnergie.utente.repositories.UtenteRepository;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private PasswordEncoder bcrypt;

    @Autowired
    private RuoloRepository ruoloRepository;

    //-------------------------------------save utente -----------------------------------
    public Utente saveUtente(UtenteDTO body) {
        this.utenteRepository.findByEmail(body.email()).ifPresent(
                utente -> {
                    throw new BadRequestException("Email " + body.email() + " già in uso!");
                }
        );

        this.utenteRepository.findByUsername(body.username()).ifPresent(utente -> {
            throw new BadRequestException("Username " + body.username() + " già in uso!");
        });
        Ruolo ruoloUtente = ruoloRepository.findByNome("USER")
                .orElseThrow(() -> new NotFoundException("Il ruolo 'USER' non e stato trovato!"));

        Utente newUtente = new Utente(body.nome(), body.cognome(), body.username(), body.email(),
                bcrypt.encode(body.password()),
                "https://ui-avatars.com/api/?name=" + body.nome() + "+" + body.cognome());
        newUtente.getRuoli().add(ruoloUtente);
        return this.utenteRepository.save(newUtente);

    }

    //-----------------------------------------find all utenti ---------------------------------
    public Page<Utente> findAll(int page, int size, String sortBy) {
        if (size > 100)
            size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.utenteRepository.findAll(pageable);
    }

    //------------------------------------- findByID utenti--------------------------------------
    public Utente findById(Long utenteId) {
        return this.utenteRepository.findById(utenteId).orElseThrow(() -> new NotFoundException(utenteId));
    }

    //-------------------------------------findByiD utende and Update ----------------
    public Utente findByIdAndUpdate(Long utenteId, UtenteDTO body) {
        Utente foundUtente = this.findById(utenteId);
        if (!foundUtente.getEmail().equals(body.email())) {
            this.utenteRepository.findByEmail(body.email()).ifPresent(
                    utente -> {
                        throw new BadRequestException("Email " + body.email() + " già in uso!");
                    }
            );
        }

        if (!foundUtente.getUsername().equals(body.username())) {
            this.utenteRepository.findByUsername(body.username()).ifPresent(
                    utente -> {
                        throw new BadRequestException("Username " + body.username() + " già in uso!");
                    }
            );
        }

        foundUtente.setNomeUtente(body.nome());
        foundUtente.setCognomeUtente(body.cognome());
        foundUtente.setEmail(body.email());
        foundUtente.setPassword(bcrypt.encode(body.password()));
        foundUtente.setAvatar("https://ui-avatars.com/api/?name=" + body.nome() + "+" + body.cognome());

        return this.utenteRepository.save(foundUtente);
    }

    //------------------------------------------delete utente ------------------------
    public void findByIdAndDelete(Long utenteId) {
        Utente foundUtente = this.findById(utenteId);
        this.utenteRepository.delete(foundUtente);
    }

    //------------------------------------find utente by username -----------------------
    public Utente findByUsername(String username) {
        return this.utenteRepository.findByUsername(username).orElseThrow(() ->
                new NotFoundException("L'utente con lo username: " + username + " non è stato trovato"));
    }

    //----------------------------------- AddRuoloUtente ---------------------------------
    public Utente addRuolo(Long utenteId, Ruolo ruolo) {
        Utente utente = findById(utenteId);
        Ruolo existRuolo = ruoloRepository.findByNome(ruolo.getNome())
                .orElseThrow(() -> new NotFoundException("Il ruolo non è stato trovato!"));
        utente.getRuoli().add(existRuolo);
        return utenteRepository.save(utente);
    }

    //-----------------------------------Delete ruolo -------------------------------
    public Utente deleteRuolo(Long utenteId, Long ruoloId) {
        Utente utente = this.findById(utenteId);
        Ruolo ruolo = ruoloRepository.findById(ruoloId).orElseThrow(() ->
                new NotFoundException("Il ruolo non e stato trovato!"));
        utente.getRuoli().remove(ruolo);
        return utenteRepository.save(utente);
    }
}


