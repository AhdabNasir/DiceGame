/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diceroll.ui;

import diceroll.controller.GameController;
import diceroll.core.PlayerType;
import diceroll.core.Score;
import diceroll.ui.listner.ThrowListener;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import sun.util.logging.PlatformLogger;

/**
 *
 * @author ahdabnasir
 */
public class GameWindow {
    
    private static Score getScore() throws IOException {
        ObjectInputStream ois = null;
        
        try {
            FileInputStream fin = new FileInputStream("score.dat");
            ois = new ObjectInputStream(fin);
            Score data = null;
            
            data = (Score) ois.readObject();
            
            if(data == null){
                ois.close();
                return score;
            }
            return data;
        } catch (Exception e) {
            return score;
        } finally {
            if (ois != null) {
                ois.close();
            }
        }
        
    }
    
    private static void writeData() throws IOException {
        FileOutputStream fout = new FileOutputStream("score.dat");
        ObjectOutputStream oos = new ObjectOutputStream(fout);
        
        oos.writeObject(score);
        oos.close();
    }
    
    public GameWindow() throws IOException {
        init();
    }
    
    public static GameController controller = new GameController();
    public static Score score = new Score();
    public static JTable scoreTable;
    public static JTable scoreHistoryTable;
    
    private static void init() throws IOException {
        score = getScore();
        System.out.println(score);
        final JFrame frame = new JFrame("Dice Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        final JPanel gui = new JPanel(new BorderLayout(15, 15));
        gui.setBorder(new TitledBorder("Game Details"));
        
        JPanel gameDetails = new JPanel(new BorderLayout(4, 4));
        final JButton newGame = new JButton("New Game");
        gameDetails.add(newGame, BorderLayout.WEST);
        
        final JButton chMaxScore = new JButton("Change Max Score");
        gameDetails.add(chMaxScore, BorderLayout.EAST);
        chMaxScore.setEnabled(!controller.isGameGoingOn());
        newGame.setEnabled(!controller.isGameGoingOn());
        newGame.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) {
                try {
                    writeData();
                }
                catch (IOException ex) {
                    Logger.getLogger(GameWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
                controller = new GameController();
                frame.dispose();
            try { 
            init();           
        } catch (IOException ex) {
            Logger.getLogger(GameWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        }
    });
        
     chMaxScore.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
             Boolean didEnter = Boolean.FALSE;
             while (!didEnter) {
                 String score = (String) JOptionPane.showInputDialog(frame, "Please enter new Max Score", "Max Score",
                         JOptionPane.QUESTION_MESSAGE, null, null, controller.getMaxScore());
                 System.out.println(score);
                 try {
                     if (score == null) {
                         didEnter = Boolean.TRUE;
                     }
                     Integer scoreInt = Integer.parseInt(score);
                     if (scoreInt > 50) {
                         controller.setMaxScore(scoreInt);
                         didEnter = Boolean.TRUE;
                     } else {
                         JOptionPane.showMessageDialog(frame, "ENTER SCORE ABOVE 50", "Click OK", 
                                 JOptionPane.ERROR_MESSAGE);
                     }
                 } catch (NumberFormatException exception) {
                   
                 }
             }
         }
     });  
     
     JPanel scoreHistoryPanel = new JPanel(new BorderLayout(4, 4));
     
     gameDetails.add(scoreHistoryPanel, BorderLayout.NORTH);
     
     scoreHistoryTable = new JTable(new DefaultTableModel(getScoreData(), getScoreColumnNames()));
     scoreHistoryPanel.add(scoreHistoryTable);
     
     gui.add(gameDetails, BorderLayout.NORTH);
     
     JPanel gamePanel = new JPanel(new BorderLayout(4, 4));
     gamePanel.setBorder(new TitledBorder("Current Game Details"));
     
     JPanel gameControls = new JPanel(new BorderLayout(4, 4));
     gamePanel.add(gameControls, BorderLayout.WEST);
     
     final JButton throwDice = new JButton("Throw");
     gameControls.add(throwDice, BorderLayout.NORTH);
     JPanel scorePanel = new JPanel(new BorderLayout(4, 4));
     scoreTable = new JTable(new DefaultTableModel(getRowData(), getColumnNames()));
     scorePanel.add(scoreTable, BorderLayout.CENTER);
     
