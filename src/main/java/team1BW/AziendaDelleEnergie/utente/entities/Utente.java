package team1BW.AziendaDelleEnergie.utente.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import team1BW.AziendaDelleEnergie.utente.enums.Ruolo;

import java.util.Collection;
import java.util.List;

import static team1BW.AziendaDelleEnergie.utente.enums.Ruolo.USER;


@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "utenti")
@JsonIgnoreProperties({"password", "role", "accountNonLocked", "credentialsNonExpired",
        "accountNonExpired", "authorities", "enabled"})
public class Utente implements UserDetails {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private Long id;
    private String nomeUtente;
    private String cognomeUtente;
    private String username;
    private String email;
    private String password;
    private String avatar;
    @Enumerated(EnumType.STRING)
    private Ruolo ruolo;

    public Utente(String nomeUtente, String cognomeUtente, String username,
                  String email, String password, String avatar) {
        this.nomeUtente = nomeUtente;
        this.cognomeUtente = cognomeUtente;
        this.username = username;
        this.email = email;
        this.password = password;
        this.avatar = avatar;
        this.ruolo = USER;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.ruolo.name()));
    }

}
