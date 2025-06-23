package implementazioneMySQLDAO;

import dao.CaricaListaUtentiDAO;
import database.ConnessioneDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

/**
 * Implementazione dell'interfaccia {@link dao.CaricaListaUtentiDAO}
 * per il recupero dell'elenco di tutti gli username dal database MySQL.
 *
 * <p>La classe si connette al database tramite {@link database.ConnessioneDatabase}
 * e popola una collezione fornita con gli username trovati nella tabella {@code Utenti}, ossia la totalità degli utenti iscritti nel database.</p>
 */
public class CaricaListaUtentiImplementazioneMySQLDAO implements CaricaListaUtentiDAO {
    private Connection connection;

    /**
     * Costruttore che inizializza la connessione al database
     * tramite il singleton {@link database.ConnessioneDatabase}.
     * In caso di errore, stampa un messaggio sulla console.
     */
    public CaricaListaUtentiImplementazioneMySQLDAO() {
        try {
            connection = ConnessioneDatabase.getInstance().connection;
        } catch (SQLException e) {
            System.err.println("Impossibile avviare la connessione al DB:\n" + e.getMessage());
        }
    }
    /**
     * Recupera dal database tutti gli username presenti nella tabella {@code Utenti}
     * e li aggiunge alla collezione fornita.
     *
     * @param listaUtenti collezione {@link Set} in cui inserire gli username trovati
     * @throws SQLException se si verifica un errore durante l'accesso al database
     */
    @Override
    public void caricaListaUtenti(Set<String> listaUtenti) throws SQLException {
        String sql = "SELECT username FROM Utenti";
        ResultSet rs;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            rs = ps.executeQuery();
            while (rs.next()) {
                listaUtenti.add(rs.getString("username"));
            }
        }
        finally {
            connection.close();
        }
    }
}
