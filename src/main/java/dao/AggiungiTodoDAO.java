package dao;

import modello.ToDo;

import java.sql.SQLException;

/**
 * Interfaccia DAO per l'inserimento di un nuovo {@link modello.ToDo}
 * nel database.
 *
 * <p>Le implementazioni di questa interfaccia si occupano della persistenza
 * di un'attività, salvandone i dettagli come titolo, scadenza, descrizione,
 * URL, immagine, stato, posizione e associazioni con bacheca e utente.</p>
 */
public interface AggiungiTodoDAO {

    /**
     * Inserisce un nuovo {@link modello.ToDo} nel database.
     *
     * @param todo oggetto {@code ToDo} da salvare
     * @throws SQLException se si verifica un errore durante l'inserimento
     */
    void aggiungiTodoAlDB(ToDo todo) throws SQLException;
}
