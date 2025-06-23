/**
 * Questo package contiene tutte le implementazioni delle interfacce DAO
 * per l'accesso e la manipolazione dei dati relativi a {@link modello.ToDo}, {@link modello.Bacheca}
 * e {@link modello.Utente} tramite database MySQL.
 *
 * <p>Ogni classe implementa specifici comportamenti definiti nelle interfacce del package {@code dao},
 * utilizzando JDBC e il singleton {@link database.ConnessioneDatabase} per gestire la connessione.</p>
 *
 * <p>Le responsabilità principali includono:
 * <ul>
 *   <li>Gestione della registrazione e autenticazione utente</li>
 *   <li>Caricamento, aggiornamento e rimozione di ToDo e bacheche</li>
 *   <li>Gestione delle condivisioni tra utenti</li>
 * </ul>
 *
 * Tutte le implementazioni chiudono le risorse JDBC in modo sicuro tramite try-with-resources.
 */
package implementazioneMySQLDAO;