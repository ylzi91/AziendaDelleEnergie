package team1BW.AziendaDelleEnergie.indirizzi.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(Long id) {
        super("Il record con l'id " + id + " non è stato trovato");
    }
    public NotFoundException(String msg) {
        super(msg);
    }
}
