package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Finestra di accesso (login) all'applicazione Gestore ToDo.
 *
 * <p>Permette all'utente di autenticarsi tramite username e password,
 * oppure registrarsi nel sistema qualora non sia ancora presente.</p>
 *
 * <p>In caso di login riuscito, apre la finestra principale {@link Home}
 * e nasconde la finestra corrente.</p>
 *
 * <p>Personalizza i colori dell’interfaccia ed è costruita con componenti Swing.</p>
 */
public class Login {
    /**
     * Il Frame della finestra Login
     */
    public static JFrame frame;
    /**
     * Il Panel principale della finestra Login
     */
    public JPanel principale;
    private JTextField usernameTextField;
    private JPasswordField passwordField1;
    private JButton accediButton;
    private JPanel panelIntestazione;
    private JPanel panelMain;
    private JPanel panelInserimento;
    private JPanel panelAccedi;
    private JPanel panelSpazio;
    private JButton registratiButton;

    /**
     * Costruisce e visualizza la finestra di login, inizializzando i pannelli e gli eventi associati.
     *
     * @param controller istanza del {@link controller.Controller} che gestisce autenticazione e registrazione
     */
    public Login(Controller controller) {

        frame = new JFrame("Login");
        frame.setContentPane(principale);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        //colori
        String colore1 = "#F7F9F7";
        String colore2 = "#e8e8e8";

        principale.setBackground(Color.decode(colore1));
        panelIntestazione.setBackground(Color.decode(colore1));
        registratiButton.setBackground(Color.decode(colore1));

        panelMain.setBackground(Color.decode(colore2));
        panelAccedi.setBackground(Color.decode(colore2));
        panelInserimento.setBackground(Color.decode(colore2));

        accediButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (controller.login(usernameTextField.getText(), new String(passwordField1.getPassword()), principale)){
                Home home = new Home(frame, controller);
                home.frame.setVisible(true);
                frame.setVisible(false);
                }
                passwordField1.setText("");
            }
        });
        registratiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.registrazioneUtente(usernameTextField.getText(), new String(passwordField1.getPassword()), principale);
                usernameTextField.setText("");
                passwordField1.setText("");
            }
        });

        //se si preme invio nel campo password si clicca il bottone accedi
        passwordField1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accediButton.doClick();
            }
        });

    }
}
