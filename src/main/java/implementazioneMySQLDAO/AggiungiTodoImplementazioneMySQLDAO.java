package implementazioneMySQLDAO;

import dao.AggiungiTodoDAO;
import database.ConnessioneDatabase;
import modello.ToDo;

import java.sql.*;

/**
 * Implementazione dell'interfaccia {@link dao.AggiungiTodoDAO}
 * per l'inserimento di un nuovo {@link modello.ToDo} nel database MySQL.
 *
 * <p>Si occupa di inserire nel database nella tabella {@code Todo} un nuovo todo con i suoi relativi dettagli,
 * assegnando automaticamente un ID generato dal database: tale ID sarà poi comunicato per poterlo assegnare al todo nel model</p>
 */
public class AggiungiTodoImplementazioneMySQLDAO implements AggiungiTodoDAO {
    private Connection connection;

    /**
     * Costruisce una nuova istanza del DAO e inizializza la connessione
     * al database tramite il singleton {@link database.ConnessioneDatabase}.
     * In caso di errore durante la connessione, stampa un messaggio di errore sulla console.
     */
    public AggiungiTodoImplementazioneMySQLDAO(){
        try {
            connection = ConnessioneDatabase.getInstance().connection;
        } catch (SQLException e) {
            System.err.println("Impossibile avviare la connessione al DB:\n" + e.getMessage());
        }
    }
    /**
     * Inserisce un nuovo {@link modello.ToDo} nel database.
     * I campi includono titolo, scadenza, URL, descrizione, titolo della bacheca, utente originale,
     * stato, posizione e immagine. L'ID generato dal database viene assegnato all'oggetto {@code todo}.
     *
     * @param todo oggetto {@link modello.ToDo} da inserire nel database
     * @throws SQLException se si verifica un errore durante l'inserimento
     */
    @Override
    public void aggiungiTodoAlDB(ToDo todo) throws SQLException {

        String statement =
        "INSERT INTO Todo (titolo, scadenza, url, descrizione, Bacheche_titolo, Bacheche_Utenti_username, stato, posizione, immagine) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        ResultSet rs;
        try (PreparedStatement aggiungiTodoPS = connection.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS)) {
            aggiungiTodoPS.setString(1, todo.getTitolo());
            aggiungiTodoPS.setTimestamp(2, Timestamp.valueOf(todo.getScadenza().atStartOfDay()));
            aggiungiTodoPS.setString(3, todo.getUrl());
            aggiungiTodoPS.setString(4, todo.getDescrizione());
            aggiungiTodoPS.setString(5, todo.getBacheca());
            aggiungiTodoPS.setString(6, todo.getUtenteOriginale());
            boolean statoTodo = todo.getStato().equals("Completato");
            aggiungiTodoPS.setBoolean(7, statoTodo);
            aggiungiTodoPS.setInt(8, todo.getPosizione());
            // Se immagine è in byte[], altrimenti passa null
            if (todo.getImmagine() != null) {
                aggiungiTodoPS.setBytes(9, todo.getImmagine());
            } else {
                aggiungiTodoPS.setNull(9, Types.BLOB);
            }
            aggiungiTodoPS.executeUpdate();
            rs = aggiungiTodoPS.getGeneratedKeys();
            if (rs.next()) {
                int idGenerato = rs.getInt(1);
                todo.setId(idGenerato); // aggiorna l'id nel model
            }
        }
        finally {
            connection.close();
        }
    }
}
