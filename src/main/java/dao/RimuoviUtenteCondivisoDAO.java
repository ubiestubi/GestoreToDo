package dao;

import java.sql.SQLException;

/**
 * Interfaccia DAO per la rimozione di una condivisione associata
 * a un {@link modello.ToDo} nel database.
 *
 * <p>Le implementazioni devono eliminare il collegamento tra un Todo
 * e l'utente con cui è stata condivisa.</p>
 */
public interface RimuoviUtenteCondivisoDAO {
    /**
     * Rimuove la condivisione di un {@link modello.ToDo} con uno specifico utente.
     *
     * @param idTodo identificatore del ToDo dal quale rimuovere la condivisione
     * @param utente username dell’utente da dissociare
     * @throws SQLException se si verifica un errore durante l’operazione
     */
    void rimuoviCondivisione(Integer idTodo, String utente) throws SQLException;
}
