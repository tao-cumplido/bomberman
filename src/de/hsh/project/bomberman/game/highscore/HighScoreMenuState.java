package de.hsh.project.bomberman.game.highscore;

import de.hsh.project.bomberman.game.menu.MenuState;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


/**
 * Created by taocu on 26.10.2015.
 */

public class HighScoreMenuState extends MenuState {
    public static void main(String[] args) {
        HighScoreMenuState m = new HighScoreMenuState();

    }

    public HighScoreMenuState(){

        this.setLayout (new BorderLayout());
        this.setSize(800, 500);


        EnterNameState hm = new EnterNameState();
        hm.addScore("Juan",7587587,"Easy",4,5);
        hm.addScore("Santiago",325252424,"Hard",5,7);


        JLabel p0 = new JLabel("Bomberman");
        //p0.setToolTipText("Bomba");

        JPanel p1 = new JPanel();
        JPanel p2 = new JPanel();

        this.add(p0,BorderLayout.NORTH);
        this.add(p1,BorderLayout.CENTER);
        p1.setLayout(new GridLayout(10,2));
        this.add(p2,BorderLayout.SOUTH);

        p0.setHorizontalAlignment(JLabel.CENTER);
        p0.setFont(new Font("",Font.ITALIC,32));
        show(p1,hm);




        //v.pack();
        this.setVisible(true);
    }

    public void show(JPanel p,EnterNameState h){
        ArrayList<HighScore> pun = h.getScores();

        for(int i=0;i<10;i++){

            JLabel name= new JLabel();
            name.setHorizontalAlignment(JLabel.CENTER);
            JLabel score = new JLabel();
            score.setHorizontalAlignment(JLabel.CENTER);

            if(i<pun.size()){
                name.setText(i+1+".            "+pun.get(i).getNam());
                name.setToolTipText("Level: "+pun.get(i).getLevel()+" Lives: "+pun.get(i).getLives()+" Time: "+pun.get(i).getTime());
                score.setText(Integer.toString(pun.get(i).getScore()));
                score.setToolTipText("Level: "+pun.get(i).getLevel()+" Lives: "+pun.get(i).getLives()+" Time: "+pun.get(i).getTime());
            }

            p.add(name);
            p.add(score);
        }
        System.out.println(pun.size());
    }

}