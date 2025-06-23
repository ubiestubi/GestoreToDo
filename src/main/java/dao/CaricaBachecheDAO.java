package dao;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Interfaccia DAO per il recupero delle bacheche associate a un determinato utente
 * dal database.
 *
 * <p>Le implementazioni di questa interfaccia devono occuparsi di caricare i titoli
 * e le descrizioni delle bacheche create dall'utente specificato,
 * popolando due liste fornite come parametro.</p>
 */
public interface CaricaBachecheDAO {
    /**
     * Carica dal database i titoli e le descrizioni delle bacheche appartenenti
     * all'utente indicato, aggiungendoli alle liste fornite.
     *
     * @param utente       username dell'utente proprietario delle bacheche
     * @param titoli       lista da riempire con i titoli delle bacheche trovate
     * @param descrizioni  lista da riempire con le descrizioni delle bacheche trovate
     * @throws SQLException se si verifica un errore durante la lettura dal database
     */
    void caricaBachecheDalDB(String utente, ArrayList<String> titoli, ArrayList<String> descrizioni) throws SQLException;
}
