/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diceroll.core;

import javax.swing.ImageIcon;

/**
 *
 * @author ahdabnasir
 */
public class Die implements DieIntf, Comparable<Die> {
    
    private ImageIcon icon;
    private int val;
    
    public Die(int val, ImageIcon icon) {
        this.val = val;
        this.icon = icon;
    }
    
    @Override 
    public ImageIcon getImage(){
        return icon;
    }
    
    @Override 
    public void setImage(ImageIcon icon) {
        this.icon = icon;
    }
    
    @Override 
    public void setValue(int val) {
        this.val = val;
    }
    
    @Override 
    public int getValue() {
        return val;
    }
    
    @Override
    public int compareTo(Die o){
        
        if (o == null)
              throw new NullPointerException();
        if (this.val == o.getValue())
            return 0;
        return this.val > o.getValue() ? 1 : -1; 
    }
    
    @Override
    public String toString() {
        return "Die " + this.val;
    }
    
    @Override
     public boolean equals(Object obj) {
         if (obj == null) {
             return false;
         }
         if (getClass() != obj.getClass()) {
             return false;
         }
         final Die other = (Die) obj;
         if (this.val != other.val) {
             return false;
         }
         return true;
     }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 61 * hash + this.val;
        return hash;
    }
    
}
