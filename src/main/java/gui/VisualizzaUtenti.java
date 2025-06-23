package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Finestra grafica per la gestione degli utenti con cui è condiviso un {@link modello.ToDo}.
 *
 * <p>Permette all’utente di:</p>
 * <ul>
 *   <li>Visualizzare l’elenco degli utenti che hanno accesso al ToDo selezionato</li>
 *   <li>Aggiungere nuovi utenti alla condivisione (se autorizzato)</li>
 *   <li>Rimuovere utenti già presenti (con vincoli di autorizzazione)</li>
 * </ul>
 *
 * <p>Questa finestra si interfaccia direttamente con il {@link controller.Controller}
 * per eseguire le operazioni e aggiornare i dati nel modello e nel database.</p>
 *
 * <p>La chiusura della finestra comporta l’azzeramento del campo {@code todoSelezionato}
 * per evitare ambiguità o conflitti nella GUI principale.</p>
 */

public class VisualizzaUtenti {
    /**
     * Il Frame della finestra VisualizzaUtenti.
     */
    public final JFrame frame;
    private JPanel principale;
    private JButton rimuoviButton;
    private JButton aggiungiButton;
    private JList<String> listUtenti;
    private JTextField inputUtenteDaAggiungere;
    private JPanel panelIntestazione;
    private JPanel panelLista;
    private JPanel panelRimuovi;
    private JPanel panelAggiungi;
    private JPanel panelSpazioDx;
    private JPanel panelSpazioSx;
    private JButton chiudiButton;
    private JPanel panelChiudi;

    /**
     * Costruisce e inizializza la finestra di gestione delle condivisioni di un ToDo.
     *
     * <p>Popola l’elenco degli utenti attualmente associati al ToDo selezionato
     * e imposta i listener per l’aggiunta e rimozione degli utenti tramite controller.</p>
     *
     * @param controller istanza del {@link controller.Controller} per eseguire operazioni logiche e gestione dei dati
     */
    public VisualizzaUtenti(Controller controller) {

        frame = new JFrame("VisualizzaUtenti");
        frame.setContentPane(principale);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e){
                frame.setVisible(false);
                frame.dispose();
                controller.todoSelezionato = null;
            }
        });
        frame.pack();
        frame.setVisible(false);

        //colori
        String colore1 = "#F7F9F7";
        String colore2 = "#E8EDE8";
        String colore3 = "#DDE4DD";

        principale.setBackground(Color.decode(colore1));
        panelIntestazione.setBackground(Color.decode(colore1));
        panelRimuovi.setBackground(Color.decode(colore1));
        panelSpazioDx.setBackground(Color.decode(colore1));
        panelSpazioSx.setBackground(Color.decode(colore1));
        panelAggiungi.setBackground(Color.decode(colore2));
        panelChiudi.setBackground(Color.decode(colore3));

        chiudiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                frame.dispose();
                controller.todoSelezionato = null;
            }
        });

        //popolo lista e crea add e remove
        DefaultListModel<String> modelloLista = new DefaultListModel<>();
        listUtenti.setModel(modelloLista);

        for (String utentiCondivisi : controller.todoSelezionato.utenti){
            modelloLista.addElement(utentiCondivisi);
        }


        aggiungiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.aggiungiUtenteCondiviso(inputUtenteDaAggiungere, modelloLista, principale);
                inputUtenteDaAggiungere.setText("");
                listUtenti.clearSelection();
            }
        });

        rimuoviButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.rimuoviUtenteCondiviso(listUtenti, modelloLista, principale);
            }
        });
    }
}
