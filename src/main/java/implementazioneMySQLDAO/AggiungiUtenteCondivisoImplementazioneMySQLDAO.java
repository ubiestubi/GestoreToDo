package implementazioneMySQLDAO;

import dao.AggiungiUtenteCondivisoDAO;
import database.ConnessioneDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Implementazione dell'interfaccia {@link dao.AggiungiUtenteCondivisoDAO}
 * per la gestione della condivisione di un {@link modello.ToDo} con un altro utente nel database MySQL.
 *
 * <p>Oltre ad aggiornare la tabella di relazione {@code Todo_has_Utenti},
 * si occupa anche di creare automaticamente una nuova bacheca per l'utente destinatario,
 * se non ne possiede una con lo stesso titolo.</p>
 */
public class AggiungiUtenteCondivisoImplementazioneMySQLDAO implements AggiungiUtenteCondivisoDAO {
    private Connection connection;

    /**
     * Costruttore che inizializza la connessione al database
     * tramite il singleton {@link database.ConnessioneDatabase}.
     * In caso di errore durante la connessione, stampa un messaggio sulla console.
     */
    public AggiungiUtenteCondivisoImplementazioneMySQLDAO(){
        try {
            connection = ConnessioneDatabase.getInstance().connection;
        } catch (SQLException e) {
            System.err.println("Impossibile avviare la connessione al DB:\n" + e.getMessage());
        }
    }
    /**
     * Aggiunge la condivisione di un ToDo per un utente specifico.
     * Inserisce la relazione nella tabella {@code Todo_has_Utenti}.
     * Se l'utente non possiede già la bacheca associata, ne viene creata automaticamente una.
     *
     * @param idTodo  identificatore del ToDo da condividere
     * @param utente  username dell'utente con cui condividere
     * @param bacheca titolo della bacheca di destinazione
     * @throws SQLException se si verificano errori durante l'accesso al database
     */
    @Override
    public void aggiungiCondivisione(Integer idTodo, String utente, String bacheca) throws SQLException {
        String sql = "INSERT INTO Todo_has_Utenti (Todo_id, Utenti_username) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idTodo);
            ps.setString(2, utente);
            ps.executeUpdate();
        }
        System.out.println("Condivisione aggiunta con successo.");
        //controlla se la bacheca del todos è esistente tra le bacheche dell'utente, se non c'è la si crea
        String sql2 = "SELECT titolo FROM Bacheche WHERE titolo = ? AND Utenti_username = ?";
        ResultSet rs;
        try (PreparedStatement ps2 = connection.prepareStatement(sql2)) {
            ps2.setString(1, bacheca);
            ps2.setString(2, utente);
            rs = ps2.executeQuery();
            if (!rs.next()){
                String sql3 = "INSERT INTO Bacheche (titolo, descrizione, Utenti_username) VALUES (?, ?, ?)";
                try (PreparedStatement ps3 = connection.prepareStatement(sql3)) {
                    ps3.setString(1, bacheca);
                    String descrizione = "Bacheca creata con la condivisione di un todo";
                    ps3.setString(2, descrizione);
                    ps3.setString(3, utente);
                    ps3.executeUpdate();
                }
            }
        }
        finally {
            connection.close();
        }
    }
}
