package dao;

import java.sql.SQLException;

/**
 * Interfaccia DAO per l'aggiornamento delle informazioni di una bacheca
 * nel database.
 *
 * <p>Le implementazioni devono permettere la modifica della descrizione
 * associata a una bacheca di un determinato utente.</p>
 */
public interface ModificaBachecaDAO {
    /**
     * Aggiorna la descrizione di una bacheca per l'utente specificato nel database.
     *
     * @param utente      username del proprietario della bacheca
     * @param bacheca     titolo della bacheca da aggiornare
     * @param descrizione nuova descrizione da salvare nel database
     * @throws SQLException se si verifica un errore durante l'operazione di aggiornamento
     */
    void aggiornaBachecaAlDB(String utente, String bacheca, String descrizione) throws SQLException;

}
