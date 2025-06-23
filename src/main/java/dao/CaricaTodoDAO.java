package dao;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Interfaccia DAO per il caricamento dei {@link modello.ToDo} dal database,
 * visibili a un determinato utente.
 *
 * <p>Recupera tutte le informazioni necessarie per popolare l'interfaccia utente
 * con le attività personali e condivise, suddivise in liste parallele.</p>
 */
public interface CaricaTodoDAO {
    /**
     * Carica dal database tutti i {@link modello.ToDo} accessibili all'utente specificato,
     * inclusi quelli condivisi, e popola i dati in liste parallele fornite come argomenti.
     *
     * @param utente           username dell'utente attualmente connesso
     * @param utenteOriginale lista che conterrà l'username proprietario di ogni ToDo
     * @param id               lista da riempire con gli ID dei ToDo
     * @param titolo           lista dei titoli dei ToDo
     * @param scadenza         lista delle date di scadenza
     * @param url              lista degli URL associati
     * @param descrizione      lista delle descrizioni testuali
     * @param bachecaTitolo    lista dei titoli delle bacheche di appartenenza
     * @param stato            lista che indica lo stato di completamento (completato o non completato)
     * @param posizione        lista delle posizioni nella bacheca
     * @param immagine         lista delle immagini associate (come array di byte)
     * @throws SQLException    se si verifica un errore durante il recupero dei dati dal database
     */
    void caricaTodoDalDB(String utente, ArrayList<String> utenteOriginale, ArrayList<Integer> id, ArrayList<String> titolo, ArrayList<LocalDate> scadenza, ArrayList<String> url, ArrayList<String> descrizione, ArrayList<String> bachecaTitolo, ArrayList<Boolean> stato, ArrayList<Integer> posizione, ArrayList<byte[]> immagine) throws SQLException;

}
