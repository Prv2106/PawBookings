package domain_layer;

/**
 * Contract for objects that observe an {@link Observable}.
 */
public interface Observer {
    /**
     * Receives a state-change notification.
     *
     * @param o observable that changed
     */
    public void update(Observable o);
}
