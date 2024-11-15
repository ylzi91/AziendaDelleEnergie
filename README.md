
# Applicazione Gestione Fatture

Questa applicazione consente di gestire le fatture dei clienti e i dati dei clienti stessi. È strutturata con un backend realizzato in **Spring Boot** e un frontend sviluppato in **React** con **Vite**. 

## Struttura del Progetto

- **Backend (Spring Boot)**: gestisce la logica del server, i dati dei clienti e delle fatture, le API REST e la sicurezza.
- **Frontend (React + Vite)**: interfaccia utente per visualizzare, aggiungere, modificare ed eliminare clienti e fatture.

---

## Requisiti

- **Java** (versione 17 o superiore)
- **Maven**
- **Node.js** (versione 16 o superiore)
- **MySQL** o un altro database relazionale supportato

## Configurazione del Backend

1. **Clonare il repository**:
    ```bash
    git clone <repository-url>
    cd <repository-directory>/backend
    ```

2. **Configurazione Database**:
   Configurare il database MySQL (o un altro RDBMS a scelta) e aggiornare `application.properties` in `src/main/resources` con le credenziali del database:

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3001/nome_del_database
   spring.datasource.username=tuo_username
   spring.datasource.password=tua_password
3.	Avviare il backend:
bash
Copia codice
mvn spring-boot:run
4.	API Endpoints:
o	GET /api/clienti: Recupera la lista di clienti.
o	POST /api/clienti: Crea un nuovo cliente.
o	GET /api/fatture: Recupera la lista delle fatture.
o	POST /api/fatture: Crea una nuova fattura.
o	PUT /api/clienti/{id}: Modifica un cliente esistente.
o	DELETE /api/clienti/{id}: Elimina un cliente esistente.
 
## Configurazione del Frontend
1.	Installare le dipendenze:
bash
Copia codice
cd ../frontend
npm install
2.	Configurare le variabili d'ambiente: Creare un file .env nella cartella frontend per configurare l'URL di base del backend:
dotenv
Copia codice
VITE_API_BASE_URL=http://localhost:8080/api
3.	Avviare il frontend:
bash
Copia codice
npm run dev
4.	Navigare all'indirizzo: http://localhost:3001 per utilizzare l'applicazione.
 
Funzionalità Principali
Clienti
•	Creazione di nuovi clienti: Form per l'inserimento dei dati di contatto e altre informazioni.
•	Modifica clienti: Possibilità di aggiornare le informazioni di un cliente esistente.
•	Eliminazione clienti: Funzionalità per eliminare un cliente.
Fatture
•	Creazione di nuove fatture: Possibilità di aggiungere una fattura associata a un cliente.
•	Visualizzazione dettagli fattura: Pagina dedicata per ogni fattura con i dettagli.
Sicurezza
•	Autenticazione: Utilizzo di JWT (JSON Web Token) per autenticare e autorizzare gli utenti.
•	Ruoli e autorizzazioni: Distinzione tra ruoli di amministratore e utente standard con accesso limitato alle funzionalità.
 
Strumenti e Tecnologie Utilizzati
•	Spring Boot: Per la gestione della logica lato server e le API REST.
•	Spring Security: Per la gestione dell'autenticazione e autorizzazione tramite JWT.
•	MySQL: Database per la persistenza dei dati.
•	React + Vite: Per il frontend con rendering rapido e reattivo.
•	Axios: Per le chiamate API dal frontend.
•	React Router: Per la gestione della navigazione lato frontend.
 
Esecuzione dei Test
•	Backend: Avviare i test usando Maven:
bash
Copia codice
mvn test
•	Frontend: Avviare i test unitari con Jest:
bash
Copia codice
npm run test

## Struttura del progetto

``` /.mvn
 /src
  ├── main
       ├── java/team1BW/AziendaDelleEnergie     # Pagine principali dell'app
         ├── resources                          # Configurazione principale dell'app
 ├── pom.xml                                    # dipendenze dell'app
 ```
 
Contribuzione
Per contribuire, fare un fork del progetto, creare un nuovo branch, effettuare le modifiche e fare una pull request.
Contatti
Per maggiori informazioni, contattare :
[ ylzi91@gmail.com - 
dianadorojuc3@gmail.com -
norfoantonio@gmail.com -
campionluca3@gmail.com ]
 
Licenza
Distribuito sotto licenza MIT.
go
Copia codice
