package main;

import controller.Controller;
import gui.Login;

/**
 * Punto di ingresso principale dell'applicazione Gestore ToDo.
 *
 * <p>Inizializza il {@link controller.Controller} centrale e
 * avvia l'interfaccia grafica di login {@link gui.Login}.</p>
 *
 * <p>Da qui parte l'interazione con l'utente, che può autenticarsi,
 * registrarsi o accedere alle funzionalità complete tramite la GUI.</p>
 */
public class Main {

    /**
     * Metodo di avvio dell'applicazione.
     *
     * <p>Crea un controller condiviso e mostra la finestra di login.</p>
     *
     * @param args argomenti da linea di comando (non utilizzati)
     */
    public static void main(String[] args) {
        Controller controller = new Controller();
        new Login(controller);
    }
}
