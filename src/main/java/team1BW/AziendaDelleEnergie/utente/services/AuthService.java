package team1BW.AziendaDelleEnergie.utente.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import team1BW.AziendaDelleEnergie.utente.entities.Utente;
import team1BW.AziendaDelleEnergie.utente.exceptions.AuthenticationFailedException;
import team1BW.AziendaDelleEnergie.utente.payloads.LoginRequestDTO;
import team1BW.AziendaDelleEnergie.utente.payloads.LoginResponseDTO;
import team1BW.AziendaDelleEnergie.utente.repositories.UtenteRepository;

import java.util.Date;
import java.util.Optional;

@Service
public class AuthService {

    private final UtenteRepository utenteRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    private String jwtSecret;

    public AuthService(UtenteRepository utenteRepository, PasswordEncoder passwordEncoder) {
        this.utenteRepository = utenteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponseDTO authenticateUser(LoginRequestDTO loginRequestDTO) {
        Utente utente = utenteRepository.findByUsername(loginRequestDTO.username())
                .orElseThrow(() -> new AuthenticationFailedException("Username o password non validi"));

        if (!passwordEncoder.matches(loginRequestDTO.password(), utente.getPassword())) {
            throw new AuthenticationFailedException("Username o password non validi");
        }

        String token = generateToken(utente.getUsername());

        return new LoginResponseDTO(
                token,
                utente.getId(),
                utente.getUsername(),
                utente.getNomeUtente(),
                utente.getCognomeUtente(),
                utente.getRuolo().name()
        );
    }

    private String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public Optional<String> getUsernameFromToken(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
            return Optional.ofNullable(claims.getSubject());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public boolean validateToken(String token) {
        return getUsernameFromToken(token).isPresent();
    }
}
