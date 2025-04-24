package Modello;

public enum TitoloBacheca {
    UNIVERSITA("Università"),
    LAVORO("Lavoro"),
    TEMPO_LIBERO("Tempo Libero");

    private String titoloVero;

    TitoloBacheca(String titolo) {
        this.titoloVero  = titolo;
    }

    public String mostraTitoloVero() { return titoloVero; }

    public static TitoloBacheca convertiDaString(String titolo) { //per convertire la stringa in TitoloBacheca
        for (TitoloBacheca t : TitoloBacheca.values()) {
            if (t.mostraTitoloVero().equalsIgnoreCase(titolo)) {
                return t;
            }
        }
        return null;
    }

}
