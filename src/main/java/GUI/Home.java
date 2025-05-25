package GUI;

import Controller.Controller;
import Modello.Bacheca;
import Modello.ToDo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Home {
    public JFrame frame;
    private JPanel principale;
    private JTabbedPane tabPanel;
    private JPanel tabUni;
    private JPanel tabLavoro;
    private JPanel tabTempoLibero;
    private JPanel tabData;
    private JPanel tabRicerca;
    private JPanel tabNewBacheca;
    private JComboBox comboAddBacheca;
    private JTextField textField3;
    private JComboBox comboBox2;
    private JTextField textField4;
    private JButton caricaButton;
    private JPanel tabNewTodo;
    private JButton aggiungiButton;
    private JTextField textField1;
    private JButton cercaButton;
    private JButton buttonUtentiUni;
    private JComboBox comboBoxGiorno;
    private JComboBox comboBoxMese;
    private JComboBox comboBoxAnno;
    private JPanel panelIntestazione;
    private JButton logoutButton;
    private JPanel panelSpazioIntestazione;
    private JPanel panelIntestazioneData;
    private JPanel panelIntestazioneRicerca;
    private JPanel panelTabRicercaCerca;
    private JPanel panelTabRicercaRisultati;
    private JPanel panelTabDataCerca;
    private JPanel panelTabDataRisultati;
    private JPanel panelMainUni;
    private JPanel panelMainLavoro;
    private JPanel panelMainTempoLibero;
    private JScrollPane panelScrollRicerca;
    private JPanel panelMainRicerca;
    private JScrollPane panelScrollData;
    private JPanel panelMainData;
    private JScrollPane panelScrollTempoLibero;
    private JScrollPane panelScrollLavoro;
    private JScrollPane panelScrollUni;
    private JScrollPane panelScrollNewBacheca;
    private JPanel panelMainNewBacheca;
    private JPanel panelIntestazioneNewBacheca;
    private JPanel panelSpazioNewBacheca;
    private JScrollPane panelScrollNewTodo;
    private JPanel panelMainNewTodo;
    private JTextArea textArea1;
    private JPanel panelSpazioNewTodo;
    private JTable tableToDoUni;
    private JButton buttonStatoUni;
    private JButton buttonDettagliUni;
    private JButton buttonSuUni;
    private JButton buttonGiuUni;
    private JPanel panelSuGiuUni;
    private JButton buttonAddBacheca;
    private JTable tableToDoLavoro;
    private JPanel panelSuGiuLavoro;
    private JButton buttonStatoLavoro;
    private JButton buttonDettagliLavoro;
    private JButton buttonUtentiLavoro;
    private JButton buttonSuLavoro;
    private JButton buttonGiuLavoro;
    private JTable tableToDoTempoLibero;
    private JButton buttonStatoTempoLibero;
    private JButton buttonDettagliTempoLibero;
    private JButton buttonUtentiTempoLibero;
    private JPanel panelSuGiuTempoLibero;
    private JButton buttonSuTempoLibero;
    private JButton buttonGiuTempoLibero;
    private JTextField textField2;
    private JPanel panelTodoSposta;


    public Home(JFrame frameChiamante, Controller controller) {

        frame = new JFrame("Home");
        frame.setContentPane(principale);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(false);

        //tab università
        buttonUtentiUni.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VisualizzaUtenti visualizzaUtenti = new VisualizzaUtenti(controller);
                visualizzaUtenti.frame.setVisible(true);
            }
        });
        // modello per tabella
        DefaultTableModel modelloUni = new DefaultTableModel();
        modelloUni.setColumnIdentifiers(new String[]{"Stato", "Titolo", "Data"});
        tableToDoUni.setModel(modelloUni);
        // popola tabella
        for (Bacheca b : controller.utente.bacheche) {
            if (b.getTitolo().equals("Università")){
                for (ToDo t : b.todos) {
                    modelloUni.addRow(new String[]{t.getStato(),t.getTitolo(),t.getScadenza().toString()});
                }

            }
        }
        //spostamento di righe
        buttonSuUni.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.spostaRiga(tableToDoUni, modelloUni, -1);
            }
        });
        buttonGiuUni.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.spostaRiga(tableToDoUni, modelloUni, 1);
            }
        });
        buttonStatoUni.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.cambiaStato(tableToDoUni,modelloUni,"Università");
            }
        });
        //scheda dettagli
        buttonDettagliUni.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tableToDoUni.getSelectedRow()> -1){
                    controller.todoSelezionato= controller.trovaTodoDaTabella(tableToDoUni,"Università");
                    Dettagli dettagli = new Dettagli(controller);
                    dettagli.frame.setVisible(true);
                }
            }
        });


        //tabLavoro
        buttonUtentiLavoro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VisualizzaUtenti visualizzaUtenti = new VisualizzaUtenti(controller);
                visualizzaUtenti.frame.setVisible(true);
            }
        });
        // modello per tabella
        DefaultTableModel modelloLavoro = new DefaultTableModel();
        modelloLavoro.setColumnIdentifiers(new String[]{"Stato", "Titolo", "Data"});
        tableToDoLavoro.setModel(modelloLavoro);
        // popola tabella
        for (Bacheca b : controller.utente.bacheche) {
            if (b.getTitolo().equals("Lavoro")){
                for (ToDo t : b.todos) {
                    modelloLavoro.addRow(new String[]{t.getStato(),t.getTitolo(),t.getScadenza().toString()});
                }

            }
        }
        //spostamento di righe
        buttonSuLavoro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.spostaRiga(tableToDoLavoro, modelloLavoro, -1);
            }
        });
        buttonGiuLavoro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.spostaRiga(tableToDoLavoro, modelloLavoro, 1);
            }
        });
        buttonStatoLavoro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.cambiaStato(tableToDoLavoro,modelloLavoro,"Lavoro");
            }
        });
        //scheda dettagli
        buttonDettagliLavoro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tableToDoLavoro.getSelectedRow()> -1){
                    controller.todoSelezionato= controller.trovaTodoDaTabella(tableToDoLavoro,"Lavoro");
                    Dettagli dettagli = new Dettagli(controller);
                    dettagli.frame.setVisible(true);
                }
            }
        });

        //tabScadenze
        // selettore data
        for (int i = 1; i < 32; i++) {
            comboBoxGiorno.addItem(i);
        }
        for (int i = 1; i < 13; i++) {
            comboBoxMese.addItem(i);
        }
        for (int i = 2025; i < 2030; i++) {
            comboBoxAnno.addItem(i);
        }

        //tabNewBacheca
        // selettore bacheca
        comboAddBacheca.addItem("Università");
        comboAddBacheca.addItem("Lavoro");
        comboAddBacheca.addItem("Tempo Libero");
        comboBox2.addItem("Università");
        comboBox2.addItem("Lavoro");
        comboBox2.addItem("Tempo Libero");
        //tastoAddBacheca (non funzionante)
        buttonAddBacheca.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(principale, "Hai già raggiunto il limite massimo di bacheche");
            }
        });


        //colori
        String colore1= "#F7F9F7";
        String colore2= "#DBF9F0";
        String colore3= "#B3DEC1";
        String colore4= "#C7ECD9";


        principale.setBackground(Color.decode(colore1));
        panelIntestazione.setBackground(Color.decode(colore1));
        panelSpazioIntestazione.setBackground(Color.decode(colore1));
        panelSpazioNewTodo.setBackground(Color.decode(colore1));

        tabPanel.setBackground(Color.decode(colore2));
        tabUni.setBackground(Color.decode(colore2));
        tabLavoro.setBackground(Color.decode(colore2));
        tabTempoLibero.setBackground(Color.decode(colore2));
        tabData.setBackground(Color.decode(colore2));
        tabRicerca.setBackground(Color.decode(colore2));
        tabNewBacheca.setBackground(Color.decode(colore2));
        tabNewTodo.setBackground(Color.decode(colore1));

        panelScrollUni.setBackground(Color.decode(colore3));
        panelMainUni.setBackground(Color.decode(colore3));
        panelSuGiuUni.setBackground(Color.decode(colore3));

        panelScrollLavoro.setBackground(Color.decode(colore3));
        panelMainLavoro.setBackground(Color.decode(colore3));
        panelSuGiuLavoro.setBackground(Color.decode(colore3));

        panelScrollTempoLibero.setBackground(Color.decode(colore3));
        panelMainTempoLibero.setBackground(Color.decode(colore3));
        panelSuGiuTempoLibero.setBackground(Color.decode(colore3));

        panelIntestazioneData.setBackground(Color.decode(colore3));
        panelScrollData.setBackground(Color.decode(colore3));
        panelMainData.setBackground(Color.decode(colore3));
        panelTabDataCerca.setBackground(Color.decode(colore3));
        panelTabDataRisultati.setBackground(Color.decode(colore3));

        panelIntestazioneRicerca.setBackground(Color.decode(colore3));
        panelScrollRicerca.setBackground(Color.decode(colore3));
        panelMainRicerca.setBackground(Color.decode(colore3));
        panelTabRicercaCerca.setBackground(Color.decode(colore3));
        panelTabRicercaRisultati.setBackground(Color.decode(colore3));

        panelIntestazioneNewBacheca.setBackground(Color.decode(colore3));
        panelScrollNewBacheca.setBackground(Color.decode(colore3));
        panelMainNewBacheca.setBackground(Color.decode(colore3));
        panelSpazioNewBacheca.setBackground(Color.decode(colore3));

        panelScrollNewTodo.setBackground(Color.decode(colore3));
        panelMainNewTodo.setBackground(Color.decode(colore3));

        tableToDoUni.setBackground(Color.decode(colore4));
        tableToDoLavoro.setBackground(Color.decode(colore4));
        tableToDoTempoLibero.setBackground(Color.decode(colore4));

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameChiamante.setVisible(true);
                frame.setVisible(false);
                frame.dispose();
            }
        });


    }








}
