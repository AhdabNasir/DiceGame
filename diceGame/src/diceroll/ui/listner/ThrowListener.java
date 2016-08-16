/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diceroll.ui.listner;

import diceroll.core.Die;
import diceroll.core.PlayerType;
import diceroll.ui.GameWindow;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ahdabnasir
 */
public class ThrowListener implements ActionListener {
    
    private final JFrame frame;
    private final JTable scoreTable;
    private final JButton throwDice;
    
    public ThrowListener(JFrame frame, JTable scoreTable, JButton throwDice) {
        this.frame = frame;
        this.scoreTable = scoreTable;
        this.throwDice = throwDice;
    }
    
    @Override 
    public void actionPerformed(ActionEvent e) {
        Thread humanPlayer = new Thread(new Runnable() {
            @Override 
            public void run() {
                GameWindow.controller.getPlayer(PlayerType.HUMAN).roll();
                final JDialog game = new JDialog(frame, "current game", true);
                GameWindow.controller.getPlayer(PlayerType.HUMAN).getController().setGame(game);
                final JPanel diceResult = new JPanel(new GridLayout(1, 5));
                diceResult.setBorder(
                new TitledBorder("Current Result"));
               final JButton reRollBtn = new JButton("Reroll");
               reRollBtn.setEnabled((GameWindow.controller.getPlayer(PlayerType.HUMAN).getKeeping().size() < 5) 
                       && (GameWindow.controller.getPlayer
        (PlayerType.HUMAN).getController().getCount() > 0));
               
               reRollBtn.addActionListener(new ThrowListener(frame, scoreTable, throwDice));
               
               for (int i = 0; i< 5; i++) {
                   final JPanel diePanel = new JPanel(new GridLayout(2,1));
                   final JCheckBox checkBox = new JCheckBox();
                   checkBox.setText(GameWindow.controller.getPlayer(PlayerType.HUMAN).getLastRolled()[i].getValue() + "");
                   checkBox.addItemListener(new ItemListener() {
                       @Override
                       public void itemStateChanged(ItemEvent e) {
                           JCheckBox checked = (JCheckBox) e.getItem();
                           if (checked.isSelected()) {
                               GameWindow.controller.getPlayer(PlayerType.HUMAN).getKeeping().
                                       add(new Die(Integer.parseInt(checked.getText()), null));
                           } else {
                               GameWindow.controller.getPlayer(PlayerType.HUMAN).getKeeping().
                                       remove(new Die(Integer.parseInt(checked.getText()), null));
                           }
                           reRollBtn.setEnabled((GameWindow.controller.getPlayer(PlayerType.HUMAN).getKeeping().size() < 5) 
                       && (GameWindow.controller.getPlayer(PlayerType.HUMAN).getController().getCount() > 0));
                       }
                   });
                   
                   JLabel label = new JLabel(GameWindow.controller.getPlayer(PlayerType.HUMAN).getLastRolled()[i].getImage());
                   
                   diePanel.add(label, BorderLayout.NORTH);
                   diePanel.add(checkBox, BorderLayout.CENTER);
                   diceResult.add(diePanel);
               }
               
               final JPanel buttonPanel = new JPanel(new GridLayout(1, 5));
               final JButton scoreBtn = new JButton("Score");
               scoreBtn.addActionListener(new ActionListener() {
                   @Override
                   public void actionPerformed(ActionEvent e) {
                       game.dispose();
                       if (GameWindow.controller.getHuman().getScore() == GameWindow.controller.getComputer().getScore()) {
                           GameWindow.controller.setNewZeroReRollGame();
                       } else {
                           GameWindow.controller.setNewGame();
                       }
                       scoreTable.setModel(new DefaultTableModel(GameWindow.getRowData(), GameWindow.getColumnNames()));
                       throwDice.setEnabled(GameWindow.controller.checkScore());
                   }
               });
               
               buttonPanel.add(reRollBtn);
               buttonPanel.add(scoreBtn);
               game.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
               game.add(buttonPanel, BorderLayout.SOUTH);
               game.add(diceResult, BorderLayout.CENTER);
               game.pack();
               game.setLocationByPlatform(true);
               game.setLocationRelativeTo(frame);
               game.setVisible(true);   
            }
        });
        
        humanPlayer.start();
  
    
    Thread aiPlayer = new Thread(new Runnable() {
        @Override
        public void run() {
            Boolean reRoll = GameWindow.controller.getPlayer(PlayerType.COMPUTER).reRoll();
            while (reRoll && GameWindow.controller.getPlayer(PlayerType.COMPUTER).getController().getCount() > 0) {
                reRoll = GameWindow.controller.getPlayer(PlayerType.COMPUTER).reRoll();
                if (GameWindow.controller.getPlayer(PlayerType.COMPUTER).getController().getCount() == 3) {
                    GameWindow.controller.getPlayer(PlayerType.COMPUTER).roll();
                } else if (reRoll) {
                    GameWindow.controller.getPlayer(PlayerType.COMPUTER).selectKeeping();
                    GameWindow.controller.getPlayer(PlayerType.COMPUTER).roll();
                }
            }
        }
     });
    
    aiPlayer.start();
 }
 
}