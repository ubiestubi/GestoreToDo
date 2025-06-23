/**
 * Questo package contiene le classi grafiche dell'applicazione Gestore ToDo,
 * costruite con la libreria Swing.
 *
 * <p>Le principali interfacce includono:</p>
 * <ul>
 *   <li>{@link gui.Login} – Gestisce l'autenticazione e la registrazione dell’utente</li>
 *   <li>{@link gui.Home} – Finestra principale con tutte le funzionalità per la gestione dei ToDo</li>
 *   <li>{@link gui.Dettagli} – Permette di visualizzare e modificare i dettagli di un ToDo selezionato, oltre che eliminarlo</li>
 *   <li>{@link gui.VisualizzaUtenti} – Gestione della condivisione di un ToDo con altri utenti</li>
 * </ul>
 *
 * <p>Ogni classe comunica con il {@link controller.Controller} per eseguire operazioni
 * sul modello, interagire con il database e aggiornare dinamicamente la GUI.</p>
 *
 * <p>Tutte le interfacce presentano un design coerente e sono divise logicamente
 * in pannelli (tab), tabelle e componenti di supporto.</p>
 */
package gui;
