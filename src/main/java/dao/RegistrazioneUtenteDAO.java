package dao;

import java.sql.SQLException;

/**
 * Interfaccia DAO per la registrazione di un nuovo utente
 * all'interno del database.
 *
 * <p>Le implementazioni devono verificare l’unicità dello username
 * e completare l’inserimento solo se non già esistente.</p>
 */
public interface RegistrazioneUtenteDAO {
    /**
     * Registra un nuovo utente nel database con username e password specificati.
     * Può generare un'eccezione se lo username è già presente o in caso di errore SQL.
     *
     * @param username nome utente da registrare
     * @param password password associata all’utente
     * @throws SQLException se si verifica un errore durante l’operazione o se lo username è già in uso
     */
    void registrazioneUtente(String username, String password) throws SQLException;
}
