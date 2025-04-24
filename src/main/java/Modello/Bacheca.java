package Modello;

import java.time.LocalDate;
import java.util.ArrayList;

public class Bacheca {
    private TitoloBacheca titolo;
    private String descrizione;
    public final ArrayList<ToDo> todos;
    public final Utente utente;

    public Bacheca(TitoloBacheca titolo, String descrizione, Utente utente) {
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.todos = new ArrayList<>();
        this.utente = utente;
    }


    public void setTitolo(String titolo) {
        TitoloBacheca titoloBacheca = TitoloBacheca.convertiDaString(titolo);  //controlla che la stringa sia un titoloBacheca valido
        if (titoloBacheca == null) {
            System.out.println("Titolo non valido. Scegliere tra: Università / Lavoro / Tempo Libero");
        }
        else  {
            this.titolo = titoloBacheca;
        }
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getTitolo() {
        return titolo.mostraTitoloVero();
    }

    public String getDescrizione() {
        return descrizione;
    }


    public ToDo creaToDo(String titolo, LocalDate scadenza) {
        return new ToDo(titolo, scadenza, this, this.utente);
    }

    public ToDo creaToDoCondiviso(ToDo todo) {
        return new ToDo(todo, this);
    }


    public void refreshPosizioni() { //sistema i valori delle posizioni dei todo nella bacheca: da usare quando si elimina o si sposta un todo dalla bacheca
        for (int i = 0; i < this.todos.size(); i++) {
            ToDo todo = todos.get(i);
            todo.setPosizione(i + 1);
        }
    }

}