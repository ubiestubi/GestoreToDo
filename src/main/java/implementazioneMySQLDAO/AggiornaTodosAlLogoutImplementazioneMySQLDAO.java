package implementazioneMySQLDAO;

import dao.AggiornaTodosAlLogoutDAO;
import database.ConnessioneDatabase;
import modello.ToDo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Implementazione concreta dell'interfaccia {@link dao.AggiornaTodosAlLogoutDAO}
 * per la gestione dell'aggiornamento in batch dei {@link modello.ToDo} al momento del logout.
 *
 * <p>Effettua l'update dello stato e della posizione di ciascun ToDo caricato precedentemente nel model sul database MySQL.</p>
 */
public class AggiornaTodosAlLogoutImplementazioneMySQLDAO implements AggiornaTodosAlLogoutDAO {
    private Connection connection;

    /**
     * Costruisce una nuova istanza del DAO e inizializza la connessione al database
     * tramite il singleton {@link database.ConnessioneDatabase}.
     * In caso di errore durante la connessione, stampa il messaggio sulla console.
     */
    public AggiornaTodosAlLogoutImplementazioneMySQLDAO(){
        try {
            connection = ConnessioneDatabase.getInstance().connection;
        } catch (SQLException e) {
            System.err.println("Impossibile avviare la connessione al DB:\n" + e.getMessage());
        }
    }
    /**
     * Aggiorna nel database lo stato e la posizione di ogni {@link modello.ToDo}
     * fornito nella lista, usando un batch SQL.
     *
     * @param todos lista di oggetti {@link modello.ToDo} da aggiornare
     * @throws SQLException se si verifica un errore durante l'operazione sul database
     */
    @Override
    public void aggiornaTuttiTodosAlLogout(ArrayList<ToDo> todos) throws SQLException {
        String sql = "UPDATE Todo SET stato = ?, posizione = ? WHERE id = ?";
        int[] risultati;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            for (ToDo todo : todos) {
                ps.setBoolean(1, todo.getStato().equals("Completato"));
                ps.setInt(2, todo.getPosizione());
                ps.setInt(3, todo.getId());
                ps.addBatch();
            }

            risultati = ps.executeBatch();
        }
        finally {
            connection.close();
        }
        System.out.println("Aggiornati " + risultati.length + " ToDo nel database.");
    }
}
