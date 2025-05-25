package GUI;

import Controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login {
    public static JFrame frame;
    public JPanel principale;
    private JTextField ubiTextField;
    private JPasswordField passwordField1;
    private JButton accediButton;
    private JPanel panelIntestazione;
    private JPanel panelMain;
    private JPanel panelInserimento;
    private JPanel panelAccedi;
    private JPanel panelSpazio;


    public Login(Controller controller) {

        frame = new JFrame("Login");
        frame.setContentPane(principale);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        //colori
        String colore1 = "#F7F9F7";
        String colore2 = "#e8e8e8";

        principale.setBackground(Color.decode(colore1));
        panelIntestazione.setBackground(Color.decode(colore1));
        panelSpazio.setBackground(Color.decode(colore1));

        panelMain.setBackground(Color.decode(colore2));
        panelAccedi.setBackground(Color.decode(colore2));
        panelInserimento.setBackground(Color.decode(colore2));

        accediButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Home home = new Home(frame, controller);
                home.frame.setVisible(true);
                frame.setVisible(false);
            }
        });
    }
}
