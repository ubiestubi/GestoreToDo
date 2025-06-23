package gui;

import controller.Controller;
import modello.TitoloBacheca;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;


/**
 * Finestra principale dell'applicazione Gestore ToDo, che raccoglie tutte le funzionalità
 * per la gestione completa delle attività dell'utente autenticato.
 *
 * <p>La GUI è organizzata in pannelli e schede tematiche:
 * <ul>
 *   <li>Bacheche (Università, Lavoro, Tempo Libero)</li>
 *   <li>Ricerca avanzata per titolo</li>
 *   <li>Visualizzazione attività per scadenza</li>
 *   <li>Creazione di nuovi ToDo</li>
 *   <li>Gestione e descrizione delle bacheche</li>
 * </ul>
 *
 * <p>Le funzionalità principali gestite:
 * <ul>
 *   <li>Ordinamento dei ToDo (sposta su/giù)</li>
 *   <li>Completamento e annullamento ToDo</li>
 *   <li>Visualizzazione dettagli con gestione immagine e eliminazione ToDo</li>
 *   <li>Condivisione del ToDo con altri utenti</li>
 *   <li>Filtraggio per scadenza o testo</li>
 *   <li>Aggiunta e rimozione dinamica delle bacheche</li>
 * </ul>
 *
 * <p>La classe imposta uno schema cromatico coerente, utilizza un {@link controller.Controller}
 * per comunicare con il modello, e personalizza il rendering delle celle delle tabelle
 * in base allo stato e alla scadenza dei ToDo.</p>
 *
 * <p>Alla chiusura richiede conferma e, se confermata, salva lo stato dell’utente
 * e chiude l’applicazione in modo ordinato.</p>
 */

public class Home {
    /**
     * Il Frame della finestra Home.
     */
    public final JFrame frame;
    private JPanel principale;
    private JTabbedPane tabPanel;
    private JPanel tabUni;
    private JPanel tabLavoro;
    private JPanel tabTempoLibero;
    private JPanel tabData;
    private JPanel tabRicerca;
    private JPanel tabGestisciBacheche;
    private JComboBox<String> comboGestisciBacheca;
    private JTextField textAddTodoTitolo;
    private JComboBox<String> comboBoxAddTodoBacheca;
    private JTextField textAddTodoUrl;
    private JButton caricaImmagineButton;
    private JPanel tabNewTodo;
    private JButton aggiungiTodoButton;
    private JTextField textFieldRicerca;
    private JButton buttonRicercaCerca;
    private JButton buttonUtentiUni;
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
    private JScrollPane panelScrollGestisciBacheche;
    private JPanel panelMainGestisciBacheche;
    private JPanel panelNewBacheca;
    private JPanel panelSpazioGestisciBacheche;
    private JScrollPane panelScrollNewTodo;
    private JPanel panelMainNewTodo;
    private JTextArea textAddTodoDescrizione;
    private JPanel panelSpazioNewTodo;
    private JTable tableToDoUni;
    private JButton buttonStatoUni;
    private JButton buttonDettagliUni;
    private JButton buttonSuUni;
    private JButton buttonGiuUni;
    private JPanel panelSuGiuUni;
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
    private JSpinner spinnerAddTodoData;
    private JPanel panelEliminaBacheca;
    private JPanel panelIntestazioneGestisciBacheche;
    private JPanel panelDescrizioneGestisciBacheche;
    private JTextArea textAreaDescrizioneGestisciBacheche;
    private JButton aggiungiBachecaButton;
    private JButton salvaButton;
    private JButton eliminaBachecaButton;
    private JTable tableRicerca;
    private JButton buttonRicercaPulisci;
    private JScrollPane scrollPanelRicerca;
    private JButton buttonRisultatiDettagli;
    private JButton buttonRisultatiUtentiCondivisi;
    private JButton buttonRisultatiCompletatoNonCompletato;
    private JLabel labelBachecaDi;
    private JPanel panelRisultatiButtons;
    private JSpinner spinnerScadenza;
    private JButton buttonScadenzaCerca;
    private JTable tableScadenza;
    private JButton buttonScadenzaDettagli;
    private JButton buttonScadenzaUtentiCondivisi;
    private JButton buttonScadenzaCompletatoNonCompletato;
    private JPanel panelScadenzaButtons;
    private JScrollPane scrollPanelScadenza;
    private JButton buttonScadenzaOggi;
    private JLabel labelNoToDoInScadenzaOggi;
    private JTextArea descrizioneTextArea;
    private JPanel panelMainNewBacheca;
    private JPanel panelTodoSposta;
    private byte[] immagineCaricataInByte = null;
    private final Controller controller;


