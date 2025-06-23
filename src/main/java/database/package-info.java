/**
 * Questo package fornisce la gestione della connessione al database MySQL
 * utilizzata da tutte le classi di implementazioni DAO dell'applicazione.
 *
 * <p>Contiene la classe singleton {@link database.ConnessioneDatabase},
 * responsabile della creazione e condivisione della connessione JDBC
 * tra i vari componenti dell’accesso ai dati.</p>
 *
 * <p>L’approccio centralizzato facilita la gestione della configurazione
 * e il controllo delle risorse, garantendo una connessione stabile e riutilizzabile.</p>
 */
package database;
