package implementazioneMySQLDAO;

import dao.EliminaBachecaDAO;
import database.ConnessioneDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Implementazione dell'interfaccia {@link dao.EliminaBachecaDAO}
 * per la rimozione di una bacheca dal database MySQL.
 *
 * <p>Esegue un'operazione {@code DELETE} sulla tabella {@code Bacheche}
 * per eliminare una bacheca specifica associata a un determinato utente.</p>
 */
public class EliminaBachecaImplementazioneMySQLDAO implements EliminaBachecaDAO {
    private Connection connection;

    /**
     * Costruttore che inizializza la connessione al database
     * tramite il singleton {@link database.ConnessioneDatabase}.
     * In caso di errore nella connessione, stampa un messaggio sulla console.
     */
    public EliminaBachecaImplementazioneMySQLDAO(){
        try {
            connection = ConnessioneDatabase.getInstance().connection;
        } catch (SQLException e) {
            System.err.println("Impossibile avviare la connessione al DB:\n" + e.getMessage());
        }
    }
    /**
     * Elimina una bacheca dal database identificata da titolo e utente proprietario.
     *
     * @param utente   username del proprietario della bacheca
     * @param bacheca  titolo della bacheca da eliminare
     * @throws SQLException se si verifica un errore durante l'operazione di cancellazione
     */
    @Override
    public void eliminaBachecaDalDB(String utente, String bacheca) throws SQLException{
        String sql = "DELETE FROM Bacheche WHERE titolo = ? AND Utenti_username = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, bacheca);
            ps.setString(2, utente);
            ps.executeUpdate();
        }
        finally {
            connection.close();
        }
    }

}
