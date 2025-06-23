package gui;

import controller.Controller;
import modello.Bacheca;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.ZoneId;
import java.util.Date;

/**
 * Finestra di dialogo per la visualizzazione e modifica dei dettagli
 * di un {@link modello.ToDo} selezionato in una tabella.
 *
 * <p>Permette all'utente di:</p>
 * <ul>
 *   <li>Modificare titolo, descrizione, URL, bacheca e scadenza del ToDo</li>
 *   <li>Visualizzare o rimuovere un'immagine associata</li>
 *   <li>Caricarne una nuova dal filesystem</li>
 *   <li>Eliminare definitivamente il ToDo</li>
 * </ul>
 *
 * <p>La finestra è composta da componenti Swing e si interfaccia direttamente
 * con il {@link controller.Controller} per aggiornare o salvare le modifiche.</p>
 *
 * <p>La chiusura della finestra azzera la selezione di {@code todoSelezionato}
 * per evitare effetti collaterali nella GUI principale.</p>
 */
public class Dettagli {
    /**
     * Il Frame della finestra Dettagli
     */
    public final JFrame frame;
    private JPanel principale;
    private JScrollPane panelScrollNewTodo;
    private JPanel panelMainNewTodo;
    private JTextField textTitolo;
    private JComboBox<String> comboBoxBacheca;
    private JTextField textFieldURL;
    private JTextArea textAreaDescrizione;
    private JButton chiudiButton;
    private JButton salvaEChiudiButton;
    private JButton caricaButton;
    private JButton apriButton;
    private JSpinner spinnerData;
    private JButton eliminaToDoButton;
    private JButton eliminaImmagineButton;
    private JTextField textFieldData;
    private byte[] immagineCaricataInByte;
    private boolean flagImmagineCaricata = false;


    /**
     * Costruisce e inizializza la finestra dei dettagli di un ToDo.
     *
     * <p>Popola i campi con le informazioni del ToDo selezionato, imposta il comportamento
     * dei bottoni e assegna i listener per modificare o visualizzare le informazioni.</p>
     *
     * @param controller riferimento al {@link controller.Controller} che gestisce i dati e le operazioni
     */
    public Dettagli(Controller controller) {
        frame = new JFrame("VisualizzaUtenti");
        frame.setContentPane(principale);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e){
                controller.todoSelezionato = null;
                frame.setVisible(false);
                frame.dispose();
            }
        });
        frame.pack();
        frame.setVisible(false);
        comboBoxBacheca.addItem("Università");
        comboBoxBacheca.addItem("Lavoro");
        comboBoxBacheca.addItem("Tempo Libero");
        //data
        SpinnerDateModel modelloData = new SpinnerDateModel(); // modello dello spinner
        spinnerData.setModel(modelloData); //lego il modello all'interfaccia
        JSpinner.DateEditor editor = new JSpinner.DateEditor(spinnerData, "dd/MM/yyyy"); //creo un editor, che sarebbe un formattazione dell'interfaccia
        spinnerData.setEditor(editor); // lego l'editor all'interfaccia

        this.aggiornaDettagli(controller);

        eliminaToDoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.eliminaTodo(controller.todoSelezionato);
                controller.refreshTabelle();
                frame.setVisible(false);
                frame.dispose();
            }
        });

        chiudiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.todoSelezionato = null;
                frame.setVisible(false);
                frame.dispose();
            }
        });
        salvaEChiudiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean flagRefresh = false;//se ho cambiato titolo o scadenza o bacheca, forzo il refresh di tutto l'oggetto home
                Date dataSpinner = (Date) spinnerData.getValue(); //prendo l'object dello spinner e lo casto in Date, successivamente lo converto in LocalDate
                if (!controller.todoSelezionato.getTitolo().equals(textTitolo.getText())
                        || !controller.todoSelezionato.getScadenza().equals(dataSpinner.toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                    ) {
                    flagRefresh = true;
                }
                controller.todoSelezionato.setTitolo(textTitolo.getText());
                controller.todoSelezionato.setDescrizione(textAreaDescrizione.getText());
                controller.todoSelezionato.setUrl(textFieldURL.getText());
                if (flagImmagineCaricata){
                    controller.todoSelezionato.setImmagine(immagineCaricataInByte);
                }
                controller.todoSelezionato.setScadenza(dataSpinner.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                if (!controller.todoSelezionato.getBacheca().equals(comboBoxBacheca.getSelectedItem())){
                    for (Bacheca b : controller.utente.bacheche){
                        if (b.getTitolo().equals(comboBoxBacheca.getSelectedItem())){
                            controller.todoSelezionato.spostaToDoInNuovaBacheca(b);
                            flagRefresh = true;
                        }
                    }
                }
                controller.aggiornaDettagliTodo(controller.todoSelezionato);
                controller.todoSelezionato = null;
                frame.setVisible(false);
                frame.dispose();

                if(flagRefresh){
                controller.refreshTabelle();
                }
            }
        });


        apriButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageIcon immagine;
                if (flagImmagineCaricata){
                    immagine = controller.convertiInImmagineDaBytes(immagineCaricataInByte);
                }
                else {
                    if (controller.todoSelezionato.getImmagine() == null || controller.todoSelezionato.getImmagine().length == 0) {
                        JOptionPane.showMessageDialog(principale, "Nessuna immagine è associata a questo ToDo");
                        return;
                    }
                    else {
                        immagine = controller.convertiInImmagineDaBytes(controller.todoSelezionato.getImmagine());

                    }
                }
                JLabel label = new JLabel(immagine);
                JFrame frameImmagine = new JFrame("Visualizzatore Immagine");
                frameImmagine.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                frameImmagine.getContentPane().add(label, BorderLayout.CENTER);
                frameImmagine.pack(); // Ridimensiona la finestra alla dimensione dell'immagine
                frameImmagine.setLocationRelativeTo(null); // Centra la finestra
                frameImmagine.setVisible(true);
            }
        });
        caricaButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int scelta = fileChooser.showOpenDialog(frame);
            if (scelta == JFileChooser.APPROVE_OPTION) {
                immagineCaricataInByte = controller.convertiImmagineCaricataInByte(fileChooser.getSelectedFile(), principale);
                if (immagineCaricataInByte != null) {
                    flagImmagineCaricata = true;
                }
            }
        });

        eliminaImmagineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (controller.todoSelezionato.getImmagine() == null || controller.todoSelezionato.getImmagine().length == 0){
                    JOptionPane.showMessageDialog(principale, "Nessuna immagine è associata a questo ToDo");
                    return;
                }
                controller.todoSelezionato.setImmagine(null);
                JOptionPane.showMessageDialog(principale, "Immagine rimossa con successo");
            }
        });
    }
    /**
     * Popola i campi grafici della finestra con le informazioni correnti
     * del {@link modello.ToDo} selezionato, come titolo, scadenza,
     * descrizione, URL e bacheca.
     *
     * @param controller riferimento al controller per accedere al ToDo selezionato
     */
    private void aggiornaDettagli(Controller controller){
        this.textTitolo.setText(controller.todoSelezionato.getTitolo());
        this.spinnerData.setValue(java.sql.Date.valueOf(controller.todoSelezionato.getScadenza()));
        this.textAreaDescrizione.setText(controller.todoSelezionato.getDescrizione());
        this.textFieldURL.setText(controller.todoSelezionato.getUrl());
        this.comboBoxBacheca.setSelectedItem(controller.todoSelezionato.getBacheca());

    }
}
