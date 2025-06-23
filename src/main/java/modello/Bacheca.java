package modello;

import java.util.ArrayList;
import java.util.List;

/**
 * Rappresenta una bacheca tematica dell'applicazione.
 * Ogni bacheca è associata a un utente, ha un titolo e una descrizione,
 * e contiene una lista ordinata di attività {@link ToDo}.
 */
public class Bacheca {
    private TitoloBacheca titolo;
    private String descrizione;
     /** Lista di attività (ToDo) associate alla bacheca. */
    public final List<ToDo> todos;
    /** Utente proprietario della bacheca. */
    public final Utente utente; //lo uso nella classe Utente per legare l'utente alla bacheca

    /**
     * Costruisce una nuova bacheca con il titolo, la descrizione e l'utente specificati.
     *
     * @param titolo       titolo della bacheca
     * @param descrizione  descrizione della bacheca
     * @param utente       utente proprietario della bacheca
     */
    public Bacheca(TitoloBacheca titolo, String descrizione, Utente utente) {
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.todos = new ArrayList<>();
        this.utente = utente;
    }


    /**
     * Imposta un nuovo titolo per la bacheca, dopo aver verificato che sia valido.
     *
     * @param titolo nuovo titolo (come stringa)
     */
    public void setTitolo(String titolo) {
        TitoloBacheca titoloBacheca = TitoloBacheca.convertiDaString(titolo);  //controlla che la stringa sia un titoloBacheca valido
        if (titoloBacheca == null) {
            System.out.println("Titolo non valido. Scegliere tra: Università / Lavoro / Tempo Libero");
        }
        else  {
            this.titolo = titoloBacheca;
        }
    }

    /**
     * Imposta una nuova descrizione per la bacheca.
     *
     * @param descrizione nuova descrizione testuale
     */
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    /**
     * Restituisce il titolo della bacheca come stringa leggibile.
     *
     * @return titolo visibile della bacheca
     */
    public String getTitolo() {
        return titolo.mostraTitoloVero();
    }

    /**
     * Restituisce la descrizione corrente della bacheca.
     *
     * @return descrizione testuale
     */
    public String getDescrizione() {
        return descrizione;
    }


    /**
     * Aggiorna i numeri di posizione di tutti i ToDo contenuti,
     * mantenendo l'ordine corretto dopo eventuali spostamenti o rimozioni.
     */
    public void refreshPosizioni() { //sistema i valori delle posizioni dei todoo nella bacheca: da usare quando si elimina o si sposta un todo dalla bacheca
        for (int i = 0; i < this.todos.size(); i++) {
            ToDo todo = todos.get(i);
            todo.setPosizione(i + 1);
        }
    }

}