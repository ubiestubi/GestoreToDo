package implementazioneMySQLDAO;

import dao.RimuoviUtenteCondivisoDAO;
import database.ConnessioneDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Implementazione dell'interfaccia {@link dao.RimuoviUtenteCondivisoDAO}
 * che consente di rimuovere una condivisione di un {@link modello.ToDo}
 * da parte di un utente nel database MySQL.
 *
 * <p>Elimina la riga corrispondente dalla tabella {@code Todo_has_Utenti}
 * e chiude la connessione al termine dell'operazione.</p>
 */
public class RimuoviUtenteCondivisoImplementazioneMySQLDAO implements RimuoviUtenteCondivisoDAO {
    private Connection connection;

    /**
     * Costruttore che inizializza la connessione al database
     * utilizzando il singleton {@link database.ConnessioneDatabase}.
     * In caso di errore, stampa un messaggio sulla console.
     */
    public RimuoviUtenteCondivisoImplementazioneMySQLDAO(){
        try {
            connection = ConnessioneDatabase.getInstance().connection;
        } catch (SQLException e) {
            System.err.println("Impossibile avviare la connessione al DB:\n" + e.getMessage());
        }
    }
    /**
     * Rimuove una condivisione dal database associata al ToDo identificato
     * e all'utente specificato.
     *
     * @param idTodo ID del {@link modello.ToDo} da dissociare
     * @param utente Username dell'utente da rimuovere dalla condivisione
     * @throws SQLException se si verifica un errore durante l'eliminazione
     */
    @Override
    public void rimuoviCondivisione(Integer idTodo, String utente) throws SQLException {
        String sql = "DELETE FROM Todo_has_Utenti WHERE Todo_id = ? AND Utenti_username = ?";
        int righeEliminate;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idTodo);
            ps.setString(2, utente);
            righeEliminate = ps.executeUpdate();
            if (righeEliminate > 0) {
                System.out.println("Rimozione avvenuta con successo");
            } else {
                System.out.println("Nessuna riga eliminata");
            }
        }
        finally {
            connection.close();
        }
    }
}
