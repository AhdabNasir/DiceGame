/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diceroll.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.ImageIcon;

/**
 *
 * @author ahdabnasir
 */
public class Dice implements DiceIntf { 
    
    private final Die one = new Die(1, new ImageIcon("images/1.jpg"));
    private final Die two = new Die(2, new ImageIcon("images/2.jpg"));
    private final Die three = new Die(3, new ImageIcon("images/3.jpg"));
    private final Die four = new Die(4, new ImageIcon("images/4.jpg"));
    private final Die five = new Die(5, new ImageIcon("images/5.jpg"));
    private final Die six = new Die(6, new ImageIcon("images/6.jpg"));
    private static final Dice INSTANCE = new Dice();
    
    private Dice() {
    }
    
    @Override 
    public Die[] roll() {
        return roll(null);
    }
    
    public static Dice getInstance() {
        return INSTANCE;
    }
    
    private Die getDie(int num) {
        switch (num) {
            case 1:
            return one;
            case 2:
                return two;
            case 3: 
                return three;
            case 4:
                return four;
            case 5: 
                return five;
            case 6:
                return six;
        }
        return null;
    }
    
    public Die[] roll(List<Die> keeping) {
        
        List<Die> dies = new ArrayList<Die>();
        
        if (keeping != null) {
            for (Die die : keeping) {
            dies.add(getDie(die.getValue()));
        }
       }
        Random r = new Random();
        while (dies.size() < 5) {
            
            Die d = getDie(r.nextInt(7));
            if (d != null) {
                dies.add(d);
            }
        }
        
        Die[] dies2 = new Die[dies.size()];
        return dies.toArray(dies2);
        
    }
    
}
