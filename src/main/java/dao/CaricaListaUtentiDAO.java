package dao;

import java.sql.SQLException;
import java.util.Set;

/**
 * Interfaccia DAO per il recupero della lista di tutti gli username
 * presenti nella tabella {@code Utenti} del database.
 *
 * <p>Le implementazioni di questa interfaccia devono caricare l’elenco completo
 * degli utenti registrati e aggiungerli alla collezione fornita.</p>
 */
public interface CaricaListaUtentiDAO {
    /**
     * Recupera dal database tutti gli username presenti
     * e li inserisce nella collezione {@code listaUtenti}.
     *
     * @param listaUtenti collezione {@link Set} da riempire con gli username degli utenti
     * @throws SQLException se si verifica un errore durante la lettura dal database
     */
    void caricaListaUtenti(Set<String> listaUtenti) throws SQLException;
}
