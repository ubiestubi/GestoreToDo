package implementazioneMySQLDAO;

import dao.CaricaTodoDAO;
import database.ConnessioneDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Implementazione dell'interfaccia {@link dao.CaricaTodoDAO}
 * per il caricamento dei {@link modello.ToDo} associati a un determinato utente
 * dal database MySQL.
 *
 * <p>Recupera tutti i ToDo visibili a un utente, inclusi quelli condivisi,
 * e popola le liste fornite con i relativi dati.</p>
 */
public class CaricaTodoImplementazioneMySQLDAO implements CaricaTodoDAO {
    private Connection connection;

    /**
     * Costruttore che inizializza la connessione al database
     * utilizzando il singleton {@link database.ConnessioneDatabase}.
     * In caso di errore, stampa un messaggio sulla console.
     */
    public CaricaTodoImplementazioneMySQLDAO(){
        try {
            connection = ConnessioneDatabase.getInstance().connection;
        } catch (SQLException e) {
            System.err.println("Impossibile avviare la connessione al DB:\n" + e.getMessage());
        }
    }
    /**
     * Carica tutti i {@link modello.ToDo} appartenenti (o condivisi con) l'utente specificato,
     * e inserisce ciascun campo nelle rispettive liste fornite come parametro.
     *
     * @param utente            username dell'utente corrente
     * @param utenteOriginale   lista in cui inserire l'utente proprietario originale del ToDo
     * @param id                lista in cui inserire gli ID dei ToDo
     * @param titolo            lista dei titoli
     * @param scadenza          lista delle date di scadenza
     * @param url               lista degli URL
     * @param descrizione       lista delle descrizioni testuali
     * @param bachecaTitolo     lista dei titoli delle bacheche associate
     * @param stato             lista dello stato di completamento (completato o meno)
     * @param posizione         lista delle posizioni nella bacheca
     * @param immagine          lista delle immagini (come array di byte)
     * @throws SQLException     se si verifica un errore durante l'accesso al database
     */
    @Override
    public void caricaTodoDalDB(String utente, ArrayList<String> utenteOriginale, ArrayList<Integer> id, ArrayList<String> titolo, ArrayList<LocalDate> scadenza, ArrayList<String> url, ArrayList<String> descrizione, ArrayList<String> bachecaTitolo, ArrayList<Boolean> stato, ArrayList<Integer> posizione, ArrayList<byte[]> immagine) throws SQLException {
        String sql = """
                SELECT id, titolo, scadenza, url, descrizione, Bacheche_titolo, immagine, stato, posizione, Bacheche_Utenti_username
                FROM Todo LEFT OUTER JOIN Todo_has_Utenti ThU on Todo.id = ThU.Todo_id
                WHERE (Bacheche_Utenti_username =  ? AND (Utenti_username = ? OR Utenti_username IS NULL))
                OR (Bacheche_Utenti_username !=  ? AND Utenti_username = ?) ORDER BY posizione""";

        ResultSet rs;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, utente);
            ps.setString(2, utente);
            ps.setString(3, utente);
            ps.setString(4, utente);
            rs = ps.executeQuery();
            while (rs.next()) {
                utenteOriginale.add(rs.getString("Bacheche_Utenti_username"));
                id.add(rs.getInt("id"));
                titolo.add(rs.getString("titolo"));
                scadenza.add(rs.getTimestamp("scadenza").toLocalDateTime().toLocalDate());
                url.add( rs.getString("url"));
                descrizione.add(rs.getString("descrizione"));
                bachecaTitolo.add(rs.getString("Bacheche_titolo"));
                stato.add(rs.getBoolean("stato"));
                posizione.add(rs.getInt("posizione"));
                immagine.add(rs.getBytes("immagine"));
            }
        }
        finally {
            connection.close();
        }
    }

}
