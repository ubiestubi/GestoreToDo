/**
 * Questo package contiene la logica di controllo dell'applicazione Gestore ToDo,
 * fungendo da intermediario tra il modello e l'interfaccia utente (GUI).
 *
 * <p>Include la classe {@link controller.Controller}, che coordina tutte le operazioni
 * dell'utente: accesso, registrazione, creazione e modifica di ToDo e bacheche,
 * gestione della condivisione, aggiornamento dinamico delle tabelle Swing e
 * interazione con i DAO per la persistenza dei dati nel database.</p>
 *
 * <p>Responsabilità principali del package:
 * <ul>
 *   <li>Gestione della sessione utente (login/logout)</li>
 *   <li>Sincronizzazione tra il modello dati e le componenti grafiche Swing</li>
 *   <li>Accesso ai servizi DAO per lettura/scrittura dati</li>
 *   <li>Filtri, ricerche, e operazioni di visualizzazione avanzata</li>
 * </ul>
 */
package controller;
