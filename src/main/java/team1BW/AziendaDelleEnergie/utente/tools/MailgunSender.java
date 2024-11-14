package team1BW.AziendaDelleEnergie.utente.tools;

import kong.unirest.core.HttpResponse;
import kong.unirest.core.JsonNode;
import kong.unirest.core.Unirest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import team1BW.AziendaDelleEnergie.utente.entities.Utente;

@Component
public class MailgunSender {

    private final String apiKey;
    private final String domain;

    public MailgunSender(@Value("${mailgun.apikey}") String apiKey,
                         @Value("${mailgun.domain}") String domain) {
        this.apiKey = apiKey;
        this.domain = domain;
        System.out.println("API Key: " + this.apiKey);
        System.out.println("Domain: " + this.domain);
    }

    public void sendRegistrationEmail(Utente recipient) {
        HttpResponse<JsonNode> response = Unirest.post("https://api.mailgun.net/v3/" + this.domain + "/messages")
                .basicAuth("api", this.apiKey)
                .queryString("from", "ertamiriam@gmail.com")
                .queryString("to", recipient.getEmail())
                .queryString("subject", "Registrazione completata!")
                .queryString("text", "Benvenuto " + recipient.getNomeUtente() + " sulla nostra piattaforma!")
                .asJson();
        System.out.println(response.getBody());
        System.out.println("Status: " + response.getStatus());
        System.out.println("Response: " + response.getBody());
    }
}
