package team1BW.AziendaDelleEnergie.utente.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import team1BW.AziendaDelleEnergie.utente.enums.Ruolo;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "utenti")
public class Utente {
    @Id
    @GeneratedValue
    private Long id;
    private String nomeUtente;
    private String cognomeUtente;
    private String username;
    private String password;
    private String email;
    private String avatar;
    @Enumerated(EnumType.STRING)
    private Ruolo ruolo;


}
