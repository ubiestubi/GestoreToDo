package modello;

import java.util.*;

/**
 * Rappresenta un utente dell'applicazione.
 * Ogni utente ha un username univoco e una lista di bacheche personali.
 */
public class Utente {
    private final String username;
    /** Lista delle bacheche associate a questo utente. */
    public final List<Bacheca> bacheche;

    /**
     * Costruisce un nuovo utente con l'username specificato
     * e una lista vuota di bacheche.
     *
     * @param username l'username dell'utente
     */
    public Utente(String username) {
        this.username = username;
        this.bacheche = new ArrayList<>();
    }

    /**
     * Restituisce lo username dell'utente.
     *
     * @return lo username
     */
    public String getUsername() {
        return username;
    }


    /**
     * Crea una nuova bacheca per l'utente, aggiungendola alla sua lista di bacheche.
     * Il titolo viene convertito in un oggetto {@link TitoloBacheca}.
     * Se il titolo non corrisponde a un valore valido, la bacheca non viene creata.
     *
     * @param titolo il titolo della bacheca da creare (uno tra "Università", "Lavoro" e "Tempo Libero")
     * @param descrizione descrizione associata alla bacheca
     */
    public void creaBacheca(String titolo, String descrizione) {
        TitoloBacheca titoloBacheca = TitoloBacheca.convertiDaString(titolo);
        if (titoloBacheca == null) {
            System.out.println("Titolo non valido. Scegliere tra: Università / Lavoro / Tempo Libero");
            return;
        }
        Bacheca bacheca = new Bacheca(titoloBacheca, descrizione, this);
        bacheche.add(bacheca);
    }

    /**
     * Rimuove una bacheca esistente dalla lista delle bacheche dell'utente.
     *
     * @param bacheca la bacheca da eliminare
     */
    public void eliminaBacheca(Bacheca bacheca) {
        bacheche.remove(bacheca);
    }

}