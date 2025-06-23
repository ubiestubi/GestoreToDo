package implementazioneMySQLDAO;

import dao.RegistrazioneUtenteDAO;
import database.ConnessioneDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Implementazione dell'interfaccia {@link dao.RegistrazioneUtenteDAO}
 * per la registrazione di un nuovo utente nel database MySQL.
 *
 * <p>Controlla l'unicità dello username prima di eseguire l'inserimento nella tabella {@code Utenti}.
 * Utilizza {@link database.ConnessioneDatabase} per stabilire la connessione al database.</p>
 */
public class RegistrazioneUtenteImplementazioneMySQLDAO implements RegistrazioneUtenteDAO {
    private Connection connection;

    /**
     * Costruttore che inizializza la connessione al database
     * tramite il singleton {@link database.ConnessioneDatabase}.
     * In caso di errore, stampa un messaggio sulla console.
     */
    public RegistrazioneUtenteImplementazioneMySQLDAO(){
        try {
            connection = ConnessioneDatabase.getInstance().connection;
        } catch (SQLException e) {
            System.err.println("Impossibile avviare la connessione al DB:\n" + e.getMessage());
        }
    }
    /**
     * Registra un nuovo utente nel database, verificando prima che lo username non sia già presente.
     * Se lo username esiste, viene lanciata una {@link SQLException}.
     *
     * @param username nome utente da registrare
     * @param password password associata all'utente
     * @throws SQLException se lo username è già in uso o se si verifica un errore SQL
     */
    @Override
    public void registrazioneUtente(String username, String password) throws SQLException {
        //controlla non esista già un username uguale
        String sql = "SELECT username FROM Utenti WHERE username = ?";
        ResultSet rs;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            rs = ps.executeQuery();
            if (rs.next()) {
                throw new SQLException("Username già in uso, sceglierne un altro");
            }
            String sql2 = "INSERT INTO Utenti (username,password) VALUES ( ? , ? )";
            try (PreparedStatement ps2 = connection.prepareStatement(sql2)) {
                ps2.setString(1, username);
                ps2.setString(2, password);
                ps2.executeUpdate();
            }
        }
        finally {
            connection.close();
        }
    }
}
