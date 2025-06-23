package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Singleton responsabile della gestione della connessione al database MySQL.
 * La classe fornisce un'unica istanza condivisa e configurata per accedere
 * al database "GestoreTodo" tramite JDBC.
 */
public class ConnessioneDatabase {

    private static ConnessioneDatabase instance;
    /**
     * La conessione JDBC.
     */
    public Connection connection = null;

    /**
     * Costruttore privato che inizializza la connessione al database.
     * Carica il driver MySQL, definisce URL, utente e password.
     * In caso di errore, stampa il messaggio sulla console.
     */
    private ConnessioneDatabase() {
        try {
            String driver = "com.mysql.cj.jdbc.Driver";
            Class.forName(driver);
            String nome = "rootGestoreTodo";
            String password = "pass";
            String url = "jdbc:mysql://localhost:3306/GestoreTodo?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
            connection = DriverManager.getConnection(url, nome, password);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Errore durante la connessione al database: " + e.getMessage());
        }
    }

    /**
     * Restituisce l'istanza singleton di {@code ConnessioneDatabase}.
     * Se non esiste o la connessione è chiusa, crea una nuova istanza.
     *
     * @return istanza della classe {@code ConnessioneDatabase}
     * @throws SQLException se la connessione non può essere aperta
     */
    public static ConnessioneDatabase getInstance() throws SQLException {
        if (instance == null || instance.connection.isClosed()) {
            instance = new ConnessioneDatabase();
        }
        return instance;
    }
}
