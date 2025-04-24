package Modello;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;

public class ToDo {
    private String titolo;
    private LocalDate scadenza;
    private String url;
    private String descrizione;
    private Object immagine;
    public int posizione;
    private String colore;
    private StatoToDo stato;  //true=completato, false=non completato
    private Bacheca bacheca;
    private final Utente utenteOriginale;
    public final HashSet<Utente> utenti; //collezione senza duplicati


    public ToDo(String titolo, LocalDate scadenza, Bacheca bacheca, Utente utenteOriginale) {
        this.titolo = titolo;
        this.scadenza = scadenza;
        this.bacheca = bacheca;
        this.bacheca.todos.add(this);
        this.posizione = bacheca.todos.size();   // mette automaticamente in posizione finale della bacheca
        this.stato = StatoToDo.NON_COMPLETATO;
        this.url = "URL";
        this.descrizione = "descrizione";
        this.immagine = "immagine";
        this.colore = "colore";
        this.utenteOriginale = utenteOriginale;
        this.utenti = new HashSet<>();
        this.utenti.add(utenteOriginale);
    }

    public ToDo(ToDo todoCondiviso, Bacheca bacheca) { //costruttore usato per creare nuovi todo quando vengono condivisi
        this.titolo = todoCondiviso.titolo;
        this.scadenza = todoCondiviso.scadenza;
        this.bacheca = bacheca;
        this.bacheca.todos.add(this);
        this.posizione = bacheca.todos.size();   // mette automaticamente in posizione finale della bacheca
        this.stato = todoCondiviso.stato;
        this.url = todoCondiviso.descrizione;
        this.descrizione = todoCondiviso.descrizione;
        this.immagine = todoCondiviso.immagine;
        this.colore = todoCondiviso.colore;
        this.utenteOriginale = todoCondiviso.utenteOriginale;
        this.utenti = todoCondiviso.utenti;
        this.utenti.add(bacheca.utente);
    }

    // lista di getter e setter per gli attributi 'facili'
    public void setTitolo(String titolo) {
        if (this.utenti.size() > 1) {
            System.out.println("Non si può cambiare titolo a ToDo condivisi");
            return;
        }
        this.titolo = titolo;
    }

    public void setScadenza(LocalDate scadenza) {
        this.scadenza = scadenza;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
    public void setImmagine(Object immagine) {
        this.immagine = immagine;
    }
    public void setPosizione(int posizione) {
        this.posizione = posizione;
    }
    public void setColore(String colore) {
        this.colore = colore;
    }

    public String getTitolo() {
        return titolo;
    }

    public LocalDate getScadenza() {
        return scadenza;
    }
    public String getUrl() {
        return url;
    }
    public String getDescrizione() {
        return descrizione;
    }
    public Object getImmagine() {
        return immagine;
    }
    public int getPosizione() {
        return posizione;
    }
    public String getColore() {
        return colore;
    }
    public ArrayList<String> getUtenti() {
        ArrayList<String> utentiUsername = new ArrayList<>();
        for (Utente u : utenti) {
            utentiUsername.add(u.getUsername());
        }
        return utentiUsername;
    }

    public void completato() {
        this.stato = StatoToDo.COMPLETATO;
    }

    public void nonCompletato() {
        this.stato = StatoToDo.NON_COMPLETATO;
    }
    // fine getter e setter




    public void elimina() {
        this.bacheca.todos.remove(this);
        bacheca.refreshPosizioni(); //sistema la bacheca vecchia
    }


    public void spostaToDo(int posizione) { //cambia la posizione del todo rimanendo nella stessa bacheca
        this.bacheca.todos.remove(this);
        bacheca.refreshPosizioni();
        this.bacheca.todos.add(posizione - 1, this);
        bacheca.refreshPosizioni();
    }

    public void spostaToDoInNuovaBacheca(Bacheca bachecaDestinazione) {
        this.bacheca.todos.remove(this);
        bacheca.refreshPosizioni(); //sistema la bacheca vecchia
        bachecaDestinazione.todos.add(this);
        this.bacheca = bachecaDestinazione;
        this.posizione = bacheca.todos.size();   // mette automaticamente in posizione finale della bacheca

    }

    public void condividi(Utente utenteDestinatario) {
        //controllo se il creatore del todo è loggato, perchè solo lui può condividere
        if (!this.utenteOriginale.getStato()) {
            System.out.println("Impossibile creare condivisione. Solo il creatore originale del ToDo può creare o eliminare condivisioni");
            return;
        }
        //cerco se il destinatario ha la stessa bacheca, altrimenti la crea
        Bacheca bachecaDestinatario = null;
        boolean bachecaPresenteNelDestinatario = false;
        for (Bacheca b : utenteDestinatario.bacheche) {
            if (b.getTitolo().equals(this.bacheca.getTitolo())) {
                bachecaPresenteNelDestinatario = true;
                bachecaDestinatario = b;
            }
        }
        if (!bachecaPresenteNelDestinatario) {
            bachecaDestinatario = utenteDestinatario.creaBacheca(this.bacheca.getTitolo().toString(), this.bacheca.getDescrizione());
        }
        //aggiungo il todo nella bacheca del destinatario
        bachecaDestinatario.creaToDoCondiviso(this);
        this.utenti.add(utenteDestinatario);
        System.out.println("ToDo condiviso correttamente");

    }

    public void eliminaCondivisione(Utente utenteDestinatario) {
        //controllo se il creatore del todo è loggato, perchè solo lui può condividere
        if (!this.utenteOriginale.getStato()) {
            System.out.println("Impossibile eliminare condivisione. Solo il creatore originale del ToDo può creare o eliminare condivisioni");
            return;
        }
        if (!this.utenti.contains(utenteDestinatario)) {
            System.out.println("Questo ToDo non è condiviso con questo utente");
            return;
        }
        //cerca il todo con lo stesso nome, data di scadenza e utente originale, ed elimina
        for (Bacheca b : utenteDestinatario.bacheche) {
            if (b.getTitolo().equals(this.bacheca.getTitolo())) {
                for (ToDo t : b.todos) {
                    if (t.getTitolo().equals(this.getTitolo())) {
                        t.elimina();
                        b.refreshPosizioni();
                        System.out.println("ToDo condiviso eliminato correttamente");
                    }
                }
            }
        }
    }
}
