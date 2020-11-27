package com.csd.huelight.Util;

import java.util.ArrayList;
import java.util.List;

public abstract class Observable {
    private List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }

    public void RemoveObserver(Observer observer) {
        this.observers.remove(observer);
    }

    protected void notifyObservers() {
        for (Observer observer : this.observers) {
            observer.update(this);
        }
    }
}
