package team1BW.AziendaDelleEnergie.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team1BW.AziendaDelleEnergie.repositories.IndirizzoRepository;

@Service
public class IndirizzoService {
    @Autowired
    private IndirizzoRepository indirizzoRepository;

    //@Autowired
    //private PasswordEncoder bcrypt;

}
