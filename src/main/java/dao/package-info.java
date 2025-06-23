/**
 * Questo package contiene tutte le interfacce DAO (Data Access Object)
 * per la gestione dell'accesso ai dati dell'applicazione Gestore ToDo.
 *
 * <p>Le interfacce definite in questo package stabiliscono i contratti
 * per le operazioni di lettura, scrittura, aggiornamento e cancellazione
 * di entità come {@link modello.ToDo}, {@link modello.Bacheca} e {@link modello.Utente}.</p>
 *
 * <p>Ogni interfaccia è pensata per essere implementata da una specifica classe,
 * che si occupa della logica concreta con tecnologie come JDBC.</p>
 *
 * <p>Responsabilità principali del package:
 * <ul>
 *   <li>Autenticazione e registrazione utente</li>
 *   <li>Gestione delle bacheche e delle attività</li>
 *   <li>Persistenza delle modifiche al logout</li>
 *   <li>Condivisione e rimozione dei ToDo</li>
 * </ul>
 */
package dao;
