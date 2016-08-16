/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diceroll.controller;

import diceroll.core.*;

/**
 *
 * @author ahdabnasir
 */
public class GameController {
    
    private Player human;
    private Player computer;
    private int maxScore = 100;
    
    public GameController() {
        human = new Player(PlayerType.HUMAN);
        computer = new Player(PlayerType.COMPUTER);
    }
    
    public Player getHuman() {
        return human;
    }
    
    public void setHuman(Player human) {
        this.human = human;
    }
    
    public Player getComputer(){
        return computer;
    }
    
    public void setComputer(Player computer) {
        this.computer = computer;
    }
    
    public Player getPlayer (PlayerType playerType) {
      switch (playerType) {
        case COMPUTER:
            return computer;
        case HUMAN:
            return human;
    }
        return null;
    }
    
    public Boolean isGameGoingOn() {
        if (computer.getAttempt() > 0 || human.getAttempt() > 0){
            return true;
        }
        return false;
    }
    
    public void setNewGame() {
        human.setCurrentGame(new DiceRollController());
        computer.setCurrentGame(new DiceRollController());
    }
    
    public void setNewZeroReRollGame() {
        human.setCurrentGame(new DiceRollController(1));
        computer.setCurrentGame(new DiceRollController(1));
    }
    
    public boolean checkScore() {
        if (human.getScore() > maxScore || computer.getScore() > maxScore) {
            return Boolean.FALSE;
        }
        else {
            return Boolean.TRUE;
        }
     }
    
    public int getMaxScore() {
        return maxScore;
    }
    
    public void setMaxScore(Integer scoreInt) {
        this.maxScore = scoreInt;
    }
    
}
