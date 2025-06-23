package dao;

import java.sql.SQLException;

/**
 * Interfaccia DAO per la verifica delle credenziali utente
 * all'interno del database.
 *
 * <p>Le implementazioni di questa interfaccia devono controllare se esiste
 * un utente registrato con la coppia di username e password fornita.</p>
 */
public interface LoginDAO {
    /**
     * Verifica la validità delle credenziali fornite controllandole nel database.
     *
     * @param username nome utente da autenticare
     * @param password password associata all'username
     * @return {@code true} se l'autenticazione ha successo, altrimenti {@code false}
     * @throws SQLException se si verifica un errore durante la query al database
     */
    boolean login(String username, String password) throws SQLException;
}
