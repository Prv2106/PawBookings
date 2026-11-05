package domain_layer;

import java.util.ArrayList;
import java.util.List;

/**
 * Minimal implementation of the Observer pattern.
 * Subclasses maintain a list of observers and notify them of state changes.
 */
public class Observable {
    protected List<Observer> observers = new ArrayList<>();

    /**
     * Adds an observer.
     *
     * @param observer observer to add
     */
    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }

    /**
     * Removes an observer.
     *
     * @param observer observer to remove
     */
    public void deleteObserver(Observer observer) {
        this.observers.remove(observer);
    }

    /**
     * Notifies every registered observer of a state change.
     */
    public void notifyObservers() {
        for (Observer observer: this.observers) {
            observer.update(this);
        }
    }

}
