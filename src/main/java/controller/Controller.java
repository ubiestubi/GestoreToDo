package controller;

import dao.*;
import implementazioneMySQLDAO.*;
import modello.Bacheca;
import modello.TitoloBacheca;
import modello.ToDo;
import modello.Utente;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Classe principale del livello di controllo dell'applicazione Gestore ToDo.
 *
 * <p>Gestisce le interazioni tra il modello e la GUI, coordinando operazioni di:
 * <ul>
 *   <li>Creazione, modifica e cancellazione di {@link modello.ToDo}</li>
 *   <li>Gestione e aggiornamento delle {@link modello.Bacheca}</li>
 *   <li>Persistenza dei dati tramite DAO</li>
 *   <li>Ricerca, filtraggio e ordinamento delle attività</li>
 *   <li>Caricamento e salvataggio delle immagini associate ai ToDo</li>
 *   <li>Gestione della sessione utente (login, logout, registrazione)</li>
 * </ul>
 *
 * <p>Funge da punto di raccordo fra la logica di business e l’interfaccia utente Swing.
 * Supporta anche funzioni avanzate come la condivisione dei ToDo con altri utenti,
 * l'elaborazione grafica di immagini e la sincronizzazione in tempo reale con il database.</p>
 *
 * <p>Utilizza classi DAO del package {@code dao} e le loro implementazioni
 * nel package {@code implementazioneMySQLDAO} per interagire con il database MySQL.</p>
 */

public class Controller {
    /**
     * Utente attualmente autenticato nell'applicazione.
     */
    public Utente utente;
    /**
     * ToDo attualmente selezionato in una delle tabelle.
     */
    public ToDo todoSelezionato;
    /** Tabella associata alla bacheca "Università". */
    public JTable tabellaUni;
    /** Tabella associata alla bacheca "Lavoro". */
    public JTable tabellaLavoro;
    /** Tabella associata alla bacheca "Tempo Libero". */
    public JTable tabellaTempoLibero;
    /** Tabella usata per visualizzare i risultati della ricerca testuale. */
    public JTable tabellaRicerca;
    /** Tabella usata per mostrare i ToDo con scadenza attuale o specificata. */
    public JTable tabellaScadenza;
    /** Modello di dati per la tabella "Università". */
    public DefaultTableModel modelloTableUni;
    /** Modello di dati per la tabella "Lavoro". */
    public DefaultTableModel modelloTableLavoro;
    /** Modello di dati per la tabella "Tempo Libero". */
    public DefaultTableModel modelloTableTempoLibero;
    /** Modello di dati per i risultati della ricerca. */
    public DefaultTableModel modelloTableRicerca;
    /** Modello di dati per i ToDo in scadenza. */
    public DefaultTableModel modelloTableScadenza;
    /**
     * Ultima stringa di testo inserita per filtrare i titoli dei ToDo.
     */
    public String textRicerca = "";
    /**
     * Ultima data usata per la ricerca dei ToDo in scadenza.
     */
    public LocalDate ultimaDataRicerca;
    /**
     * Insieme di tutti gli username registrati nel sistema,
     * usato per validare le condivisioni dei ToDo.
     */
    public final Set<String> listaUtenti = new HashSet<>(); // lo uso per fare un check quando condivido i todoos
    private String [] headerTabellaTodo = new String[]{"Stato", "Titolo", "Data"};
    private String [] headerTabellaRicercaEScadenza= new String[]{"Stato", "Titolo", "Data", "Bacheca"};



    /**
     * Costruttore di default della classe {@code Controller}.
     *
     * <p>Inizializza il controller senza parametri; l’utente viene impostato successivamente
     * tramite {@link #login(String, String, JPanel)}.</p>
     */
    public Controller (){
            // Vuoto perchè inizializzo gli attributi man mano nell'esecuzione del programma
    }

    //Accesso e gestione utente

