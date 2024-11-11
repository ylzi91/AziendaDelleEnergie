package team1BW.AziendaDelleEnergie.utente.exceptions;

public class  AuthenticationFailedException extends RuntimeException {
    public  AuthenticationFailedException(String message) {
        super(message);
    }
}
