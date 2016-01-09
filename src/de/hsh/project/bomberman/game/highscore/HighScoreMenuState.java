package de.hsh.project.bomberman.game.highscore;


import de.hsh.project.bomberman.game.menu.FontImage;
import de.hsh.project.bomberman.game.menu.MenuState;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


/**
 * Created by taocu on 26.10.2015.
 */
public class HighScoreMenuState extends MenuState {



    private FontImage back;

    public HighScoreMenuState(){
        super();


        FontImage highscore = new FontImage("highscore",7,false);
        back = new FontImage("back",4,true);

        EnterNameState hm = new EnterNameState();
        hm.addScore("Juan",7587587,"Easy",4,5);
        hm.addScore("San",325252434,"Hard",5,7);

        JPanel title = new JPanel();
        JPanel p1 = new JPanel();
        JPanel p2 = new JPanel();
        title.setOpaque(false);
        p1.setOpaque(false);
        p2.setOpaque(false);


        this.setLayout(new BorderLayout());

        title.add(highscore);

        this.add(title,BorderLayout.NORTH);
        this.add(p1,BorderLayout.CENTER);

        p1.setLayout(new GridLayout(10,2));
        this.add(p2,BorderLayout.SOUTH);


        show(p1, hm);

        p2.add(back);
        setBackButton(back);

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
                    name.setFont(new Font("",Font.HANGING_BASELINE,24));
                    score.setFont(new Font("", Font.BOLD, 24));
                }
                name.setText(i+1+".            "+pun.get(i).getNam());
                name.setToolTipText("Level: "+pun.get(i).getLevel()+" Lives: "+pun.get(i).getLives()+" Time: "+pun.get(i).getTime());
                score.setText(Integer.toString(pun.get(i).getScore()));
                score.setToolTipText("Level: " + pun.get(i).getLevel() + " Lives: " + pun.get(i).getLives() + " Time: " + pun.get(i).getTime());
            }

            p.add(name);
            p.add(score);
        }
       // System.out.println(pun.size());
    }



    @Override
    protected void setPanelPosition(){
       back.setPanelPoint();
    }

}