    /**
     * Effettua la procedura di login autenticando l'utente nel database.
     *
     * @param username nome utente inserito
     * @param password password inserita
     * @param principale riferimento al pannello Swing per messaggi di dialogo
     * @return {@code true} se il login ha avuto successo, altrimenti {@code false}
     */
    public boolean login(String username, String password,  JPanel principale) {
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(principale, "Inserire nome utente e password" );
            return false;
        }
        LoginDAO loginDAO = new LoginImplementazioneMySQLDAO();
        try {
            if (loginDAO.login(username, password)){
                this.utente = new Utente(username);
                return true;
            }
            else  {
                JOptionPane.showMessageDialog(principale, "Login non valido. Username e/o password errati" );
                return false;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(principale, "Errore nella procedura di login: \n" + e.getMessage() );
        }
        return false;
    }

    /**
     * Salva tutte le modifiche e ripristina lo stato iniziale
     * del controller alla chiusura della sessione utente.
     */
    public void logout(){
        aggiornaTodosAlLogout(this.utente);
        this.utente.bacheche.clear();
        this.utente = null;
        this.listaUtenti.clear();
        this.todoSelezionato = null;
        this.tabellaUni = null;
        this.tabellaLavoro = null;
        this.tabellaTempoLibero = null;
        this.tabellaRicerca = null;
        this.tabellaScadenza = null;
        this.modelloTableUni = null;
        this.modelloTableLavoro = null;
        this.modelloTableTempoLibero = null;
        this.modelloTableRicerca = null;
        this.modelloTableScadenza = null;
        this.textRicerca = "";
        this.ultimaDataRicerca = null;
    }

