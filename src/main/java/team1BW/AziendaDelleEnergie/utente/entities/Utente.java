package team1BW.AziendaDelleEnergie.utente.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import team1BW.AziendaDelleEnergie.ruoliUtente.entities.Ruolo;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


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

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "ruoli_utente",
            joinColumns = @JoinColumn(name = "utente_id"),
            inverseJoinColumns = @JoinColumn(name = "ruoli_id")
    )
    private Set<Ruolo> ruoli = new HashSet<>();

    public Utente(String nomeUtente, String cognomeUtente, String username,
                  String email, String password, String avatar) {
        this.nomeUtente = nomeUtente;
        this.cognomeUtente = cognomeUtente;
        this.username = username;
        this.email = email;
        this.password = password;
        this.avatar = avatar;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return ruoli.stream().map(ruolo -> new SimpleGrantedAuthority(ruolo.getNome())).collect(Collectors.toList());
    }

}
