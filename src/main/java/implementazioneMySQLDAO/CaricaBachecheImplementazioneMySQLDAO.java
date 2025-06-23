package implementazioneMySQLDAO;

import dao.CaricaBachecheDAO;
import database.ConnessioneDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Implementazione dell'interfaccia {@link dao.CaricaBachecheDAO}
 * per il recupero delle bacheche associate a un utente dal database MySQL.
 *
 * <p>Recupera i titoli e le descrizioni delle bacheche appartenenti a uno specifico utente,
 * popolando due liste fornite come parametri.</p>
 */
public class CaricaBachecheImplementazioneMySQLDAO implements CaricaBachecheDAO {
    private Connection connection;

    /**
     * Costruttore che inizializza la connessione al database
     * utilizzando il singleton {@link database.ConnessioneDatabase}.
     * In caso di errore, stampa il messaggio sulla console.
     */
    public CaricaBachecheImplementazioneMySQLDAO() {
        try {
            connection = ConnessioneDatabase.getInstance().connection;
        } catch (SQLException e) {
            System.err.println("Impossibile avviare la connessione al DB:\n" + e.getMessage());
        }
    }
    /**
     * Carica dal database tutti i titoli e le descrizioni delle bacheche
     * appartenenti all'utente specificato e li inserisce nelle liste fornite.
     *
     * @param utente       username dell'utente proprietario delle bacheche
     * @param titoli       lista da riempire con i titoli delle bacheche
     * @param descrizioni  lista da riempire con le descrizioni delle bacheche
     * @throws SQLException se si verifica un errore durante l'accesso al database
     */
    @Override
    public void caricaBachecheDalDB(String utente, ArrayList<String> titoli, ArrayList<String> descrizioni) throws SQLException {
        String sql = "SELECT titolo, descrizione FROM Bacheche WHERE Utenti_username = ?" ;

        ResultSet rs;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, utente);
            rs = ps.executeQuery();
            while (rs.next()) {
                titoli.add(rs.getString("titolo"));
                descrizioni.add(rs.getString("descrizione"));
            }
        }
        finally {
            connection.close();
        }
    }
}