     gamePanel.add(scorePanel, BorderLayout.CENTER);
     gui.add(gamePanel, BorderLayout.CENTER);
     
     throwDice.addActionListener(new ThrowListener(frame, scoreTable, throwDice));
     
     throwDice.addPropertyChangeListener("enabled", new PropertyChangeListener() {
         
         @Override
         public void propertyChange(PropertyChangeEvent evt) {
             System.out.println(evt.getNewValue().toString());
              if (evt.getNewValue().toString().equalsIgnoreCase("false")) {
                  if (controller.getHuman().getScore() > controller.getMaxScore() || controller.getComputer()
                          .getScore() > controller.getMaxScore()) {
                      if(controller.getHuman().getScore() > controller.getComputer().getScore()) {
                          JOptionPane.showMessageDialog(frame, "YOU WIN", "Click Ok", 
                                  JOptionPane.INFORMATION_MESSAGE);
                          updateScore(PlayerType.HUMAN);
                      } else if (controller.getHuman().getScore() < controller.getComputer().getScore()) {
                          JOptionPane.showMessageDialog(frame, "YOU LOSE", "Click Ok", 
                                  JOptionPane.INFORMATION_MESSAGE);
                          updateScore(PlayerType.COMPUTER);
                      } else {
                          JOptionPane.showOptionDialog(frame, "Same Score, Throw Again" , "Same score", JOptionPane.OK_OPTION, 
                                  JOptionPane.INFORMATION_MESSAGE, null, new String[]{"OK"}, 0);
                          SwingUtilities.invokeLater(new Runnable() {
                              @Override
                              public void run() {
                                  throwDice.setEnabled(true);
                              }
                          });
                      }
                  }
                  System.out.println(score);
              }
         }
         
         
          private void updateScore(PlayerType playerType) {
              switch(playerType) {
                  case HUMAN: {
                      score = new Score(score.getComputer(), score.getUser() + 1);
                      scoreHistoryTable = new JTable(new DefaultTableModel(getScoreData(), getScoreColumnNames()));
                      return;
                  }
                  case COMPUTER: {
                      score = new Score(score.getComputer() + 1, score.getUser());
                      scoreHistoryTable = new JTable(new DefaultTableModel(getScoreData(), getScoreColumnNames()));
                  }
              }
              scoreHistoryTable.revalidate();
          }     
     });
     
     frame.setContentPane(gui);
     frame.setMinimumSize(new Dimension(500, 400));
     frame.addWindowListener(new WindowAdapter() {
         @Override 
         public void windowClosing(WindowEvent e) {
             try {
                 writeData();
             } catch (IOException ex) {
                 Logger.getLogger(GameWindow.class.getName()).log(Level.SEVERE, null, ex);
             }
         }
     });
     
     frame.pack();
     frame.setLocationByPlatform(true);
     frame.setVisible(true); 
}
    
    public static String[][] getRowData() {
        String[][] rows = new String[controller.getHuman().getAttemptScoreMap().keySet().size() + 2][2];
        rows[0] = new String[]{"Attempts", "Human", "Computer"};
        for (int i = 0; i < controller.getHuman().getAttemptScoreMap().keySet().size(); i++) {
            rows[i + 1] = new String[]{ (i + 1) + "", controller.getHuman().getAttemptScoreMap().get(i + 1) + "",
                controller.getComputer().getAttemptScoreMap().get(i + 1) + ""};
            }
        rows[controller.getHuman().getAttemptScoreMap().keySet().size() + 1] = new String[]{"Total",
            controller.getHuman().getScore() + "", controller.getComputer().getScore() + ""};
        return rows;
        }
    
    public static Object[] getColumnNames() {
        return new String[]{"Attempts", "User", "Computer"};  
    }
    
    public static String[][] getScoreData(){
        String[][] rows = new String[controller.getHuman().getAttemptScoreMap().keySet().size() + 2][2];
        rows[0] = new String[]{"User", "Computer"};
        rows[1] = new String[]{score.getUser() + "", score.getComputer() + ""};
        return rows;
    }
    
    public static Object[] getScoreColumnNames() {
        return new String[]{"User", "Computer"};  
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    init();
                } catch (IOException ex) {
                    Logger.getLogger(GameWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }  
}