    /**
     * Registra un nuovo utente nel sistema, se lo username non è già esistente.
     *
     * @param username nome utente desiderato
     * @param password password da associare
     * @param principale pannello Swing per visualizzare eventuali messaggi di errore o conferma
     */
    public void registrazioneUtente(String username, String password, JPanel principale) {
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(principale, "Inserire nome utente e password" );
            return;
        }
        RegistrazioneUtenteDAO registrazioneUtenteDAO = new RegistrazioneUtenteImplementazioneMySQLDAO();
        try {
            registrazioneUtenteDAO.registrazioneUtente(username, password);
            JOptionPane.showMessageDialog(principale, "Utente registrato con successo" );

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(principale, "Errore durante la registrazione utente: \n" + e.getMessage());
        }
    }

    //Operazioni su bacheche

    /**
     * Verifica se esiste una bacheca con il titolo specificato.
     *
     * @param titoloBacheca titolo della bacheca da cercare
     * @return {@code true} se la bacheca è presente, {@code false} altrimenti
     */
    public boolean checkEsistenzaBacheca(String titoloBacheca) {
        for (Bacheca b : utente.bacheche) {
            if (b.getTitolo().equals(titoloBacheca)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Restituisce la descrizione associata alla bacheca specificata.
     *
     * @param titoloBacheca titolo della bacheca
     * @return la descrizione della bacheca, oppure una stringa vuota se non trovata
     */
    public String getDescrizioneBacheca(String titoloBacheca) {
        for (Bacheca b : utente.bacheche) {
            if (b.getTitolo().equals(titoloBacheca)) {
                return b.getDescrizione();
            }
        }
        return "";
    }

    /**
     * Aggiorna la descrizione di una bacheca esistente nel modello e nel database.
     *
     * @param titoloBacheca titolo della bacheca
     * @param descrizioneBacheca nuova descrizione da salvare
     */
    public void setDescrizioneBacheca(String titoloBacheca, String descrizioneBacheca) {
        for (Bacheca b : utente.bacheche) {
            if (b.getTitolo().equals(titoloBacheca)) {
                ModificaBachecaDAO modificaBachecaDAO = new ModificaBachecaImplementazioneMySQLDAO();
                try {
                    modificaBachecaDAO.aggiornaBachecaAlDB(utente.getUsername(),titoloBacheca, descrizioneBacheca);
                    b.setDescrizione(descrizioneBacheca);
                } catch (SQLException e) {
                    System.out.println("Errore nell'aggiornamento della descrizione bacheca nel database: " + e.getMessage());
                }
            }
        }
    }

    /**
     * Aggiunge una nuova bacheca per l'utente corrente e la salva nel database.
     *
     * @param titoloBacheca titolo della bacheca da creare
     * @param descrizioneBacheca descrizione testuale della bacheca
     * @return indice della scheda da attivare nella GUI in base al titolo
     */
    public int aggiungiBacheca(String titoloBacheca, String descrizioneBacheca){ //mi returna un int che sarebbe quale tab devo mostrare
        utente.creaBacheca(titoloBacheca, descrizioneBacheca );
        AggiungiBachecaDAO aggiungiBachecaDAO = new AggiungiBachecaImplementazioneMySQLDAO();
        aggiungiBachecaDAO.aggiungiBachecaAlDB(titoloBacheca, descrizioneBacheca, utente.getUsername());
        TitoloBacheca titoloBachecaCostante = TitoloBacheca.convertiDaString(titoloBacheca);
        return switch (titoloBachecaCostante) {
            case UNIVERSITA -> 0;
            case LAVORO -> 1;
            case TEMPO_LIBERO -> 2;
            case null -> 3;
        };
    }

    /**
     * Rimuove una bacheca dal modello e dal database.
     *
     * @param titoloBacheca titolo della bacheca da eliminare
     * @return indice della scheda da rimuovere, oppure -1 se la bacheca non esiste
     */
    public int eliminaBacheca(String titoloBacheca){ //mi returna un int che sarebbe quale tab devo eliminare
        for (Bacheca b : utente.bacheche) {
            if (b.getTitolo().equals(titoloBacheca)) {
                EliminaBachecaDAO eliminaBachecaDAO = new EliminaBachecaImplementazioneMySQLDAO();
                try {
                    eliminaBachecaDAO.eliminaBachecaDalDB(utente.getUsername(), titoloBacheca);
                    b.todos.clear();
                    utente.eliminaBacheca(b);
                    TitoloBacheca titoloBachecaCostante = TitoloBacheca.convertiDaString(titoloBacheca);
                    if (titoloBachecaCostante == null) {
                        throw new IllegalStateException("Valore non previsto: " + titoloBacheca);
                    }
                    return switch (titoloBachecaCostante) {
                        case UNIVERSITA -> 0;
                        case LAVORO -> 1;
                        case TEMPO_LIBERO -> 2;
                    };
                } catch (SQLException e) {
                    System.out.println("Errore nell'eliminazione della bacheca dal database: " + e.getMessage());
                }
            }
        }
        return -1;
    }

    //Operazioni su ToDo

    /**
     * Aggiunge un nuovo ToDo con i parametri specificati e lo salva nel database.
     *
     * @param titolo titolo dell'attività
     * @param scadenza data di scadenza
     * @param bacheca titolo della bacheca di destinazione
     * @param descrizione testo descrittivo dell’attività
     * @param url link associato all’attività
     * @param immagine array di byte contenente l’immagine (facoltativa)
     * @param principale pannello Swing per eventuali messaggi
     */
    public void aggiungiTodo(String titolo, LocalDate scadenza, String bacheca, String descrizione, String url, byte[] immagine, JPanel principale){
        boolean bachecaTrovata = false;
        for (Bacheca b : utente.bacheche) {
            for (ToDo t : b.todos){
                if (titolo.equals(t.getTitolo())){
                    JOptionPane.showMessageDialog(principale, "Impossibile creare questo ToDo: un ToDo con lo stesso titolo è già esistente");
                    return;
                }
            }
        }
        for (Bacheca b: utente.bacheche) {
            if (b.getTitolo().equals(bacheca)){
                bachecaTrovata = true;
                ToDo todoDaAggiungere = new ToDo(titolo,scadenza,b,utente.getUsername());
                todoDaAggiungere.setUrl(url);
                todoDaAggiungere.setDescrizione(descrizione);
                if (immagine != null) {
                    todoDaAggiungere.setImmagine(immagine);
                }
                AggiungiTodoDAO aggiungiTodoDAO = new AggiungiTodoImplementazioneMySQLDAO();
                try {
                    aggiungiTodoDAO.aggiungiTodoAlDB(todoDaAggiungere);
                } catch (SQLException e) {
                    System.out.println("Errore nell'aggiunta del todo al database: " + e.getMessage());
                }
                AggiungiUtenteCondivisoDAO aggiungiUtenteCondivisoDAO = new AggiungiUtenteCondivisoImplementazioneMySQLDAO();
                try {
                    aggiungiUtenteCondivisoDAO.aggiungiCondivisione(todoDaAggiungere.getId(), utente.getUsername(), b.getTitolo());
                } catch (SQLException e) {
                    System.out.println("Errore nell'aggiunta del todo al database(tabella TODOhasUTENTI): " + e.getMessage());
                }
            }
        }
        if(!bachecaTrovata) {
            JOptionPane.showMessageDialog(principale, "La bacheca non esiste");
        }
        else {
            JOptionPane.showMessageDialog(principale, "ToDo aggiunto correttamente");

        }
    }

    /**
     * Elimina definitivamente un ToDo selezionato, sia dal modello che dal database.
     *
     * @param todo oggetto ToDo da cancellare
     */
    public void eliminaTodo(ToDo todo){
        EliminaTodoDAO eliminaTodoDAO = new EliminaTodoImplementazioneMySQLDAO();
        try {
            eliminaTodoDAO.eliminaTodoDalDB(todo);
            todo.elimina();
        } catch (SQLException e) {
            System.out.println("Errore nella cancellazione del todo: " + e.getMessage());
        }
    }

    /**
     * Aggiunge un utente alla lista di condivisione del {@link modello.ToDo} selezionato, sia nel model che nel database,
     * solo se l’utente è l'autore originale e se l’utente da condividere è registrato.
     *
     * @param input campo di testo contenente lo username da aggiungere
     * @param modello modello della lista grafica da aggiornare
     * @param principale pannello per eventuali messaggi di dialogo
     */
    public void aggiungiUtenteCondiviso(JTextField input, DefaultListModel<String> modello, JPanel principale) {
        if (!todoSelezionato.getUtenteOriginale().equals(utente.getUsername())){
            JOptionPane.showMessageDialog(principale,"Solo il creatore del Todo può aggiungere condivisioni");
            return;
        }
        String nome = input.getText().trim();
        for (String utenteDaAggiungere : listaUtenti){
            if (nome.equals(utenteDaAggiungere)){
                AggiungiUtenteCondivisoDAO aggiungiUtenteCondivisoDAO = new AggiungiUtenteCondivisoImplementazioneMySQLDAO();
                try {
                    aggiungiUtenteCondivisoDAO.aggiungiCondivisione(todoSelezionato.getId(), utenteDaAggiungere, todoSelezionato.getBacheca());
                    todoSelezionato.condividi(utenteDaAggiungere);
                    modello.addElement(nome);
                    return;
                }
                catch (SQLException e) {
                    System.out.println("Errore database nell'aggiunta della condivisione: " + e.getMessage());
                }
            }
        }
        JOptionPane.showMessageDialog(principale,"Impossibile condividere ToDo: l'utente non esiste");

    }

    /**
     * Rimuove un utente dalla lista di condivisione del {@link modello.ToDo} selezionato, sia dal model che dal database,
     * se l’operazione è effettuata dal creatore del ToDo e non riguarda sé stesso.
     *
     * @param lista componente JList con gli utenti con cui è condiviso il ToDo
     * @param modello modello della lista da cui rimuovere l'utente
     * @param principale pannello per eventuali messaggi o errori
     */
    public void rimuoviUtenteCondiviso(JList<String> lista, DefaultListModel<String> modello, JPanel principale) {
        if (!todoSelezionato.getUtenteOriginale().equals(utente.getUsername())){
            JOptionPane.showMessageDialog(principale,"Solo il creatore del Todo può rimuovere condivisioni");
            return;
        }
        int indiceSelezionato = lista.getSelectedIndex();
        if (indiceSelezionato != -1) { // Verifica che qualcosa sia selezionato
            String usernameDaCancellare = modello.elementAt(indiceSelezionato);
            if (usernameDaCancellare.equals(utente.getUsername())){
                JOptionPane.showMessageDialog(principale,"Non puoi rimuovere l'autore originale del ToDo");
                return;
            }
            RimuoviUtenteCondivisoDAO rimuoviUtenteCondivisoDAO = new RimuoviUtenteCondivisoImplementazioneMySQLDAO();
            try {
                rimuoviUtenteCondivisoDAO.rimuoviCondivisione(todoSelezionato.getId(),usernameDaCancellare);
                todoSelezionato.eliminaCondivisione(usernameDaCancellare);
                modello.remove(indiceSelezionato);
            } catch (SQLException e) {
                System.out.println("Errore database nella rimozione della condivisione: " + e.getMessage());
            }
        }
    }

    /**
     * Cambia lo stato di completamento del ToDo selezionato nella tabella,
     * alternando tra "Completato" e "Non completato" e aggiornando la vista.
     *
     * @param tabella tabella contenente il ToDo selezionato
     * @param modello modello dati della tabella
     */
    public void cambiaStato(JTable tabella, DefaultTableModel modello){

        int rigaSelezionata = tabella.getSelectedRow();
        ToDo todo = trovaTodoDaTabella(tabella);
        String valoreAttuale = (String) modello.getValueAt(rigaSelezionata, 0);
        switch (valoreAttuale){
            case "Completato":
                modello.setValueAt("Non completato", rigaSelezionata, 0);
                todo.nonCompletato();
                break;
            case "Non completato":
                modello.setValueAt("Completato", rigaSelezionata, 0);
                todo.completato();
                break;
            default:
                break;
        }
        refreshTabelle(); //aggiorno tutte le tabelle
    }

    /**
     * Sposta la riga selezionata nella tabella su o giù,
     * modificando di conseguenza anche la posizione nel modello {@link modello.ToDo}.
     *
     * @param tabella tabella da cui spostare la riga
     * @param modello modello dati della tabella
     * @param direzione direzione di spostamento: {@code -1} per su, {@code +1} per giù
     */
    public void spostaRiga(JTable tabella, DefaultTableModel modello, int direzione) {
        int rigaSelezionata = tabella.getSelectedRow();
        if (rigaSelezionata == -1)
            return; // nessuna riga selezionata

        int nuovaPosizione = rigaSelezionata + direzione;
        if (nuovaPosizione < 0 || nuovaPosizione >= modello.getRowCount())
            return; // inizio o fine tabella

        // scambia le righe, prima graficamente...
        Object[] datiRiga = new Object[modello.getColumnCount()];
        for (int i = 0; i < modello.getColumnCount(); i++) {
            datiRiga[i] = modello.getValueAt(rigaSelezionata, i);
        }

        modello.removeRow(rigaSelezionata);
        modello.insertRow(nuovaPosizione, datiRiga);

        // riseleziona la posizione
        tabella.setRowSelectionInterval(nuovaPosizione, nuovaPosizione);

        //...poi anche realmente
        ToDo todo = trovaTodoDaTabella(tabella);
        todo.spostaToDo(nuovaPosizione+1);
    }

    /**
     * Aggiorna nel database tutti i dettagli di un ToDo modificato nella GUI.
     *
     * @param todo ToDo da aggiornare
     */
    public void aggiornaDettagliTodo(ToDo todo){
        AggiornaDettagliTodoDAO aggiornaDettagliTodoDAO = new AggiornaDettagliTodoImplementazioneMySQLDAO();
        try {
            aggiornaDettagliTodoDAO.aggiornaDettagliTodoAlDB(todo);
        } catch (SQLException e) {
            System.out.println("Errore nell'update dei dettagli nel db: " + e.getMessage());
        }
    }

    /**
     * Salva lo stato dei ToDo al termine della sessione, aggiornando
     * posizione e stato di completamento nel database.
     *
     * @param utente utente corrente
     */
    public void aggiornaTodosAlLogout(Utente utente){
        ArrayList<ToDo> todos = new ArrayList<>();
        for (Bacheca b : utente.bacheche) {
            todos.addAll(b.todos);
        }
        AggiornaTodosAlLogoutDAO aggiornaTodosAlLogoutDAO = new AggiornaTodosAlLogoutImplementazioneMySQLDAO();
        try {
            aggiornaTodosAlLogoutDAO.aggiornaTuttiTodosAlLogout(todos);
        } catch (SQLException e) {
            System.out.println("Errore nell'update dei todos al logout nel db: " + e.getMessage());
        }

    }

    //Operazioni di caricamento dati dal database al model

    /**
     * Carica la lista degli utenti registrati nel sistema.
     * Usata per la condivisione dei ToDo.
     */
    public void caricaListaUtenti(){
        CaricaListaUtentiDAO caricaListaUtentiDAO = new CaricaListaUtentiImplementazioneMySQLDAO();
        try {
            caricaListaUtentiDAO.caricaListaUtenti(this.listaUtenti);
        } catch (SQLException e) {
            System.out.println("Errore nel caricamento della lista utenti: " + e.getMessage());
        }
    }

    /**
     * Carica nel model le bacheche dell'utente corrente dal database.
     */
    public void caricaBachecheDalDB(){
        ArrayList<String> titoli = new ArrayList<>() ;
        ArrayList<String> descrizioni = new ArrayList<>() ;
        CaricaBachecheDAO caricaBachecheDAO = new CaricaBachecheImplementazioneMySQLDAO();
        try {
            caricaBachecheDAO.caricaBachecheDalDB(utente.getUsername(), titoli,descrizioni);
            for (int i = 0; i < titoli.size(); i++){
                utente.creaBacheca(titoli.get(i),descrizioni.get(i));
            }
        } catch (SQLException e) {
            System.out.println("Errore nel caricamento Bacheche dal database. Errore: " + e.getMessage());
        }
    }

    /**
     * Carica nel model tutti i ToDo associati all'utente dal database,
     * includendo anche quelli condivisi e i relativi metadati.
     */
    public void caricaTodoDalDB(){
        ArrayList<String> utenteOriginale = new ArrayList<>() ;
        ArrayList<Integer> id = new ArrayList<>() ;
        ArrayList<String> titolo = new ArrayList<>() ;
        ArrayList<LocalDate> scadenza = new ArrayList<>() ;
        ArrayList<String> url = new ArrayList<>() ;
        ArrayList<String> descrizione = new ArrayList<>() ;
        ArrayList<String> bachecaTitolo = new ArrayList<>() ;
        ArrayList<Boolean> stato = new ArrayList<>() ;
        ArrayList<Integer> posizione = new ArrayList<>() ;
        ArrayList<byte[]> immagine = new ArrayList<>() ;
        HashMap<Integer, ArrayList<String>> mappaUtentiCondivisi = new HashMap<>() ;
        try {
            CaricaTodoDAO caricaTodoDAO = new CaricaTodoImplementazioneMySQLDAO();
            caricaTodoDAO.caricaTodoDalDB(utente.getUsername(), utenteOriginale,id,titolo, scadenza, url, descrizione, bachecaTitolo, stato, posizione, immagine);
            CaricaCondivisioniTodoDAO caricaCondivisioniTodoDAO = new CaricaCondivisioniTodoImplementazioneMySQLDAO();
            caricaCondivisioniTodoDAO.caricaCondivisioniTodoDalDB(utente.getUsername(), mappaUtentiCondivisi);
            for (int i = 0; i < id.size(); i++){
                for (Bacheca b: utente.bacheche) {
                    if (b.getTitolo().equals(bachecaTitolo.get(i))) {
                        ToDo nuovoTodo = new ToDo(titolo.get(i), scadenza.get(i),b, utenteOriginale.get(i));
                        nuovoTodo.setId(id.get(i));
                        nuovoTodo.setUrl(url.get(i));
                        nuovoTodo.setDescrizione(descrizione.get(i));
                        nuovoTodo.setPosizione(posizione.get(i));
                        nuovoTodo.setImmagine(immagine.get(i));
                        if (Boolean.TRUE.equals(stato.get(i))){
                            nuovoTodo.completato();
                        }
                        ArrayList<String> listaUtentiCondivisi = mappaUtentiCondivisi.get(id.get(i));
                        if (listaUtentiCondivisi != null) {
                            nuovoTodo.utenti.addAll(listaUtentiCondivisi);
                        }
                    }
                }
            }
        } catch (SQLException e ) {
            System.out.println("Errore nel caricamento Todo dal database. Errore: " + e.getMessage());
        }
    }

    //Ricerca e filtri

    /**
     * Popola la tabella di ricerca con tutti i ToDo che contengono
     * il testo specificato nel titolo.
     *
     * @param titolo stringa da cercare nei titoli dei ToDo
     */
    public void popolaRicerca(String titolo) {
        if (titolo.isEmpty()) return;
        textRicerca =  titolo; // così posso fare refresh della tabella in base all'ultima ricerca effettuata
        modelloTableRicerca = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Impedisce la modifica di tutte le celle
            }};
        modelloTableRicerca.setColumnIdentifiers(headerTabellaRicercaEScadenza);
        for (Bacheca b : utente.bacheche) {
            for (ToDo t : b.todos){
                if (t.getTitolo().contains(titolo)) {
                    modelloTableRicerca.addRow(new String[]{t.getStato(), t.getTitolo(), t.getScadenza().toString(), t.getBacheca()});
                }
            }
        }
        this.tabellaRicerca.setModel(modelloTableRicerca);
    }

    /**
     * Popola la tabella con i ToDo in scadenza alla data specificata.
     *
     * @param data data da confrontare
     * @param oggi {@code true} se si tratta della data odierna, {@code false} se si desiderano anche quelle passate
     */
    public void ricercaScadenze(LocalDate data, boolean oggi) {
        ultimaDataRicerca = data; // per fare refresh della tabella con l'ultima data inserita (parte la prima volta con il now)
        modelloTableScadenza = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Impedisce la modifica di tutte le celle
            }};
        modelloTableScadenza.setColumnIdentifiers(headerTabellaRicercaEScadenza);
        for (Bacheca b : utente.bacheche) {
            for (ToDo t : b.todos) {
                if (oggi){
                    if (t.getScadenza().isEqual(data)) { // aggiunge date uguali a quella data (si presuppone sia oggi)
                        modelloTableScadenza.addRow(new String[]{t.getStato(), t.getTitolo(), t.getScadenza().toString(), t.getBacheca()});
                    }
                }
                else {
                    if (t.getScadenza().isBefore(data) || t.getScadenza().isEqual(data) ) { // aggiunge date uguali o minori a quella data
                        modelloTableScadenza.addRow(new String[]{t.getStato(), t.getTitolo(), t.getScadenza().toString(), t.getBacheca()});
                    }
                }
            }
        }
        tabellaScadenza.setModel(modelloTableScadenza);


    }

    /**
     * Restituisce il {@link modello.ToDo} selezionato nella tabella specificata,
     * confrontando il titolo con quelli presenti nelle bacheche dell’utente.
     *
     * @param tabella oggetto {@code JTable} da cui leggere la selezione
     * @return il ToDo corrispondente alla riga selezionata, oppure {@code null} se non trovato
     */
    public ToDo trovaTodoDaTabella(JTable tabella) {
        int rigaSelezionata = tabella.getSelectedRow();
        String titoloTodo = tabella.getValueAt(rigaSelezionata, 1).toString();
        for (Bacheca b: utente.bacheche) {
            for (ToDo t : b.todos){
                if (titoloTodo.equals(t.getTitolo())){
                    return t;
                }
            }
        }
        return null;
    } // a partire da un elemento selezionato in tabella trova il todos corrispondente


    //Utility grafiche e metodi di supporto

    /**
     * Aggiorna e popola tutte le tabelle della GUI (Università, Lavoro, Tempo Libero, scadenza, ricerca).
     */
    public void refreshTabelle() {
        this.modelloTableUni = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Impedisce la modifica di tutte le celle
            }};
        this.modelloTableUni.setColumnIdentifiers(headerTabellaTodo);
        this.modelloTableLavoro = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Impedisce la modifica di tutte le celle
            }};
        this.modelloTableLavoro.setColumnIdentifiers(headerTabellaTodo);
        this.modelloTableTempoLibero = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Impedisce la modifica di tutte le celle
            }};
        this.modelloTableTempoLibero.setColumnIdentifiers(headerTabellaTodo);
        // popola tabelle
        for (Bacheca b : this.utente.bacheche) {
            TitoloBacheca titoloBachecaCostante = TitoloBacheca.convertiDaString(b.getTitolo());
            if (titoloBachecaCostante == null) continue; //vai avanti col for each
            for (ToDo t : b.todos) {
                String[] riga = new String[]{ t.getStato(), t.getTitolo(), t.getScadenza().toString()};
                switch (titoloBachecaCostante) {
                    case UNIVERSITA -> this.modelloTableUni.addRow(riga);
                    case LAVORO -> this.modelloTableLavoro.addRow(riga);
                    case TEMPO_LIBERO -> this.modelloTableTempoLibero.addRow(riga);
                }
            }
        }
        this.tabellaUni.setModel(this.modelloTableUni);
        this.tabellaLavoro.setModel(this.modelloTableLavoro);
        this.tabellaTempoLibero.setModel(this.modelloTableTempoLibero);

        popolaRicerca(textRicerca); //aggiorno tabella di ricerca
        ricercaScadenze(ultimaDataRicerca, ultimaDataRicerca.isEqual(LocalDate.now())); //aggiorno tabella di scadenze OGGI se la data è uguale a oggi
    }

    /**
     * Restituisce la descrizione di ciascuna bacheca associata all'utente connesso.
     *
     * @return array di descrizioni (Università, Lavoro, Tempo Libero)
     */
    public String[] getDescrizioniTabelle(){
        String[] descrizioniTabelle = new String[3];
        for (Bacheca b : utente.bacheche) {
            TitoloBacheca titoloBachecaCostante = TitoloBacheca.convertiDaString(b.getTitolo());
            if (titoloBachecaCostante == null) continue;
            switch (titoloBachecaCostante) {
                case UNIVERSITA -> descrizioniTabelle[0] = b.getDescrizione();
                case LAVORO -> descrizioniTabelle[1] = b.getDescrizione();
                case TEMPO_LIBERO -> descrizioniTabelle[2] = b.getDescrizione();
            }
        }
        return descrizioniTabelle;
    }

    /**
     * Restituisce un array booleano che indica quali delle tre bacheche standard sono presenti tra le bacheche dell'utente.
     *
     * @return array di 3 valori booleani: [Università, Lavoro, Tempo Libero]
     */
    public boolean[] getListBacheche(){

        boolean[] listBacheche = new boolean[3]; // di default sono tutti false alla creazione
        for (Bacheca b : utente.bacheche) {
            TitoloBacheca titoloBachecaCostante = TitoloBacheca.convertiDaString(b.getTitolo());
            if (titoloBachecaCostante == null) continue;
            switch (titoloBachecaCostante) {
                case UNIVERSITA -> listBacheche[0] = true;
                case LAVORO -> listBacheche[1] = true;
                case TEMPO_LIBERO -> listBacheche[2] = true;
            }
        }
        return listBacheche;
    }

    /**
     * Converte un array di byte in un oggetto {@code ImageIcon} visualizzabile.
     *
     * @param datiImmagine array di byte che rappresenta un'immagine
     * @return immagine convertita come {@code ImageIcon}
     */
    public ImageIcon convertiInImmagineDaBytes(byte[] datiImmagine) {
        ByteArrayInputStream bais = new ByteArrayInputStream(datiImmagine);
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(bais);
        } catch (IOException e) {
            System.out.println("Errore nella conversione in immagine" + e.getMessage());
        }
        assert bufferedImage != null;
        return new ImageIcon(bufferedImage);
    }

    /**
     * Converte un file immagine selezionato in un array di byte per il salvataggio.
     *
     * @param fileScelto file immagine selezionato
     * @param principale pannello per eventuali messaggi di errore
     * @return byte[] che rappresenta l'immagine, oppure {@code null} in caso di errore
     */
    public byte[] convertiImmagineCaricataInByte(File fileScelto, JPanel principale){
        try {
            BufferedImage img = ImageIO.read(fileScelto);

            // Converti l'immagine in byte[]
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(img, "jpg", baos); // ocio al formato
            byte[] result =  baos.toByteArray();
            JOptionPane.showMessageDialog(principale, "Immagine caricata con successo!");
            return result;
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(principale, "Errore durante il salvataggio: " + ex.getMessage());
            return null; // mi serve il null e non l'array vuoto, per una migliore gestione nel db
        }
    }

}



