/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diceroll.core;

import diceroll.controller.DiceRollController;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

/**
 *
 * @author ahdabnasir
 */
public class Player {
    
    private final PlayerType type;
    private Map<Integer, Integer> attemptScoreMap = new TreeMap<Integer, Integer>();
    private int attempt= 0;
    private Die[] lastRolled;
    private List<Die> keeping = new ArrayList<Die>();
    private DiceRollController currentGame;

    
    public int getAttempt() {
        return attempt;
    }
    
    public void setAttempt(int attempt) {
        this.attempt= attempt;
    }
    
    public PlayerType getType() {
        return type;
    }
    
    public Map <Integer, Integer> getAttemptScoreMap() {
        return attemptScoreMap;
    }
    
    public Player(PlayerType playerType) {
        this.type = playerType;
        currentGame = new DiceRollController();   
    }
    
    public List <Die> getKeeping() {
        return keeping;
    }
    
    public void setKeeping(List<Die> keeping) {
        this.keeping = keeping;
    }
    
    public void addScore(int attempt, int score, Die[] rolled) {
        attemptScoreMap.put(attempt, score);
        lastRolled = rolled;
    }
    
    public Die[] getLastRolled() {
        return lastRolled;
    }
    
    public int getScore() {
        int score= 0;
        for (Integer i : attemptScoreMap.values()) {
            score += i;
        }
        return score;
    }
    
    public DiceRollController getController() {
        return this.currentGame;
    }
    
    public void roll() {
        this.currentGame.roll(type);
    }
    
    public void setCurrentGame(DiceRollController diceRollController) {
        this.currentGame = diceRollController;
    }
    
    public Boolean reRoll() {
        if(PlayerType.COMPUTER != this.type) {
            throw new UnsupportedOperationException("Not supported Humans and other beings");
        } else if (currentGame.getCount()==3) {
        return Boolean.TRUE;
    } else {
            Random r = new Random();
            return r.nextBoolean();
        }
    }
    
    public void selectKeeping() {
       if(PlayerType.COMPUTER != this.type) {
            throw new UnsupportedOperationException("Not supported Humans and other beings");
        } else {
           Random r = new Random();
            for (int i = 0; i < lastRolled.length; i++) {
                if (r.nextBoolean())
                {
                   keeping.add(lastRolled[i]);
                }}
       } 
    }
    
    
    
}
