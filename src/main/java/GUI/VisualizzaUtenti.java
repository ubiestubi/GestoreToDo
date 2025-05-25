package GUI;

import Controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VisualizzaUtenti {
    public JFrame frame;
    private JPanel principale;
    private JButton rimuoviButton;
    private JButton aggiungiButton;
    private JList listUtenti;
    private JTextField inputUtenteDaAggiungere;
    private JPanel panelIntestazione;
    private JPanel panelLista;
    private JPanel panelRimuovi;
    private JPanel panelAggiungi;
    private JPanel panelSpazioDx;
    private JPanel panelSpazioSx;
    private JButton chiudiButton;
    private JPanel panelChiudi;

    public VisualizzaUtenti(Controller controller) {

        frame = new JFrame("VisualizzaUtenti");
        frame.setContentPane(principale);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
            }
        });

        //popolo lista e crea add e remove
        DefaultListModel<String> modelloLista = new DefaultListModel<>();
        listUtenti.setModel(modelloLista);

        modelloLista.addElement("Ubi");
        modelloLista.addElement("Mario");
        modelloLista.addElement("Luigi");


        aggiungiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.aggiungiUtenteCondiviso(inputUtenteDaAggiungere, modelloLista);
            }
        });

        rimuoviButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.rimuoviUtenteCondiviso(listUtenti, modelloLista);
            }
        });
    }
}
