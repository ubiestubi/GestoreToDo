package dao;

import modello.ToDo;

import java.sql.SQLException;

/**
 * Interfaccia DAO per la rimozione di un {@link modello.ToDo} dal database.
 *
 * <p>Consente di eliminare definitivamente un ToDo sia per l'utente
 * sia per eventuali altri utenti con il quale è stato condiviso.</p>
 */
public interface EliminaTodoDAO {
    /**
     * Elimina un {@link modello.ToDo} dal database, includendo anche eventuali
     * record collegati alla condivisione.
     *
     * @param todo oggetto {@code ToDo} da eliminare, identificato tramite il suo ID
     * @throws SQLException se si verifica un errore durante l'eliminazione
     */
    void eliminaTodoDalDB(ToDo todo) throws SQLException;
}
