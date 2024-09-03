/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.restaurant.observer;

import com.restaurant.model.Reservation;
import com.restaurant.model.User;

/**
 *
 * @author hp
 */
// Observer.java
public interface Observer {
    void update(User user);

    public void update(Reservation reservation);
}





