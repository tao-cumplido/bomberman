package de.hsh.project.bomberman.game.highscore;

import de.hsh.project.bomberman.game.Game;
import de.hsh.project.bomberman.game.credits.CreditsState;
import de.hsh.project.bomberman.game.menu.MenuState;
import de.hsh.project.bomberman.game.menu.TitleState;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


/**
 * Created by taocu on 26.10.2015.
 */

public class HighScoreMenuState extends MenuState {


    public HighScoreMenuState(){
        super();
        this.setLayout (new BorderLayout());


        EnterNameState hm = new EnterNameState();
        hm.addScore("Juan",7587587,"Easy",4,5);
        hm.addScore("San",325252434,"Hard",5,7);


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

        JButton menu = new JButton("<<");
        menu.setFont(new Font("",Font.BOLD,32));
        p2.add(menu);
        setMenuBut(menu);


    }

    public void show(JPanel p,EnterNameState h){
        ArrayList<HighScore> pun = h.getScores();

        for(int i=0;i<10;i++){

            JLabel name= new JLabel();
            name.setHorizontalAlignment(JLabel.CENTER);
            JLabel score = new JLabel();
            score.setHorizontalAlignment(JLabel.CENTER);

            if(i<pun.size()){
                if(i==0){
                    name.setFont(new Font("",Font.BOLD,24));
                    score.setFont(new Font("",Font.BOLD,24));
                }
                name.setText(i+1+".            "+pun.get(i).getNam());
                name.setToolTipText("Level: "+pun.get(i).getLevel()+" Lives: "+pun.get(i).getLives()+" Time: "+pun.get(i).getTime());
                score.setText(Integer.toString(pun.get(i).getScore()));
                score.setToolTipText("Level: " + pun.get(i).getLevel() + " Lives: " + pun.get(i).getLives() + " Time: " + pun.get(i).getTime());
            }

            p.add(name);
            p.add(score);
        }
        System.out.println(pun.size());
    }

    protected void setMenuBut(JButton menuBut) {
        menuBut.addActionListener((event) -> Game.switchState(new TitleState()));
    }
}