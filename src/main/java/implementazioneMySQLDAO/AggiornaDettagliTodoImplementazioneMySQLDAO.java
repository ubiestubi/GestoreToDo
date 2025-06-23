package implementazioneMySQLDAO;

import dao.AggiornaDettagliTodoDAO;
import database.ConnessioneDatabase;
import modello.ToDo;

import java.sql.*;

/**
 * Implementazione concreta dell'interfaccia {@link AggiornaDettagliTodoDAO} per database MySQL.
 * Si occupa dell'aggiornamento dei dettagli di un {@link modello.ToDo} nel database quando viene chiusa la scheda Dettagli con un "Salva e chiudi".
 */
public class AggiornaDettagliTodoImplementazioneMySQLDAO implements AggiornaDettagliTodoDAO {
    private Connection connection;

    /**
     * Costruisce una nuova istanza e inizializza la connessione al database
     * utilizzando il singleton {@link database.ConnessioneDatabase}.
     * In caso di errore, stampa un messaggio sulla console.
     */
    public AggiornaDettagliTodoImplementazioneMySQLDAO(){
        try {
            connection = ConnessioneDatabase.getInstance().connection;
        } catch (SQLException e) {
            System.err.println("Impossibile avviare la connessione al DB:\n" + e.getMessage());
        }
    }
    /**
     * Aggiorna nel database i dettagli del todo specificato.
     * I campi aggiornati includono titolo, scadenza, URL, descrizione,
     * titolo della bacheca, immagine, stato e posizione.
     *
     * @param todo l'oggetto {@link modello.ToDo} da aggiornare nel database
     * @throws SQLException se si verifica un errore durante l'esecuzione dell'update SQL
     */
    @Override
    public void aggiornaDettagliTodoAlDB(ToDo todo) throws SQLException {
        String sql = """
        UPDATE Todo SET
            titolo = ?,
            scadenza = ?,
            url = ?,
            descrizione = ?,
            Bacheche_titolo = ?,
            immagine = ?,
            stato = ?,
            posizione = ?
        WHERE id = ?
    """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, todo.getTitolo());
            ps.setTimestamp(2, Timestamp.valueOf(todo.getScadenza().atStartOfDay()));
            ps.setString(3, todo.getUrl());
            ps.setString(4, todo.getDescrizione());
            ps.setString(5, todo.getBacheca());

            // Se immagine è in byte[], altrimenti passa null
            if (todo.getImmagine() != null) {
                ps.setBytes(6, todo.getImmagine());
            } else {
                ps.setNull(6, Types.BLOB);
            }

            ps.setBoolean(7, todo.getStato().equals("Completato"));
            ps.setInt(8, todo.getPosizione());
            ps.setInt(9, todo.getId());

            ps.executeUpdate();
        }
        finally {
            connection.close();
        }
        System.out.println("ToDo aggiornato con successo.");


    }
}
