package modello;
/**
 * Enum che rappresenta lo stato di completamento di un'attività {@link ToDo}.
 * Può essere:
 * <ul>
 *   <li>{@link #COMPLETATO} – l'attività è stata completata.</li>
 *   <li>{@link #NON_COMPLETATO} – l'attività è ancora da svolgere.</li>
 * </ul>
 */
public enum StatoToDo {
    COMPLETATO,
    NON_COMPLETATO
}
