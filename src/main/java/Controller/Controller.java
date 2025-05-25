package Controller;

import Modello.Bacheca;
import Modello.ToDo;
import Modello.Utente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Controller {
    public Utente utente;
    public ToDo todoSelezionato;


    //per spostare le righe nelle tabelle in home
    public void spostaRiga(JTable tabella, DefaultTableModel modello, int direzione) {
        int rigaSelezionata = tabella.getSelectedRow();
        if (rigaSelezionata == -1)
            return; // nessuna riga selezionata

        int nuovaPosizione = rigaSelezionata + direzione;
        if (nuovaPosizione < 0 || nuovaPosizione >= modello.getRowCount())
            return; // inizio o fine tabella

        // scambia le righe
        Object[] datiRiga = new Object[modello.getColumnCount()];
        for (int i = 0; i < modello.getColumnCount(); i++) {
            datiRiga[i] = modello.getValueAt(rigaSelezionata, i);
        }

        modello.removeRow(rigaSelezionata);
        modello.insertRow(nuovaPosizione, datiRiga);

        // riseleziona la posizione
        tabella.setRowSelectionInterval(nuovaPosizione, nuovaPosizione);
    }
    //per cambiare stato al todo in tabella
    public void cambiaStato(JTable tabella, DefaultTableModel modello, String bacheca ){

        int rigaSelezionata = tabella.getSelectedRow();
        ToDo todo = trovaTodoDaTabella(tabella,bacheca);
        String valoreAttuale = (String) modello.getValueAt(rigaSelezionata, 0);
        switch (valoreAttuale){
            case "Completato":
                modello.setValueAt("Non completato", rigaSelezionata, 0);
                todo.nonCompletato();
                break;
            case "Non completato":
                modello.setValueAt("Completato", rigaSelezionata, 0);
                todo.completato();
                break;
            default:
                break;
        }
    }

    //per rimuovere un utente
    public void rimuoviUtenteCondiviso(JList lista, DefaultListModel modello) {
        int indiceSelezionato = lista.getSelectedIndex();
        if (indiceSelezionato != -1) { // Verifica che qualcosa sia selezionato
            modello.remove(indiceSelezionato);
        }
    }
    //per aggiungere un utente
    public void aggiungiUtenteCondiviso(JTextField input, DefaultListModel modello) {
        String nome = input.getText().trim();
        if (!nome.isEmpty()) {
            modello.addElement(nome);
            input.setText("");
        }
    }
    public ToDo trovaTodoDaTabella(JTable tabella, String bacheca) {
        int rigaSelezionata = tabella.getSelectedRow();
        if (rigaSelezionata != -1) {
            switch (bacheca) {
                case "Lavoro":
                    for (Bacheca b : utente.bacheche) {
                        if (b.getTitolo().equals("Lavoro")) {
                            for (ToDo t : b.todos) {
                                if (t.posizione == rigaSelezionata + 1) {
                                    return t;
                                }
                            }

                        }
                    }
                    break;
                case "Tempo Libero":
                    for (Bacheca b : utente.bacheche) {
                        if (b.getTitolo().equals("Tempo Libero")) {
                            for (ToDo t : b.todos) {
                                if (t.posizione == rigaSelezionata + 1) {
                                    return t;

                                }
                            }

                        }
                    }
                    break;
                case "Università":
                    for (Bacheca b : utente.bacheche) {
                        if (b.getTitolo().equals("Università")) {
                            for (ToDo t : b.todos) {
                                if ((int) t.posizione == (int) (rigaSelezionata + 1)) {
                                    return t;
                                }
                            }

                        }
                    }
                    break;
            }

        }
        return null;
    }


}


