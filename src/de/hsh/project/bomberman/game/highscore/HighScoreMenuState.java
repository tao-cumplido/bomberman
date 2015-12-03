package de.hsh.project.bomberman.game.highscore;

import de.hsh.project.bomberman.game.Game;
import de.hsh.project.bomberman.game.GameState;
import de.hsh.project.bomberman.game.credits.CreditsState;
import de.hsh.project.bomberman.game.menu.MenuState;
import de.hsh.project.bomberman.game.menu.TitleState;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by taocu on 26.10.2015.
 */

public class HighScoreMenuState extends GameState {


    ImageIcon icon = new ImageIcon(this.getClass().getResource("/res/images/cover.png"));

    public HighScoreMenuState(){
        super();

        this.setLayout(new BorderLayout());


        EnterNameState hm = new EnterNameState();
        hm.addScore("Juan",7587587,"Easy",4,5);
        hm.addScore("San",325252434,"Hard",5,7);


        JLabel p0 = new JLabel("Bomberman");
        JPanel p1 = new JPanel();
        JPanel p2 = new JPanel();
        p0.setOpaque(false);
        p1.setOpaque(false);
        p2.setOpaque(false);

        this.add(p0,BorderLayout.NORTH);
        this.add(p1,BorderLayout.CENTER);
        p1.setLayout(new GridLayout(10,2));
        this.add(p2,BorderLayout.SOUTH);

        p0.setHorizontalAlignment(JLabel.CENTER);
        p0.setFont(new Font("",Font.ITALIC,32));
        show(p1,hm);

        JButton menu = new JButton("<<");
        menu.setContentAreaFilled(false);
        menu.setBorderPainted(false);
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
        System.out.println(pun.size());
    }

    protected void setMenuBut(JButton menuBut) {
        menuBut.addActionListener((event) -> Game.switchState(new TitleState()));
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(icon.getImage(),0,0,getWidth(),getHeight(),null);
    }
}

