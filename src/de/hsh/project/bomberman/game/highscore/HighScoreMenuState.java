package de.hsh.project.bomberman.game.highscore;


import de.hsh.project.bomberman.game.Game;
import de.hsh.project.bomberman.game.battlemode.BattleState;
import de.hsh.project.bomberman.game.menu.FontImage;
import de.hsh.project.bomberman.game.menu.MenuState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;


/**
 * Created by taocu on 26.10.2015.
 */
public class HighScoreMenuState extends MenuState {



    private FontImage back;
    private FontImage play;

    public HighScoreMenuState(){
        super();


        FontImage highscore = new FontImage("highscore",7,false);
        back = new FontImage("back",4,true);
        play = new FontImage("play",4,true);


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
        p2.setPreferredSize(new Dimension((int)getPreferredSize().getWidth(),(int)(getPreferredSize().getHeight()*0.065)));
        p2.setLayout(null);

        show(p1);

        p2.add(back);
        p2.add(play);
        back.setBounds((int) (p2.getPreferredSize().getWidth()*0.20), (int) (p2.getPreferredSize().getHeight()*0.05),5*8*4,5*8);
        play.setBounds((int) (p2.getPreferredSize().getWidth()*0.70), (int) (p2.getPreferredSize().getHeight()*0.05),5*8*4,5*8);
        setBackButton(back);
        play.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Game.switchState(new BattleState());
            }
        });

    }

    public void show(JPanel p){
        ArrayList<HighScore> pun = HighScoreFile.getScores();

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
    }



    @Override
    protected void setPanelPosition(){
        play.setPanelPoint();
        back.setPanelPoint();
    }

}

