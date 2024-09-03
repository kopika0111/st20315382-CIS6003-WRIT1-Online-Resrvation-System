/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.restaurant.observer;

/**
 *
 * @author hp
 */
import com.restaurant.model.Reservation;
import java.util.ArrayList;
import java.util.List;

public class ReservationObservable {
    private List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void notifyObservers(Reservation reservation) {
        for (Observer observer : observers) {
            observer.update(reservation);
        }
    }
}

