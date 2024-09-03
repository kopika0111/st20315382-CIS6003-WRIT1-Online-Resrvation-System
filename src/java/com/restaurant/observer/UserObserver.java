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
public class UserObserver implements Observer {
    @Override
    public void update(User user) {
        // Notify changes
        System.out.println("User " + user.getUsername() + " has been updated.");
    }

    @Override
    public void update(Reservation reservation) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
