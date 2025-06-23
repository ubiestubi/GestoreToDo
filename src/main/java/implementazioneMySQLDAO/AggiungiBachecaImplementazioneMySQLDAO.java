package implementazioneMySQLDAO;

import dao.AggiungiBachecaDAO;
import database.ConnessioneDatabase;

import java.sql.*;

/**
 * Implementazione concreta dell'interfaccia {@link dao.AggiungiBachecaDAO}
 * per l'inserimento di nuove bacheche nel database MySQL.
 *
 * <p>Utilizza {@link database.ConnessioneDatabase} per ottenere una connessione JDBC
 * e inserisce i dati nel database nella tabella {@code Bacheche}.</p>
 */
public class AggiungiBachecaImplementazioneMySQLDAO implements AggiungiBachecaDAO {
    private Connection connection;

    /**
     * Costruttore che inizializza la connessione al database tramite il singleton
     * {@link database.ConnessioneDatabase}.
     * In caso di errore durante la connessione, stampa un messaggio di errore sulla console.
     */
    public AggiungiBachecaImplementazioneMySQLDAO(){
        try {
            connection = ConnessioneDatabase.getInstance().connection;
        } catch (SQLException e) {
            System.err.println("Impossibile avviare la connessione al DB:\n" + e.getMessage());
        }
    }
    /**
     * Esegue l'inserimento nel database di una nuova bacheca, associata a un utente.
     *
     * @param titoloBacheca       titolo della bacheca
     * @param descrizioneBacheca  descrizione testuale
     * @param utente              username dell'utente proprietario
     */
    @Override
    public void aggiungiBachecaAlDB(String titoloBacheca, String descrizioneBacheca, String utente) {
        try {
            String statement =
                    "INSERT INTO Bacheche (titolo, descrizione, Utenti_username) VALUES (?, ?, ?)";

            try (PreparedStatement aggiungiBachecaPS = connection.prepareStatement(statement)) {
                aggiungiBachecaPS.setString(1, titoloBacheca);
                aggiungiBachecaPS.setString(2, descrizioneBacheca);
                aggiungiBachecaPS.setString(3, utente);
                aggiungiBachecaPS.executeUpdate();
            }
            finally {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Impossibile aggiungere la bacheca al DB:\n" + e.getMessage());
        }
    }
}
