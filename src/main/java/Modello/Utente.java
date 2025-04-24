package Modello;

import java.util.*;

public class Utente {
    private final String username;
    private String password;
    public final ArrayList<Bacheca> bacheche;
    private Boolean stato; // se è loggato o meno

    public Utente(String username, String password) {
        this.username = username;
        this.password = password;
        this.bacheche = new ArrayList<>();
        this.stato = false;
    }

    public String getUsername() {
        return username;
    }
    public boolean getStato() {
        return this.stato;
    }

    public void login(String username, String password) {
        this.stato = this.username.equals(username) && this.password.equals(password);
        if(this.stato) {
            System.out.println("Login effettuato correttamente");
        }
        else {
            System.out.println("Login non effettuato correttamente");
        }
    }
    public void logout() {
        this.stato = false;
    }


    public Bacheca creaBacheca(String titolo, String descrizione) {
        TitoloBacheca titoloBacheca = TitoloBacheca.convertiDaString(titolo);
        if (titoloBacheca == null) {
            System.out.println("Titolo non valido. Scegliere tra: Università / Lavoro / Tempo Libero");
            return null;
        }
        Bacheca bacheca = new Bacheca(titoloBacheca, descrizione, this);
        bacheche.add(bacheca);
        return bacheca;
    }

    public void eliminaBacheca(Bacheca bacheca) {
        bacheche.remove(bacheca);
    }


}