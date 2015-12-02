package de.hsh.project.bomberman.game.highscore;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by taocu on 26.10.2015.
 */
public class EnterNameState extends JPanel {
    public static void main(String[] args) {
        EnterNameState m = new EnterNameState();

    }

    public EnterNameState(){

        this.setLayout (new BorderLayout());
        this.setSize(800, 500);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        HighScoreMenuState hm = new HighScoreMenuState();
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



        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //v.pack();
        this.setVisible(true);
    }

    public void show(JPanel p,HighScoreMenuState h){
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