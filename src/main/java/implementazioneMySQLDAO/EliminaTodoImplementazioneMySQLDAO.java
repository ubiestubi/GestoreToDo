package implementazioneMySQLDAO;

import dao.EliminaTodoDAO;
import database.ConnessioneDatabase;
import modello.ToDo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Implementazione dell'interfaccia {@link dao.EliminaTodoDAO}
 * per la cancellazione di un {@link modello.ToDo} dal database MySQL.
 *
 * <p>Questa classe si occupa di eliminare un ToDo sia dalla tabella {@code Todo_has_Utenti},
 * che memorizza le condivisioni, sia dalla tabella principale {@code Todo}.</p>
 */
public class EliminaTodoImplementazioneMySQLDAO implements EliminaTodoDAO {
    private Connection connection;

    /**
     * Costruttore che inizializza la connessione al database
     * utilizzando il singleton {@link database.ConnessioneDatabase}.
     * In caso di errore, viene stampato un messaggio sulla console.
     */
    public EliminaTodoImplementazioneMySQLDAO(){
        try {
            connection = ConnessioneDatabase.getInstance().connection;
        } catch (SQLException e) {
            System.err.println("Impossibile avviare la connessione al DB:\n" + e.getMessage());
        }
    }
    /**
     * Elimina un {@link modello.ToDo} dal database in base al suo ID.
     * Prima elimina le eventuali relazioni nella tabella {@code Todo_has_Utenti},
     * e successivamente rimuove il ToDo dalla tabella {@code Todo}.
     *
     * @param todo l'oggetto {@link modello.ToDo} da eliminare
     * @throws SQLException se si verifica un errore durante l'eliminazione
     */
    @Override
    public void eliminaTodoDalDB(ToDo todo) throws SQLException {
        String sql2 = "DELETE FROM Todo_has_Utenti WHERE Todo_id = " + todo.getId() +" ";
        try (PreparedStatement ps2 = connection.prepareStatement(sql2)) {
            ps2.executeUpdate();
            String sql = "DELETE FROM Todo WHERE id = " + todo.getId() + " ";
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.executeUpdate();
            }
        }
        finally {
            connection.close();
        }
    }
}
