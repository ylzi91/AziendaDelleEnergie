package team1BW.AziendaDelleEnergie.utente.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import team1BW.AziendaDelleEnergie.utente.entities.Utente;
import team1BW.AziendaDelleEnergie.utente.exceptions.UserNotFoundException;
import team1BW.AziendaDelleEnergie.utente.payloads.UtenteCreateDTO;
import team1BW.AziendaDelleEnergie.utente.payloads.UtenteDTO;
import team1BW.AziendaDelleEnergie.utente.payloads.UtenteResponseDTO;
import team1BW.AziendaDelleEnergie.utente.repositories.UtenteRepository;

@Service
public class UtenteService {

    private final UtenteRepository utenteRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UtenteService(UtenteRepository utenteRepository, PasswordEncoder passwordEncoder) {
        this.utenteRepository = utenteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(UtenteCreateDTO utenteCreateDTO) {
        if (utenteRepository.existsByEmail(utenteCreateDTO.email())) {
            throw new IllegalArgumentException("Email già in uso");
        }

        Utente utente = new Utente();
        utente.setNomeUtente(utenteCreateDTO.nome());
        utente.setCognomeUtente(utenteCreateDTO.cognome());
        utente.setUsername(utenteCreateDTO.username());
        utente.setEmail(utenteCreateDTO.email());
        utente.setPassword(passwordEncoder.encode(utenteCreateDTO.password()));
        utente.setAvatar(utenteCreateDTO.avatar());
        utente.setRuolo(utenteCreateDTO.ruolo());

        utenteRepository.save(utente);
    }

    public UtenteResponseDTO createUser(UtenteCreateDTO utenteCreateDTO) {
        Utente utente = new Utente();
        utente.setNomeUtente(utenteCreateDTO.nome());
        utente.setCognomeUtente(utenteCreateDTO.cognome());
        utente.setUsername(utenteCreateDTO.username());
        utente.setEmail(utenteCreateDTO.email());
        utente.setPassword(passwordEncoder.encode(utenteCreateDTO.password()));
        utente.setAvatar(utenteCreateDTO.avatar());
        utente.setRuolo(utenteCreateDTO.ruolo());

        Utente savedUser = utenteRepository.save(utente);

        return new UtenteResponseDTO(
                savedUser.getId(),
                savedUser.getNomeUtente(),
                savedUser.getCognomeUtente(),
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getAvatar()
        );
    }

    public UtenteResponseDTO getUserById(Long id) {
        Utente utente = utenteRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Utente con ID " + id + " non trovato"));

        return new UtenteResponseDTO(
                utente.getId(),
                utente.getNomeUtente(),
                utente.getCognomeUtente(),
                utente.getUsername(),
                utente.getEmail(),
                utente.getAvatar()
        );
    }

    public UtenteResponseDTO updateUser(Long id, UtenteDTO utenteDTO) {
        Utente utente = utenteRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Utente con ID " + id + " non trovato"));

        utente.setNomeUtente(utenteDTO.nome());
        utente.setCognomeUtente(utenteDTO.cognome());
        utente.setUsername(utenteDTO.username());
        utente.setEmail(utenteDTO.email());
        utente.setPassword(passwordEncoder.encode(utenteDTO.password()));
        utente.setAvatar(utenteDTO.avatar());

        Utente updatedUser = utenteRepository.save(utente);

        return new UtenteResponseDTO(
                updatedUser.getId(),
                updatedUser.getNomeUtente(),
                updatedUser.getCognomeUtente(),
                updatedUser.getUsername(),
                updatedUser.getEmail(),
                updatedUser.getAvatar()
        );
    }

    public void deleteUser(Long id) {
        if (!utenteRepository.existsById(id)) {
            throw new UserNotFoundException("Utente con ID " + id + " non trovato");
        }
        utenteRepository.deleteById(id);
    }
}
