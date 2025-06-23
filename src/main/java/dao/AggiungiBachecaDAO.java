package dao;

/**
 * Interfaccia DAO per l'inserimento di una nuova bacheca nel database.
 *
 * <p>Le implementazioni di questa interfaccia si occupano della creazione di una nuova bacheca
 * e della sua associazione a un utente tramite l'inserimento nella tabella {@code Bacheche}.</p>
 */
public interface AggiungiBachecaDAO {
    /**
     * Inserisce una nuova bacheca nel database, associandola all'utente specificato.
     *
     * @param titoloBacheca       titolo della bacheca
     * @param descrizioneBacheca  descrizione testuale della bacheca
     * @param utente              username del proprietario della bacheca
     */
    void aggiungiBachecaAlDB(String titoloBacheca, String descrizioneBacheca, String utente);
}
