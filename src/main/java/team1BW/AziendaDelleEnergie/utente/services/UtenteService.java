package team1BW.AziendaDelleEnergie.utente.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import team1BW.AziendaDelleEnergie.exceptions.BadRequestException;
import team1BW.AziendaDelleEnergie.exceptions.NotFoundException;
import team1BW.AziendaDelleEnergie.utente.entities.Utente;
import team1BW.AziendaDelleEnergie.utente.payloads.UtenteDTO;
import team1BW.AziendaDelleEnergie.utente.repositories.UtenteRepository;
import team1BW.AziendaDelleEnergie.utente.tools.MailgunSender;

import java.io.IOException;
import java.util.Optional;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private PasswordEncoder bcrypt;

    @Autowired
    private MailgunSender mailgunSender;

    @Autowired
    private Cloudinary cloudinary;

    //-------------------------------------save utente -----------------------------------
    public Utente saveUtente(UtenteDTO body) {
        // Verifica se l'email è già in uso
        this.utenteRepository.findByEmail(body.email()).ifPresent(
                utente -> {
                    throw new BadRequestException("Email " + body.email() + " già in uso!");
                }
        );

        // Verifica se lo username è già in uso
        this.utenteRepository.findByUsername(body.username()).ifPresent(utente -> {
            throw new BadRequestException("Username " + body.username() + " già in uso!");
        });

        // Crea un nuovo utente con avatar generato automaticamente
        Utente newUtente = new Utente(body.nome(), body.cognome(), body.username(), body.email(),
                bcrypt.encode(body.password()),
                "https://ui-avatars.com/api/?name=" + body.nome() + "+" + body.cognome());

        Utente savedUser = this.utenteRepository.save(newUtente);

        // Invia email di registrazione
        mailgunSender.sendRegistrationEmail(savedUser);

        return savedUser;
    }

    //-----------------------------------------find all utenti ---------------------------------
    public Page<Utente> findAll(int page, int size, String sortBy) {
        // Limita la dimensione della pagina per evitare richieste troppo grandi
        if (size > 100) {
            size = 100;
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.utenteRepository.findAll(pageable);
    }

    //------------------------------------- findByID utenti--------------------------------------
    public Utente findById(Long utenteId) {
        return this.utenteRepository.findById(utenteId).orElseThrow(() -> new NotFoundException(utenteId));
    }

    //-------------------------------------findByiD utente and Update ----------------
    public Utente findByIdAndUpdate(Long utenteId, UtenteDTO body) {
        Utente foundUtente = this.findById(utenteId);

        // Verifica se la nuova email è già in uso
        if (!foundUtente.getEmail().equals(body.email())) {
            this.utenteRepository.findByEmail(body.email()).ifPresent(
                    utente -> {
                        throw new BadRequestException("Email " + body.email() + " già in uso!");
                    }
            );
        }

        // Verifica se il nuovo username è già in uso
        if (!foundUtente.getUsername().equals(body.username())) {
            this.utenteRepository.findByUsername(body.username()).ifPresent(
                    utente -> {
                        throw new BadRequestException("Username " + body.username() + " già in uso!");
                    }
            );
        }

        // Aggiorna le informazioni dell'utente
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

    //-----------------------------------find utente by email --------------------------
    public Optional<Utente> findByEmail(String email) {
        return this.utenteRepository.findByEmail(email);
    }

    //------------------------------------upload avatar ------------------------------
    public String uploadAvatar(Long utenteId, MultipartFile file) {
        Utente utente = findById(utenteId);
        String url;
        try {
            // Carica il file su Cloudinary e ottieni l'URL dell'immagine
            url = (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
            utente.setAvatar(url);
            utenteRepository.save(utente);
        } catch (IOException e) {
            throw new BadRequestException("Errore durante l'upload dell'immagine.");
        }
        return url;
    }
}
