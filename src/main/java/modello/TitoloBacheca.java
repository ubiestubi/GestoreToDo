package modello;

/**
 * Enum che rappresenta i possibili titoli predefiniti per una {@link Bacheca}.
 * Ogni costante ha un'etichetta leggibile in formato stringa, usata ad esempio per visualizzazione o confronto.
 */
public enum TitoloBacheca {
    /**
     * Titolo bacheca Università.
     */
    UNIVERSITA("Università"),
    /**
     * Titolo bacheca Lavoro.
     */
    LAVORO("Lavoro"),
    /**
     * Titolo bacheca Tempo libero.
     */
    TEMPO_LIBERO("Tempo Libero");

    private final String titoloVero;

    TitoloBacheca(String titolo) {
        this.titoloVero  = titolo;
    }

    /**
     * Restituisce il titolo leggibile associato alla costante.
     *
     * @return stringa corrispondente al titolo
     */
    public String mostraTitoloVero() { return titoloVero; }

    /**
     * Converte una stringa in una costante di {@code TitoloBacheca}, se compatibile.
     * Il confronto è case-insensitive.
     *
     * @param titolo stringa da convertire
     * @return la costante corrispondente, oppure {@code null} se non valida
     */
    public static TitoloBacheca convertiDaString(String titolo) { //per convertire la stringa in TitoloBacheca
        for (TitoloBacheca t : TitoloBacheca.values()) {
            if (t.mostraTitoloVero().equalsIgnoreCase(titolo)) {
                return t;
            }
        }
        return null;
    }

}
