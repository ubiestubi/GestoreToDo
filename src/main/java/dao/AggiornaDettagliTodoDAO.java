package dao;

import modello.ToDo;

import java.sql.SQLException;

/**
 * Interfaccia DAO per l'aggiornamento dei dettagli di un {@link modello.ToDo}
 * nel database.
 *
 * <p>Le implementazioni di questa interfaccia devono occuparsi della persistenza
 * delle modifiche apportate a un ToDo, incluse informazioni come titolo, descrizione,
 * scadenza, stato, immagine, URL, posizione e bacheca associata.</p>
 */
public interface AggiornaDettagliTodoDAO {
    /**
     * Aggiorna nel database tutti i dettagli del {@link modello.ToDo} specificato.
     *
     * @param todo oggetto {@code ToDo} da aggiornare
     * @throws SQLException se si verifica un errore durante l'operazione di aggiornamento
     */
    void aggiornaDettagliTodoAlDB(ToDo todo) throws SQLException;
}
