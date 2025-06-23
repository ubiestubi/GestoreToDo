package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Interfaccia DAO per il recupero delle condivisioni dei {@link modello.ToDo}
 * associati a un determinato utente nel database.
 *
 * <p>Permette di caricare tutte le condivisioni attive, raggruppando per ID di ToDo
 * gli utenti con cui ciascun ToDo è stato condiviso.</p>
 */
public interface CaricaCondivisioniTodoDAO {
    /**
     * Popola una mappa che associa gli ID dei {@link modello.ToDo} a una lista
     * di username degli utenti con cui ciascun ToDo è stato condiviso.
     *
     * @param utente username dell'utente che effettua la richiesta
     * @param mappaUtentiCondivisi mappa da riempire, con ID del ToDo come chiave
     *                              e lista di username condivisi come valore
     * @throws SQLException se si verifica un errore durante la lettura dal database
     */
    void caricaCondivisioniTodoDalDB(String utente, HashMap<Integer, ArrayList<String>> mappaUtentiCondivisi) throws SQLException;
}
