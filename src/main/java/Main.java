import Modello.*;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        // crea nuovo utente ed effettua login
        Utente ubi = new Utente("ubi","pass");
        ubi.login("ubi","pass");

        System.out.println("------------");

        //crea 2 bacheche valide e 1 non valida, modifica una ed elimina un'altra, stampa le bacheche col for-each
        Bacheca bacheca1 = ubi.creaBacheca("Tempo Libero", "Cose da fare nel tempo libero");
        Bacheca bacheca2 = ubi.creaBacheca("Università", "Cose da fare in università");
        Bacheca bacheca3 = ubi.creaBacheca("Casa", "Cose da fare a casa");
        bacheca2.setTitolo("Lavoro");
        bacheca2.setDescrizione("Cose da fare a lavoro");
        ubi.eliminaBacheca(bacheca1);
        for (Bacheca b : ubi.bacheche) {
            System.out.println(b.getTitolo() + " : " + b.getDescrizione()); // lavoro
        }

        System.out.println("------------");

        //crea quattro todo, modificane uno, elimina il secondo todo, sposta l'ultimo in prima posizione, stampa la bacheca
        ToDo todoAlfa = bacheca2.creaToDo("alfa", LocalDate.of(2024, 4, 25));
        ToDo todoBeta = bacheca2.creaToDo("beta", LocalDate.of(2024, 4, 26));
        ToDo todoGamma = bacheca2.creaToDo("gamma", LocalDate.of(2026, 4, 27));
        ToDo todoDelta = bacheca2.creaToDo("d", LocalDate.of(2026, 4, 28));
        todoDelta.setTitolo("delta");
        todoBeta.elimina();
        todoDelta.spostaToDo(1);
        for (ToDo t : bacheca2.todos) {
            System.out.println(t.getTitolo() + " in posizione " + t.getPosizione()); // delta alfa gamma
        }

        System.out.println("------------");

        //crea una nuova bacheca, sposta il secondo todo da una bacheca all'altra, stampa entrambe le bacheche
        Bacheca bacheca4 = ubi.creaBacheca("Tempo Libero", "Cose da fare nel tempo libero");
        todoAlfa.spostaToDoInNuovaBacheca(bacheca4);
        for (Bacheca b : ubi.bacheche) {
            System.out.println(b.getTitolo() + " : ");
            for (ToDo t : b.todos) {
                System.out.println(t.getTitolo() + " in posizione " + t.getPosizione());
            }
            System.out.println("----");
        }

        System.out.println("------------");

        //crea un todo e modifica tutto
        ToDo todoTest = bacheca2.creaToDo("test", LocalDate.of(2025, 4, 30));
        todoTest.setTitolo("testTitolo");
        todoTest.setDescrizione("testDescrizione");
        todoTest.setColore("testColore");
        todoTest.setImmagine("testImmagine");
        todoTest.setUrl("testUrl");
        todoTest.setScadenza(LocalDate.of(2026,5,1));
        todoTest.completato();
        //vedere l'oggetto col debug


        //test per le condivisioni: creo un nuovo utente e gli condivido un todo, stampo poi la sua bacheca
        Utente mario = new Utente("mario","pass");
        mario.login("mario","pass");
        Bacheca bachecaMarioLavoro = mario.creaBacheca("Lavoro", "Cose da fare a lavoro");
        todoGamma.condividi(mario);
        for (ToDo t : bachecaMarioLavoro.todos) {
            System.out.println(t.getTitolo() + " in posizione " + t.getPosizione());
        }

        System.out.println("------------");

        //se la bacheca di destinazione non esiste la crea
        todoAlfa.condividi(mario);
        for (Bacheca b : mario.bacheche) {
            System.out.println(b.getTitolo() + " : ");
            for (ToDo t : b.todos) {
                System.out.println(t.getTitolo() + " in posizione " + t.getPosizione());
            }
            System.out.println("----");
        }

        System.out.println("------------");

        //se l'utente originale del todo non è connesso allora non si possono creare o eliminare condivisioni
        ubi.logout();
        todoDelta.condividi(mario);

        System.out.println("------------");

        //si possono vedere gli utenti col quale è condiviso il todo
        System.out.println(todoAlfa.getUtenti());
    }
}