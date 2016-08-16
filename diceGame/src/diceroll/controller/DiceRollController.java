/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diceroll.controller;

import diceroll.core.Dice;
import diceroll.core.Die;
import diceroll.core.PlayerType;
import java.util.ArrayList;
import diceroll.ui.GameWindow;
import javax.swing.JDialog;

/**
 *
 * @author ahdabnasir
 */
public class DiceRollController {
    
    private JDialog game;
    private final Dice dice;
    private int count = 3;
    private Boolean reRollGame = Boolean.TRUE;
    
    
    public DiceRollController() {
        this.dice = Dice.getInstance();
    }
    
    public DiceRollController(int count) {
        this.dice = Dice.getInstance();
        this.reRollGame = Boolean.FALSE;
    }
    
    public int getCount() {
        return this.count;
    }
    
    public void roll(PlayerType playerType) {
        Die[] rolled = null;
          if (this.count != 3 && reRollGame) {
              if (this.game != null) 
                  this.game.dispose();
          }
          
          if (this.count == 3 || !reRollGame) {
              rolled = this.dice.roll();
              GameWindow.controller.getPlayer(playerType).setAttempt(GameWindow.controller.getPlayer
              (playerType).getAttempt()+ 1);
          } else {
              rolled = this.dice.roll(GameWindow.controller.getPlayer(playerType).getKeeping());
          }
          count--;
          
          GameWindow.controller.getPlayer(playerType).addScore(GameWindow.controller.getPlayer(playerType).getAttempt(), score(rolled), rolled);
          GameWindow.controller.getPlayer(playerType).setKeeping(new ArrayList<Die>());
    }
    
    private int score(Die[] rolled) {
        int score = 0;
        for (int i = 0; i <rolled.length; i++) {
            score += rolled[i].getValue();
        }
        return score;
    }
    
    public void setGame(JDialog game) {
        this.game = game;
    }
    
    public Dice getDice() {
        return dice;
    }
    
    public void setCount(int count) {
        this.count = count;
    }
    
    public JDialog getGame() {
        return this.game;
    }
    
    
    
}
