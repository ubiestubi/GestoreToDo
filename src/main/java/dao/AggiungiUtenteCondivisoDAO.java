package dao;

import java.sql.SQLException;

/**
 * Interfaccia DAO per la gestione della condivisione di un {@link modello.ToDo}
 * con un altro utente nel database.
 *
 * <p>L'implementazione di questo metodo dovrebbe occuparsi sia della condivisione del todo, sia dell'eventuale creazione
 * automatica di una bacheca per l'utente destinatario, se non esistente.</p>
 */
public interface AggiungiUtenteCondivisoDAO {
    /**
     * Registra una condivisione del ToDo con un altro utente, eventualmente creando
     * anche la bacheca associata per il destinatario.
     *
     * @param idTodo  identificatore del {@link modello.ToDo} da condividere
     * @param utente  username dell'utente con cui condividere l'attività
     * @param bacheca titolo della bacheca di destinazione
     * @throws SQLException se si verifica un errore durante l'accesso al database
     */
    void aggiungiCondivisione(Integer idTodo, String utente, String bacheca) throws SQLException;

}
