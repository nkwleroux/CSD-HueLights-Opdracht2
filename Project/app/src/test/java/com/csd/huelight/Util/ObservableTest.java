package com.csd.huelight.Util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ObservableTest {
    @Test
    void addObserverTest() {
        //arrange
        Observable observable = new ObservableMock();

        //act assert
        assertTrue(observable.addObserver(new ObserverMock()));
    }

    @Test
    void removeObserverTest() {
        //arrange
        Observable observable = new ObservableMock();
        Observer observer1 = new ObserverMock();
        Observer observer2 = new ObserverMock();

        //act assert
        assertFalse(observable.removeObserver(observer1));
        assertFalse(observable.removeObserver(observer2));

        observable.addObserver(observer1);

        assertFalse(observable.removeObserver(observer2));
        assertTrue(observable.removeObserver(observer1));
    }

    @Test
    void notiyObserversTest() {
        //arrange
        Observable observable = new ObservableMock();
        ObserverMock observerMock = new ObserverMock();

        //act
        observable.addObserver(observerMock);

        observable.notifyObservers();

        assertTrue(observerMock.updated);
    }
}


class ObservableMock extends Observable {
}

class ObserverMock implements Observer {
    boolean updated = false;

    @Override
    public void update(Observable source) {
        updated = true;
    }

}