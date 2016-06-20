/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.util.Comparator;
import middleware.User;
import middleware.UserGame;

/**
 *
 * @author carlosmorais
 */
public class ScoreGameComparator implements Comparator <UserGame> {
    @Override
    public int compare(UserGame u1, UserGame u2) {
      return u1.getIntegerPoits().compareTo(u2.getIntegerPoits());
    }
  }