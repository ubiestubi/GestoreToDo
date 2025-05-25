package GUI;

import Controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Dettagli {
    public JFrame frame;
    private JPanel principale;
    private JScrollPane panelScrollNewTodo;
    private JPanel panelMainNewTodo;
    private JTextField textTitolo;
    private JComboBox comboBoxBacheca;
    private JTextField textFieldURL;
    private JTextArea textAreaDescrizione;
    private JButton chiudiButton;
    private JButton salvaEChiudiButton;
    private JButton caricaButton;
    private JButton apriButton;
    private JTextField textFieldData;


    public Dettagli(Controller controller) {
        frame = new JFrame("VisualizzaUtenti");
        frame.setContentPane(principale);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(false);
        this.aggiornaDettagli(controller);


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
                controller.todoSelezionato.setTitolo(textTitolo.getText());
                controller.todoSelezionato.setDescrizione(textAreaDescrizione.getText());
                controller.todoSelezionato.setUrl(textFieldURL.getText());
                controller.todoSelezionato = null;
                frame.setVisible(false);
                frame.dispose();
            }
        });
    }
    private void aggiornaDettagli(Controller controller){
        this.textTitolo.setText(controller.todoSelezionato.getTitolo());
        this.textFieldData.setText(controller.todoSelezionato.getScadenza().toString()); // non modificabile, per ora
        this.textAreaDescrizione.setText(controller.todoSelezionato.getDescrizione());
        this.textFieldURL.setText(controller.todoSelezionato.getUrl());
        //manca il poter cambiare la bacheca, questa riga è solo per riempire:
        this.comboBoxBacheca.addItem(controller.todoSelezionato.getBacheca());

    }
}
