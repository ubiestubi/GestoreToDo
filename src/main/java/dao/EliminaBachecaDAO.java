package dao;


import java.sql.SQLException;

/**
 * Interfaccia DAO per la rimozione di una bacheca dal database.
 *
 * <p>Le implementazioni devono garantire la cancellazione della bacheca
 * specificata solo se associata all’utente indicato.</p>
 */
public interface EliminaBachecaDAO {
    /**
     * Elimina dal database la bacheca identificata dal titolo e dall'utente proprietario.
     *
     * @param utente   username del proprietario della bacheca
     * @param bacheca  titolo della bacheca da eliminare
     * @throws SQLException se si verifica un errore durante l’operazione di eliminazione
     */
    void eliminaBachecaDalDB(String utente, String bacheca) throws SQLException;
}
