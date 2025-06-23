package dao;

import modello.ToDo;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Interfaccia DAO per l'aggiornamento di una lista di {@link modello.ToDo}
 * nel database al momento del logout dell'utente.
 *
 * <p>Serve a garantire che tutte le modifiche effettuate ai ToDo (ad es. stato o posizione)
 * durante la sessione siano correttamente salvate prima della chiusura dell'applicazione.</p>
 */
public interface AggiornaTodosAlLogoutDAO {
    /**
     * Aggiorna nel database tutti i {@link modello.ToDo} specificati,
     * aggiornando tipicamente lo stato di completamento e la posizione all'interno della bacheca.
     *
     * @param todos lista di ToDo da aggiornare
     * @throws SQLException se si verifica un errore durante l'operazione di aggiornamento in batch
     */
    void aggiornaTuttiTodosAlLogout(ArrayList<ToDo> todos) throws SQLException;
}
