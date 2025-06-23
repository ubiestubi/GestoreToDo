package implementazioneMySQLDAO;

import dao.CaricaCondivisioniTodoDAO;
import database.ConnessioneDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Implementazione dell'interfaccia {@link dao.CaricaCondivisioniTodoDAO}
 * per la lettura dal database MySQL delle condivisioni associate ai {@link modello.ToDo}.
 *
 * <p>Recupera, per un determinato utente, la mappatura dei ToDo condivisi e i relativi utenti destinatari.</p>
 */
public class CaricaCondivisioniTodoImplementazioneMySQLDAO implements CaricaCondivisioniTodoDAO {
    private Connection connection;

    /**
     * Costruttore che inizializza la connessione al database
     * tramite il singleton {@link database.ConnessioneDatabase}.
     * In caso di errore, stampa un messaggio esplicativo sulla console.
     */
    public CaricaCondivisioniTodoImplementazioneMySQLDAO(){
        try {
            connection = ConnessioneDatabase.getInstance().connection;
        } catch (SQLException e) {
            System.err.println("Impossibile avviare la connessione al DB:\n" + e.getMessage());
        }
    }
    /**
     * Carica dal database tutti i {@link modello.ToDo} condivisi relativi all'utente specificato
     * e popola la mappa fornita con le associazioni tra ID del ToDo e utenti con cui è condiviso.
     *
     * @param utente username dell'utente attualmente connesso
     * @param mappaUtentiCondivisi mappa da riempire con ID del ToDo come chiave
     *                              e lista di username ai quali il todo è condiviso come valore
     * @throws SQLException se si verifica un errore durante l'accesso al database
     */
    @Override
    public void caricaCondivisioniTodoDalDB(String utente , HashMap<Integer, ArrayList<String>> mappaUtentiCondivisi) throws SQLException {
        String sql = """
                SELECT Todo_id, Utenti_username AS utenteCondiviso
                FROM Todo LEFT OUTER JOIN Todo_has_Utenti ThU on Todo.id = ThU.Todo_id
                WHERE Todo_id IN
                (SELECT Todo.id
                FROM Todo LEFT OUTER JOIN Todo_has_Utenti ThU on Todo.id = ThU.Todo_id
                WHERE (Bacheche_Utenti_username = ? AND (Utenti_username = ? OR Utenti_username IS NULL))
                   OR (Bacheche_Utenti_username !=  ? AND Utenti_username = ?))""";

        ResultSet rs;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, utente);
            ps.setString(2, utente);
            ps.setString(3, utente);
            ps.setString(4, utente);
            rs = ps.executeQuery();
            while (rs.next()) {
                int todoId = rs.getInt("Todo_id");
                String username = rs.getString("utenteCondiviso");
                mappaUtentiCondivisi.computeIfAbsent(todoId, k -> new ArrayList<>()).add(username);
            }
        }
        finally {
            connection.close();
        }
    }
}
