package modello;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Rappresenta un'attività ("ToDo") associata a una {@link Bacheca}.
 * Ogni ToDo ha un titolo, una scadenza, uno stato (completato o non completato),
 * un utente creatore, un insieme di utenti con cui è eventualmente condiviso,
 * e può contenere informazioni aggiuntive come descrizione, immagine, URL.
 */
public class ToDo {
    private String titolo;
    private LocalDate scadenza;
    private String url;
    private String descrizione;
    private byte[] immagine;
    /**
     * Posizione del todo all'interno della bacheca
     */
    public int posizione;
    private StatoToDo stato;  //true=completato, false=non completato
    private Bacheca bacheca;
    /**
     * L'utente creatore del todo, serve per distinguerlo da eventuali condivisioni
     */
    private final String utenteOriginale;
    /**
     * Lista di utenti con il quale il todo è condiviso, è usato un set per evitare duplicati
     */
    public final Set<String> utenti; //collezione senza duplicati
    /**
     * Identificatore univoco del todo
     */
    private int id; //per il db


    /**
     * Crea un nuovo ToDo e lo aggiunge automaticamente alla bacheca specificata.
     *
     * @param titolo           Titolo dell'attività.
     * @param scadenza         Data di scadenza dell'attività.
     * @param bacheca          Bacheca alla quale è associata l'attività.
     * @param utenteOriginale  Username del creatore originale del ToDo.
     */
    public ToDo(String titolo, LocalDate scadenza, Bacheca bacheca, String utenteOriginale) {
        this.titolo = titolo;
        this.scadenza = scadenza;
        this.bacheca = bacheca;
        this.bacheca.todos.add(this);
        this.posizione = bacheca.todos.size();   // mette automaticamente in posizione finale della bacheca
        this.stato = StatoToDo.NON_COMPLETATO;
        this.url = "URL";
        this.descrizione = "descrizione";
        this.immagine = null;
        this.utenteOriginale = utenteOriginale;
        this.utenti = new HashSet<>();
        this.utenti.add(utenteOriginale);
    }

    /**
     * Imposta il titolo dell'attività.
     *
     * @param titolo il titolo
     */
    public void setTitolo(String titolo) {
        if (this.utenti.size() > 1) {
            System.out.println("Non si può cambiare titolo a ToDo condivisi");
            return;
        }
        this.titolo = titolo;
    }

    /**
     * Imposta la data di scadenza dell'attività.
     *
     * @param scadenza la scadenza
     */
    public void setScadenza(LocalDate scadenza) {
        this.scadenza = scadenza;
    }

    /**
     * Imposta un URL di riferimento per l'attività.
     *
     * @param url l'URL
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Imposta la descrizione testuale dell'attività.
     *
     * @param descrizione la descrizione
     */
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    /**
     * Imposta l'immagine associata all'attività come array di byte.
     *
     * @param immagine l'immagine
     */
    public void setImmagine(byte[] immagine) {
        this.immagine = immagine;
    }

    /**
     * Imposta la posizione del ToDo all'interno della bacheca.
     *
     * @param posizione la posizione
     */
    public void setPosizione(int posizione) {
        this.posizione = posizione;
    }

    /**
     * Imposta l'identificatore univoco dell'attività (es. per un database).
     *
     * @param id l'id
     */
    public void setId(int id) {this.id = id;}


    /**
     * Restituisce il titolo dell'attività.
     *
     * @return il titolo
     */
    public String getTitolo() {
        return titolo;
    }

    /**
     * Restituisce lo stato dell'attività come stringa leggibile.
     *
     * @return lo stato
     */
    public String getStato(){
        String statotemp = "";
        if (this.stato == StatoToDo.NON_COMPLETATO) {
            statotemp = "Non completato";
        } else if (this.stato == StatoToDo.COMPLETATO) {
            statotemp = "Completato";
        }
        return statotemp;
    }

    /**
     * Restituisce il titolo della bacheca associata.
     *
     * @return il titolo della bacheca
     */
    public String getBacheca(){
        return this.bacheca.getTitolo();
    }

    /**
     * Restituisce la data di scadenza.
     *
     * @return la scadenza
     */
    public LocalDate getScadenza() {
        return scadenza;
    }

    /**
     * Restituisce l'URL associato.
     *
     * @return l'URL
     */
    public String getUrl() {
        return url;
    }

    /**
     * Restituisce la descrizione dell'attività.
     *
     * @return la descrizione
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * Restituisce l'immagine come array di byte, poi il controller avrà il compito di renderla ImageIcon per visualizzarla nella gui.
     *
     * @return immagine come byte[]
     */
    public byte[] getImmagine() {
        return immagine;
    }

    /**
     * Restituisce la posizione all'interno della bacheca.
     *
     * @return la posizione
     */
    public int getPosizione() {
        return posizione;
    }

    /**
     * Restituisce lo username dell'utente creatore.
     *
     * @return username
     */
    public String getUtenteOriginale() {
        return utenteOriginale;
    }

    /**
     * Imposta il todo come Completato.
     */
    public void completato() {
        this.stato = StatoToDo.COMPLETATO;
    }

    /**
     * Imposta il todo come Non completato.
     */
    public void nonCompletato() {
        this.stato = StatoToDo.NON_COMPLETATO;
    }

    /**
     * Restituisce l'identificatore univoco.
     *
     * @return l'id
     */
    public int getId() {return id;}

    /**
     * Elimina il todo e fai un refresh della bacheca di appartenenza per sistemare le posizioni di altri todo nella bacheca
     */
    public void elimina() {
        this.bacheca.todos.remove(this);
        bacheca.refreshPosizioni(); //sistema la bacheca vecchia
    }


    /**
     * Sposta todo all'interno della stessa bacheca in una determinata posizione.
     *
     * @param posizione la posizione di destinazione
     */
    public void spostaToDo(int posizione) { //cambia la posizione del todoo rimanendo nella stessa bacheca
        this.bacheca.todos.remove(this);
        bacheca.refreshPosizioni();
        this.bacheca.todos.add(posizione - 1, this); // così la posizione del primo todoo è 1
        bacheca.refreshPosizioni();
    }

    /**
     * Sposta todo in un'altra bacheca.
     *
     * @param bachecaDestinazione la bacheca di destinazione
     */
    public void spostaToDoInNuovaBacheca(Bacheca bachecaDestinazione) {
        this.bacheca.todos.remove(this);
        bacheca.refreshPosizioni(); //sistema la bacheca vecchia
        bachecaDestinazione.todos.add(this);
        this.bacheca = bachecaDestinazione;
        this.posizione = bacheca.todos.size();   // mette automaticamente in posizione finale della bacheca

    }

    /**
     * Condividi todo con un utente.
     *
     * @param utente l'utente con cui condividere il todo
     */
    public void condividi(String utente) {
       utenti.add(utente);

    }

    /**
     * Elimina condivisione del todo con un utente.
     *
     * @param utente l'utente a cui rimuovere la condivisione del todo
     */
    public void eliminaCondivisione(String utente) {
        utenti.remove(utente);
    }
}
