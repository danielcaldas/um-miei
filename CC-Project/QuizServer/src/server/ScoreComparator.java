/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.util.Comparator;
import middleware.User;

/**
 *
 * @author carlosmorais
 */
class ScoreComparator implements Comparator <User> {
    @Override
    public int compare(User u1, User u2) {
      return u2.getIntegerScore()-u1.getIntegerScore();
    }
  }