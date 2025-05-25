import Controller.Controller;
import GUI.Login;
import Modello.Bacheca;
import Modello.ToDo;
import Modello.Utente;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {
        // popolo di 4 todo e lancio la schermata di login

        Utente ubi = new Utente("ubi","pass");
        Bacheca bachecaTempoLibero = ubi.creaBacheca("Tempo Libero", "Cose da fare nel tempo libero");
        Bacheca bachecaUni = ubi.creaBacheca("Università", "Cose da fare in università");
        Bacheca bachecaLavoro = ubi.creaBacheca("Lavoro", "Cose da fare a lavoro");

        ToDo todoUni1 = bachecaUni.creaToDo("Progetto OO", LocalDate.of(2025, 5, 25));
        ToDo todoUni2 = bachecaUni.creaToDo("Progetto LASD", LocalDate.of(2025, 5, 25));
        ToDo todoUni3 = bachecaUni.creaToDo("Basi di Dati", LocalDate.of(2025, 6, 13));

        todoUni1.setDescrizione("testDescrizione1");
        todoUni1.setUrl("testUrl1");
        todoUni1.completato();

        todoUni2.setDescrizione("testDescrizione2");
        todoUni2.setUrl("testUrl2");
        todoUni2.nonCompletato();

        todoUni3.setDescrizione("testDescrizione3");
        todoUni3.setUrl("testUrl3");
        todoUni3.completato();

        ToDo todoLavoro1 = bachecaLavoro.creaToDo("Cercare un lavoro", LocalDate.of(2024, 7, 6));
        todoLavoro1.setDescrizione("testDescrizioneLavoro");
        todoLavoro1.setUrl("testUrlLavoro");
        todoLavoro1.nonCompletato();

        //lancio del login
        Controller controller = new Controller();
        controller.utente= ubi;
        Login login = new Login(controller);
    }
}