    /**
     * Costruisce e inizializza la finestra principale dell'applicazione Gestore ToDo.
     *
     * <p>Configura la GUI caricando i dati dell'utente autenticato (ToDo e bacheche),
     * imposta i colori, i pannelli e le schede in modo dinamico in base allo stato del modello.
     * Collega tutti i listener per le operazioni sui ToDo e sulle bacheche.</p>
     *
     * <p>Inoltre, imposta il comportamento alla chiusura del programma, attivando il logout
     * e il salvataggio dello stato.</p>
     *
     * @param frameChiamante finestra di origine (tipicamente il frame {@link gui.Login})
     * @param controller il {@link controller.Controller} associato alla sessione utente corrente
     */
    public Home(JFrame frameChiamante, Controller controller) {

        frame = new JFrame("Home");
        frame.setContentPane(principale);
        //cambio il comportamento alla chiusura della finestra
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int risposta = JOptionPane.showConfirmDialog(
                        frame,
                        "Vuoi uscire dal programma?",
                        "Conferma uscita",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

                if (risposta == JOptionPane.YES_OPTION) {
                    controller.logout();
                    System.exit(0);
                }
            }
        });
        frame.pack();
        frame.setVisible(false);

        this.controller = controller;
        this.controller.ultimaDataRicerca = LocalDate.now();

        //prendo i dati dal db
        controller.caricaListaUtenti();
        controller.caricaBachecheDalDB();
        controller.caricaTodoDalDB();

        labelBachecaDi.setText("Bacheca di " + controller.utente.getUsername());

        //salvo le tab per averle dinamiche
        Component tabSalvataUni = tabPanel.getComponentAt(0);
        Component tabSalvataLavoro = tabPanel.getComponentAt(1);
        Component tabSalvataTempoLibero = tabPanel.getComponentAt(2);

        //tolgo le tab che non hanno corrispondenza con le bacheche
        for (int i = 2; i >= 0; i--) {
            if (!controller.getListBacheche()[i]) {
                tabPanel.removeTabAt(i);
            }
        }

        //descrizioni delle bacheche
        aggiornaDescrizioniBacheche();

        //setta sul controller le tab di home e popola tab
        controller.tabellaUni = tableToDoUni;
        controller.tabellaLavoro = tableToDoLavoro;
        controller.tabellaTempoLibero = tableToDoTempoLibero;
        controller.tabellaRicerca = tableRicerca;
        controller.tabellaScadenza = tableScadenza;
        controller.refreshTabelle();

        //stringhe con nomi di bacheche per selettori
        String selezionaBacheca = "Seleziona...";
        String universitaBacheca = "Università";
        String lavoroBacheca = "Lavoro";
        String tempoLiberoBacheca = "Tempo Libero";

        //tab università
        //scheda utenti
        buttonUtentiUni.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tableToDoUni.getSelectedRow() > -1) {
                    controller.todoSelezionato = controller.trovaTodoDaTabella(tableToDoUni);
                    VisualizzaUtenti visualizzaUtenti = new VisualizzaUtenti(controller);
                    visualizzaUtenti.frame.setVisible(true);
                }
            }
        });
        //spostamento di righe
        buttonSuUni.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.spostaRiga(tableToDoUni, controller.modelloTableUni, -1);
            }
        });
        buttonGiuUni.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.spostaRiga(tableToDoUni, controller.modelloTableUni, 1);
            }
        });
        buttonStatoUni.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tableToDoUni.getSelectedRow() > -1) {
                    controller.cambiaStato(tableToDoUni, controller.modelloTableUni);
                }
            }
        });
        //scheda dettagli
        buttonDettagliUni.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tableToDoUni.getSelectedRow() > -1) {
                    controller.todoSelezionato = controller.trovaTodoDaTabella(tableToDoUni);
                    Dettagli dettagli = new Dettagli(controller);
                    dettagli.frame.setVisible(true);
                }
            }
        });


        //tabLavoro
        //scheda utenti
        buttonUtentiLavoro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tableToDoLavoro.getSelectedRow() > -1) {
                    controller.todoSelezionato = controller.trovaTodoDaTabella(tableToDoLavoro);
                    VisualizzaUtenti visualizzaUtenti = new VisualizzaUtenti(controller);
                    visualizzaUtenti.frame.setVisible(true);
                }
            }
        });
        //spostamento di righe
        buttonSuLavoro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.spostaRiga(tableToDoLavoro, controller.modelloTableLavoro, -1);
            }
        });
        buttonGiuLavoro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.spostaRiga(tableToDoLavoro, controller.modelloTableLavoro, 1);
            }
        });
        buttonStatoLavoro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tableToDoLavoro.getSelectedRow() > -1) {
                    controller.cambiaStato(tableToDoLavoro, controller.modelloTableLavoro);
                }
            }
        });
        //scheda dettagli
        buttonDettagliLavoro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tableToDoLavoro.getSelectedRow() > -1) {
                    controller.todoSelezionato = controller.trovaTodoDaTabella(tableToDoLavoro);
                    Dettagli dettagli = new Dettagli(controller);
                    dettagli.frame.setVisible(true);
                }
            }
        });

        //tabTempoLibero
        //scheda utenti
        buttonUtentiTempoLibero.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tableToDoTempoLibero.getSelectedRow() > -1) {
                    controller.todoSelezionato = controller.trovaTodoDaTabella(tableToDoTempoLibero);
                    VisualizzaUtenti visualizzaUtenti = new VisualizzaUtenti(controller);
                    visualizzaUtenti.frame.setVisible(true);
                }
            }
        });
        //spostamento di righe
        buttonSuTempoLibero.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.spostaRiga(tableToDoTempoLibero, controller.modelloTableTempoLibero, -1);
            }
        });
        buttonGiuTempoLibero.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.spostaRiga(tableToDoTempoLibero, controller.modelloTableTempoLibero, 1);
            }
        });
        buttonStatoTempoLibero.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tableToDoTempoLibero.getSelectedRow() > -1) {
                    controller.cambiaStato(tableToDoTempoLibero, controller.modelloTableTempoLibero);
                }
            }
        });
        //scheda dettagli
        buttonDettagliTempoLibero.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tableToDoTempoLibero.getSelectedRow() > -1) {
                    controller.todoSelezionato = controller.trovaTodoDaTabella(tableToDoTempoLibero);
                    Dettagli dettagli = new Dettagli(controller);
                    dettagli.frame.setVisible(true);
                }
            }
        });

        //tabScadenze
        //setto lo spinner per la data
        SpinnerDateModel modelloDataScadenza = new SpinnerDateModel(); // modello dello spinner
        spinnerScadenza.setModel(modelloDataScadenza); //lego il modello all'interfaccia
        JSpinner.DateEditor editorScadenza = new JSpinner.DateEditor(spinnerScadenza, "dd/MM/yyyy"); //creo un editor, che sarebbe un formattazione dell'interfaccia
        spinnerScadenza.setEditor(editorScadenza); // lego l'editor all'interfaccia

        controller.ricercaScadenze(LocalDate.now(), true); //popolo subito la tabella con i todos di oggi
        if (controller.modelloTableScadenza.getRowCount() == 0) {
            labelNoToDoInScadenzaOggi.setVisible(true);
            scrollPanelScadenza.setVisible(false);
            panelScadenzaButtons.setVisible(false);
        } else {
            labelNoToDoInScadenzaOggi.setVisible(false);
            scrollPanelScadenza.setVisible(true);
            panelScadenzaButtons.setVisible(true);
        }


        buttonScadenzaCerca.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date dataSpinnerScadenza = (Date) spinnerScadenza.getValue(); //prendo l'object dello spinner e lo casto in Date, successivamente lo converto in LocalDate
                controller.ricercaScadenze(dataSpinnerScadenza.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), false);
                labelNoToDoInScadenzaOggi.setVisible(false);
                scrollPanelScadenza.setVisible(true);
                panelScadenzaButtons.setVisible(true);
            }
        });

        buttonScadenzaOggi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.ricercaScadenze(LocalDate.now(), true);
                spinnerScadenza.setValue(new Date());
                if (controller.modelloTableScadenza.getRowCount() == 0) {
                    labelNoToDoInScadenzaOggi.setVisible(true);
                    scrollPanelScadenza.setVisible(false);
                    panelScadenzaButtons.setVisible(false);
                } else {
                    labelNoToDoInScadenzaOggi.setVisible(false);
                    scrollPanelScadenza.setVisible(true);
                    panelScadenzaButtons.setVisible(true);
                }
            }
        });
        buttonScadenzaDettagli.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tableScadenza.getSelectedRow() > -1) {
                    controller.todoSelezionato = controller.trovaTodoDaTabella(tableScadenza);
                    Dettagli dettagli = new Dettagli(controller);
                    dettagli.frame.setVisible(true);
                }
            }
        });
        buttonScadenzaUtentiCondivisi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tableScadenza.getSelectedRow() > -1) {
                    controller.todoSelezionato = controller.trovaTodoDaTabella(tableScadenza);
                    VisualizzaUtenti visualizzaUtenti = new VisualizzaUtenti(controller);
                    visualizzaUtenti.frame.setVisible(true);
                }
            }
        });
        buttonScadenzaCompletatoNonCompletato.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int rigaSelezionata = tableScadenza.getSelectedRow();
                if (rigaSelezionata > -1) {
                    controller.cambiaStato(tableScadenza, controller.modelloTableScadenza);
                }

            }
        });
        //questo aggiorna graficamente la schermata quando viene cliccata la tab (la table invece è refreshata sempre)
        tabPanel.addChangeListener(e -> { // rileva un qualsiasi cambio di stato, anche un selectedIndex, quindi quando clicco su una specifica tab (nel mio caso la tab scadenza)
            int tabIndex = tabPanel.getSelectedIndex();
            if (tabIndex == (tabPanel.getTabCount()-4)) { //parto a contare l'indice della tab a partire dalla fine, dato che i primi 3 tab possono essere rimossi
                if (controller.modelloTableScadenza.getRowCount() == 0) { // fai cose con tabella vuota
                    labelNoToDoInScadenzaOggi.setVisible(true);
                    scrollPanelScadenza.setVisible(false);
                    panelScadenzaButtons.setVisible(false);
                } else { // fai cose con tabella non vuota
                    labelNoToDoInScadenzaOggi.setVisible(false);
                    scrollPanelScadenza.setVisible(true);
                    panelScadenzaButtons.setVisible(true);
                }
            }
        });


        //tab ricerca
        scrollPanelRicerca.setVisible(false);
        panelRisultatiButtons.setVisible(false);

        buttonRicercaCerca.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.popolaRicerca(textFieldRicerca.getText());
                scrollPanelRicerca.setVisible(true);
                panelRisultatiButtons.setVisible(true);
            }
        });
        buttonRicercaPulisci.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.textRicerca = "";
                scrollPanelRicerca.setVisible(false);
                textFieldRicerca.setText("");
                panelRisultatiButtons.setVisible(false);
            }
        });
        buttonRisultatiDettagli.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tableRicerca.getSelectedRow() > -1) {
                    controller.todoSelezionato = controller.trovaTodoDaTabella(tableRicerca);
                    Dettagli dettagli = new Dettagli(controller);
                    dettagli.frame.setVisible(true);
                }
            }
        });
        buttonRisultatiUtentiCondivisi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tableRicerca.getSelectedRow() > -1) {
                    controller.todoSelezionato = controller.trovaTodoDaTabella(tableRicerca);
                    VisualizzaUtenti visualizzaUtenti = new VisualizzaUtenti(controller);
                    visualizzaUtenti.frame.setVisible(true);
                }
            }
        });
        buttonRisultatiCompletatoNonCompletato.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int rigaSelezionata = tableRicerca.getSelectedRow();
                if (rigaSelezionata > -1) {
                    controller.cambiaStato(tableRicerca, controller.modelloTableRicerca);
                }

            }
        });

        //tabGestisciBacheca
        panelDescrizioneGestisciBacheche.setVisible(false);
        panelNewBacheca.setVisible(false);
        panelEliminaBacheca.setVisible(false);
        // selettore bacheca
        comboGestisciBacheca.addItem(selezionaBacheca);
        comboGestisciBacheca.addItem(universitaBacheca);
        comboGestisciBacheca.addItem(lavoroBacheca);
        comboGestisciBacheca.addItem(tempoLiberoBacheca);
        // Listener per rimuovere "Seleziona..." al primo cambio e per far cascare eventi in base se la bacheca esiste già o meno
        comboGestisciBacheca.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (comboGestisciBacheca.getSelectedItem() == selezionaBacheca) // posso usare il == che tanto puntano alla stessa stringa
                    return; // Se è ancora su "Seleziona..." non fa nulla
                if (comboGestisciBacheca.getItemAt(0).equals(selezionaBacheca))
                    comboGestisciBacheca.removeItemAt(0); // Se non è più su "Seleziona..." ed esiste ancora, rimuove l'elemento iniziale
                if (controller.checkEsistenzaBacheca((String) comboGestisciBacheca.getSelectedItem())) {
                    //bacheca c'è
                    panelDescrizioneGestisciBacheche.setVisible(true);
                    panelEliminaBacheca.setVisible(true);
                    panelNewBacheca.setVisible(false);
                    textAreaDescrizioneGestisciBacheche.setText(controller.getDescrizioneBacheca(comboGestisciBacheca.getSelectedItem().toString()));
                } else { //bacheca non c'è
                    panelDescrizioneGestisciBacheche.setVisible(true);
                    panelNewBacheca.setVisible(true);
                    panelEliminaBacheca.setVisible(false);
                    textAreaDescrizioneGestisciBacheche.setText("");
                }
            }
        });
        //bottone SalvaBacheca
        salvaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.setDescrizioneBacheca((String) comboGestisciBacheca.getSelectedItem(), textAreaDescrizioneGestisciBacheche.getText());
                aggiornaDescrizioniBacheche();
                textAreaDescrizioneGestisciBacheche.setText("");
                panelDescrizioneGestisciBacheche.setVisible(false);
                panelEliminaBacheca.setVisible(false);
                comboGestisciBacheca.insertItemAt(selezionaBacheca, 0);
                comboGestisciBacheca.setSelectedIndex(0);
            }
        });
        //bottone EliminaBacheca
        eliminaBachecaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int tabDaRimuovere = controller.eliminaBacheca((String) comboGestisciBacheca.getSelectedItem());
                if (tabDaRimuovere < 0) {
                    JOptionPane.showMessageDialog(principale,"Errore nel rimuovere la bacheca");
                    return;
                }
                tabPanel.removeTabAt(tabDaRimuovere);
                controller.refreshTabelle();
                aggiornaDescrizioniBacheche();
                textAreaDescrizioneGestisciBacheche.setText("");
                panelDescrizioneGestisciBacheche.setVisible(false);
                panelEliminaBacheca.setVisible(false);
                comboGestisciBacheca.insertItemAt(selezionaBacheca, 0);
                comboGestisciBacheca.setSelectedIndex(0);
            }
        });
        //bottone aggiungiBacheca
        aggiungiBachecaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object selected = comboGestisciBacheca.getSelectedItem();
                if (selected == null) return;
                TitoloBacheca titoloBachecaCostante = TitoloBacheca.convertiDaString(selected.toString());
                if (titoloBachecaCostante == null)
                    throw new IllegalStateException("Valore imprevisto: " + selected);

                String descrizione = textAreaDescrizioneGestisciBacheche.getText();
                switch (titoloBachecaCostante) {
                    case UNIVERSITA -> tabPanel.insertTab(universitaBacheca, null, tabSalvataUni, "", controller.aggiungiBacheca(titoloBachecaCostante.mostraTitoloVero(), descrizione));
                    case LAVORO -> tabPanel.insertTab(lavoroBacheca, null, tabSalvataLavoro, "", controller.aggiungiBacheca(titoloBachecaCostante.mostraTitoloVero(), descrizione));
                    case TEMPO_LIBERO -> tabPanel.insertTab(tempoLiberoBacheca, null, tabSalvataTempoLibero, "", controller.aggiungiBacheca(titoloBachecaCostante.mostraTitoloVero(), descrizione));
                }
                aggiornaDescrizioniBacheche();
                textAreaDescrizioneGestisciBacheche.setText("");
                panelDescrizioneGestisciBacheche.setVisible(false);
                panelNewBacheca.setVisible(false);
                comboGestisciBacheca.insertItemAt(selezionaBacheca, 0);
                comboGestisciBacheca.setSelectedIndex(0);
            }
        });


        //tabNewTodo
        comboBoxAddTodoBacheca.addItem(selezionaBacheca);
        // Listener per rimuovere "Seleziona..." al primo cambio
        comboBoxAddTodoBacheca.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (comboBoxAddTodoBacheca.getSelectedIndex() == 0)
                    return; // Se è ancora su "Seleziona..." non fa nulla
                comboBoxAddTodoBacheca.removeItemAt(0); // Rimuove l'elemento iniziale
                comboBoxAddTodoBacheca.removeActionListener(this); // Evita future chiamate inutili
            }
        });
        comboBoxAddTodoBacheca.addItem(universitaBacheca);
        comboBoxAddTodoBacheca.addItem(lavoroBacheca);
        comboBoxAddTodoBacheca.addItem(tempoLiberoBacheca);
        //setto lo spinner per la data
        SpinnerDateModel modelloData = new SpinnerDateModel(); // modello dello spinner
        spinnerAddTodoData.setModel(modelloData); //lego il modello all'interfaccia
        JSpinner.DateEditor editor = new JSpinner.DateEditor(spinnerAddTodoData, "dd/MM/yyyy"); //creo un editor, che sarebbe un formattazione dell'interfaccia
        spinnerAddTodoData.setEditor(editor); // lego l'editor all'interfaccia
        //bottone per caricare immagine
        caricaImmagineButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int scelta = fileChooser.showOpenDialog(frame);
            if (scelta == JFileChooser.APPROVE_OPTION) {
                immagineCaricataInByte = controller.convertiImmagineCaricataInByte(fileChooser.getSelectedFile(), principale);
            }
        });
        //bottone AddTodo
        aggiungiTodoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date dataSpinner = (Date) spinnerAddTodoData.getValue(); //prendo l'object dello spinner e lo casto in Date, successivamente lo converto in LocalDate
                controller.aggiungiTodo(textAddTodoTitolo.getText(), dataSpinner.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                        (String) comboBoxAddTodoBacheca.getSelectedItem(), textAddTodoDescrizione.getText(), textAddTodoUrl.getText(), immagineCaricataInByte ,principale);
                //popolaTabelle
                controller.refreshTabelle();
                //resetta i valori della pagina
                textAddTodoTitolo.setText("");
                textAddTodoDescrizione.setText("");
                textAddTodoUrl.setText("");
                immagineCaricataInByte = null;

            }
        });


        //colori
        String colore1 = "#F7F9F7";
        String colore2 = "#DBF9F0";
        String colore3 = "#B3DEC1";
        String colore4 = "#C7ECD9";
        String coloreRosso = "#F16464";
        String coloreVerde = "#95DBB6";
        String coloreBeige = "#F7F9F7";
        String coloreFocus = "#767676";


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
        tabGestisciBacheche.setBackground(Color.decode(colore2));
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
        panelScadenzaButtons.setBackground(Color.decode(colore3));

        panelIntestazioneRicerca.setBackground(Color.decode(colore3));
        panelScrollRicerca.setBackground(Color.decode(colore3));
        panelMainRicerca.setBackground(Color.decode(colore3));
        panelTabRicercaCerca.setBackground(Color.decode(colore3));
        panelTabRicercaRisultati.setBackground(Color.decode(colore3));
        panelRisultatiButtons.setBackground(Color.decode(colore3));

        panelNewBacheca.setBackground(Color.decode(colore3));
        panelScrollGestisciBacheche.setBackground(Color.decode(colore3));
        panelMainGestisciBacheche.setBackground(Color.decode(colore3));
        panelEliminaBacheca.setBackground(Color.decode(colore3));
        panelSpazioGestisciBacheche.setBackground(Color.decode(colore3));
        textAreaDescrizioneGestisciBacheche.setBorder(BorderFactory.createLineBorder(Color.decode(colore3), 10, false));
        panelDescrizioneGestisciBacheche.setBackground(Color.decode(colore3));
        panelIntestazioneGestisciBacheche.setBackground(Color.decode(colore3));

        panelScrollNewTodo.setBackground(Color.decode(colore3));
        panelMainNewTodo.setBackground(Color.decode(colore3));

        tableToDoUni.setBackground(Color.decode(colore4));
        tableToDoLavoro.setBackground(Color.decode(colore4));
        tableToDoTempoLibero.setBackground(Color.decode(colore4));
        tableScadenza.setBackground(Color.decode(colore4));
        tableRicerca.setBackground(Color.decode(colore4));

        //il renderer è la classe che gestisce come si vedranno a schermo le celle della tabella: la tabella usa il Component del renderer per portare a schermo i dati del modello
        //setto un nuovo render per la tabella e ci sovrascrivo il metodo getTableCellRendererComponent, che è il Component responsabile del rendering della cella
        //ci faccio cmq il super in modo tale che rimanga identica all'originale ma ci aggiungo l'if per decidere che colore deve usare il Component per darlo alla cella
        //una volta settato il colore, se non si tocca più, tutte le celle renderizzate successivamente avranno questo colore
        //perchè per ogni cella la table prende e usa l'oggetto Component
        DefaultTableCellRenderer rendererTabelle = new DefaultTableCellRenderer(){
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                Component cell = super.getTableCellRendererComponent(table, value, false, hasFocus, row, column);
                if (isSelected) {
                    setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                    setForeground(Color.WHITE);  // testo bianco
                } else {
                    setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
                    setForeground(table.getForeground());
                }
                if (value != null) { //tanto per
                    if (table.getValueAt(row,0).toString().equals("Non completato")) {
                        if (table.getValueAt(row, 2).toString().compareTo(LocalDate.now().toString())<0) { // cioè se la data in tabella è minore di quella di oggi
                            cell.setBackground(isSelected ? Color.decode(coloreFocus) : Color.decode(coloreRosso));
                        }
                        else {
                            cell.setBackground(isSelected ? Color.decode(coloreFocus) : Color.decode(coloreBeige));
                        }
                    } else if (table.getValueAt(row,0).toString().equals("Completato")) {
                        cell.setBackground(isSelected ? Color.decode(coloreFocus) : Color.decode(coloreVerde));
                    }
                } else {
                    cell.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
                }

                return cell;
            }
        };

        tableToDoUni.setDefaultRenderer(Object.class, rendererTabelle);
        tableToDoLavoro.setDefaultRenderer(Object.class, rendererTabelle);
        tableToDoTempoLibero.setDefaultRenderer(Object.class, rendererTabelle);
        tableScadenza.setDefaultRenderer(Object.class, rendererTabelle);
        tableRicerca.setDefaultRenderer(Object.class, rendererTabelle);


        //possibilità di deselezionare la riga quando si clicca una zona della tabella vuota
        tableToDoUni.setFillsViewportHeight(true);
        tableToDoLavoro.setFillsViewportHeight(true);
        tableToDoTempoLibero.setFillsViewportHeight(true);
        tableScadenza.setFillsViewportHeight(true);
        tableRicerca.setFillsViewportHeight(true);

        tableToDoUni.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int row = tableToDoUni.rowAtPoint(e.getPoint());
                int column = tableToDoUni.columnAtPoint(e.getPoint());
                if (row == -1 || column == -1) {
                    tableToDoUni.clearSelection();
                }
            }
        });
        tableToDoLavoro.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int row = tableToDoLavoro.rowAtPoint(e.getPoint());
                int column = tableToDoLavoro.columnAtPoint(e.getPoint());
                if (row == -1 || column == -1) {
                    tableToDoLavoro.clearSelection();
                }
            }
        });
        tableToDoTempoLibero.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int row = tableToDoTempoLibero.rowAtPoint(e.getPoint());
                int column = tableToDoTempoLibero.columnAtPoint(e.getPoint());
                if (row == -1 || column == -1) {
                    tableToDoTempoLibero.clearSelection();
                }
            }
        });
        tableScadenza.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int row = tableScadenza.rowAtPoint(e.getPoint());
                int column = tableScadenza.columnAtPoint(e.getPoint());
                if (row == -1 || column == -1) {
                    tableScadenza.clearSelection();
                }
            }
        });
        tableRicerca.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int row = tableRicerca.rowAtPoint(e.getPoint());
                int column = tableRicerca.columnAtPoint(e.getPoint());
                if (row == -1 || column == -1) {
                    tableRicerca.clearSelection();
                }
            }
        });





        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.logout();
                frameChiamante.setVisible(true);
                frame.setVisible(false);
                frame.dispose();
            }
        });


    }


 private void aggiornaDescrizioniBacheche(){
     String[] descrizioniBacheche = this.controller.getDescrizioniTabelle();
     tabPanel.setToolTipTextAt(0, descrizioniBacheche[0]);
     tabPanel.setToolTipTextAt(1, descrizioniBacheche[1]);
     tabPanel.setToolTipTextAt(2, descrizioniBacheche[2]);
 }





}
