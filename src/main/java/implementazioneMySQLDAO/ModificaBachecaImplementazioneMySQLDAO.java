package implementazioneMySQLDAO;

import dao.ModificaBachecaDAO;
import database.ConnessioneDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Implementazione dell'interfaccia {@link dao.ModificaBachecaDAO}
 * per l'aggiornamento della descrizione di una bacheca nel database MySQL.
 *
 * <p>Questa classe utilizza {@link database.ConnessioneDatabase} per stabilire la connessione
 * e applica una query parametrizzata sulla tabella {@code Bacheche}.</p>
 */
public class ModificaBachecaImplementazioneMySQLDAO implements ModificaBachecaDAO {
    private Connection connection;

    /**
     * Costruttore che inizializza la connessione al database
     * utilizzando il singleton {@link database.ConnessioneDatabase}.
     * In caso di errore, stampa un messaggio sulla console.
     */
    public ModificaBachecaImplementazioneMySQLDAO(){
        try {
            connection = ConnessioneDatabase.getInstance().connection;
        } catch (SQLException e) {
            System.err.println("Impossibile avviare la connessione al DB:\n" + e.getMessage());
        }
    }
    /**
     * Aggiorna la descrizione di una bacheca per uno specifico utente nel database.
     *
     * @param utente      username del proprietario della bacheca
     * @param bacheca     titolo della bacheca da aggiornare
     * @param descrizione nuova descrizione testuale da salvare
     * @throws SQLException se si verifica un errore durante l'esecuzione della query
     */
    @Override
    public void aggiornaBachecaAlDB(String utente, String bacheca, String descrizione) throws SQLException{
        String sql = "UPDATE Bacheche SET descrizione = ? WHERE titolo = ? AND Utenti_username = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, descrizione);
            ps.setString(2, bacheca);
            ps.setString(3, utente);
            ps.executeUpdate();
        }
        finally {
            connection.close();
        }
    }

}
