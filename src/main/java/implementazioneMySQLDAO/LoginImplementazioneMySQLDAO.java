package implementazioneMySQLDAO;

import dao.LoginDAO;
import database.ConnessioneDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Implementazione dell'interfaccia {@link dao.LoginDAO} per la verifica
 * delle credenziali utente con i dati presenti nel database MySQL.
 *
 * <p>Utilizza {@link database.ConnessioneDatabase} per stabilire la connessione
 * e autentica l'utente tramite interrogazione SQL parametrizzata sulla tabella {@code Utenti}.</p>
 */
public class LoginImplementazioneMySQLDAO implements LoginDAO {
    private Connection connection;

    /**
     * Costruttore che inizializza la connessione al database
     * tramite il singleton {@link database.ConnessioneDatabase}.
     * In caso di errore, stampa un messaggio sulla console.
     */
    public LoginImplementazioneMySQLDAO(){
        try {
            connection = ConnessioneDatabase.getInstance().connection;
        } catch (SQLException e) {
            System.err.println("Impossibile avviare la connessione al DB:\n" + e.getMessage());
        }
    }
    /**
     * Verifica se esiste nel database un utente con le credenziali specificate.
     *
     * @param username nome utente da autenticare
     * @param password password associata
     * @return {@code true} se l'autenticazione ha successo, altrimenti {@code false}
     * @throws SQLException se si verifica un errore durante l'accesso al database
     */
    @Override
    public boolean login(String username, String password) throws SQLException {
        String sql = "SELECT username, password FROM Utenti WHERE username = ? AND password = ?" ;
        ResultSet rs;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            rs = ps.executeQuery();
            return rs.next();
        }
        finally {
        connection.close();
        }
    }
}
