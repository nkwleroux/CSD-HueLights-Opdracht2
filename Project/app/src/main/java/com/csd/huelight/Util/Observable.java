package com.csd.huelight.Util;

import java.util.ArrayList;
import java.util.List;

public abstract class Observable {
    private List<Observer> observers = new ArrayList<>();

    public boolean addObserver(Observer observer) {
        return this.observers.add(observer);
    }

    public boolean removeObserver(Observer observer) {
        return this.observers.remove(observer);
    }

    protected void notifyObservers() {
        for (Observer observer : this.observers) {
            observer.update(this);
        }
    }
}
