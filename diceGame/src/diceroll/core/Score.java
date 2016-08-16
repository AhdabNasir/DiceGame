/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diceroll.core;

import java.io.Serializable;

/**
 *
 * @author ahdabnasir
 */
public class Score implements Serializable {
    
    private static final long serialVersionID = 23453424;
    private int computer;
    private int user;
    
    public Score(int computer, int user) {
        this.computer = computer;
         this.user = user;      
    }
    
    public Score() {      
    }
    
    public int getComputer() {
        return computer;
    }
    
    public void setComputer(int computer) {
        this.computer = computer;
    }
    
    public int getUser() {
        return user;
    }
    
    public void setUser(int user) {
        this.user = user;
    }
    
    public String toString() {
        return "User Score = " + user +". Computer Score = " +computer + ".";
    }
    
    
}
