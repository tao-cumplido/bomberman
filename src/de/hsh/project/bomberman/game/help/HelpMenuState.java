package de.hsh.project.bomberman.game.help;

import de.hsh.project.bomberman.game.menu.FontImage;
import de.hsh.project.bomberman.game.menu.MenuState;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;


/**
 * Created by taocu on 26.10.2015.
 */
public class HelpMenuState extends MenuState{

    private FontImage back;
    private JPanel rules;
    private JPanel powerup;
    private JPanel points;

    public HelpMenuState(){
        FontImage title = new FontImage("bomberman",10,false);
        back = new FontImage("back",5,true);
        this.add(back);
        this.add(title);
        this.setLayout(null);
        title.setBounds((int) (getPreferredSize().getWidth()*0.5-5*9*8), (int) (getPreferredSize().getHeight()*0.05),10*8*9,10*8);
        back.setBounds((int) (getPreferredSize().getWidth()*0.05), (int) (getPreferredSize().getHeight()*0.92),5*8*4,5*8);
        setBackButton(back);
        init();
        this.add(rules);
        this.add(powerup);
        this.add(points);
    }

    private void init(){

        rules = new JPanel();
        powerup = new JPanel();
        points = new JPanel();
        rules.setOpaque(false);
        powerup.setOpaque(false);
        points.setOpaque(false);
        Border lineBorder = BorderFactory.createLineBorder(Color.GRAY,2);
        rules.setBorder(new TitledBorder(lineBorder, "RULES"));
        powerup.setBorder(new TitledBorder(lineBorder,"POWERUP"));
        points.setBorder(new TitledBorder(lineBorder,"SCORING"));
        rules.setPreferredSize(new Dimension((int) (getPreferredSize().getWidth()*0.8), (int) (getPreferredSize().getHeight()*0.20)));
        powerup.setPreferredSize(new Dimension((int) (getPreferredSize().getWidth()*0.8), (int) (getPreferredSize().getHeight()*0.20)));
        points.setPreferredSize(new Dimension((int) (getPreferredSize().getWidth()*0.8), (int) (getPreferredSize().getHeight()*0.20)));
        powerup.setBounds((int) (getPreferredSize().getWidth()*0.1), (int) (getPreferredSize().getHeight()*0.2),
                (int) (getPreferredSize().getWidth()*0.8), (int) (getPreferredSize().getHeight()*0.20));
        rules.setBounds((int) (getPreferredSize().getWidth() * 0.1), (int) (getPreferredSize().getHeight() * 0.45),
                (int) (getPreferredSize().getWidth() * 0.7), (int) (getPreferredSize().getHeight() * 0.20));
        points.setBounds((int) (getPreferredSize().getWidth()*0.1), (int) (getPreferredSize().getHeight()*0.7),
                (int) (getPreferredSize().getWidth()*0.6), (int) (getPreferredSize().getHeight()*0.2));

        JTextArea ruleText = new JTextArea();
        //ruleText.setEnabled(false);
        ruleText.setLineWrap(true);
        ruleText.setWrapStyleWord(true);
        ruleText.setOpaque(false);
        ruleText.setFont(new Font("Arial",1,20));
        ruleText.setForeground(Color.BLACK);
        ruleText.setText("Select the number of players, lives, board, time and level to start the game. " +
                "Blow up your opponents using bombs before they manage to kill you. " +
                "Destroy blocks to find power-ups which will enhance your abillities.");
        ruleText.setPreferredSize(new Dimension((int) (getPreferredSize().getWidth()*0.6), (int) (getPreferredSize().getHeight()*0.15)));
        rules.add(ruleText);

        JTextArea powerupText = new JTextArea();
        //powerupText.setEnabled(false);
        powerupText.setLineWrap(true);
        powerupText.setWrapStyleWord(true);
        powerupText.setOpaque(false);
        powerupText.setForeground(Color.black);
        powerupText.setText("You'll find items which will raise the amount of bombs you can place at one time, " +
                "the range of your bombs and your movement speed. In addition you can " +
                "find remote controls which allow you to detonate your bombs when you want them to. " +
                "Powerups marked with a question mark contain special abillities but no one knows what will happen.");
        powerupText.setFont(new Font("Arial",1,20));
        powerupText.setPreferredSize(new Dimension((int) (getPreferredSize().getWidth()*0.7), (int) (getPreferredSize().getHeight()*0.15)));
        powerup.add(powerupText);

        JTextArea pointText = new JTextArea();
        //pointText.setEnabled(false);
        pointText.setLineWrap(true);
        pointText.setWrapStyleWord(true);
        pointText.setOpaque(false);
        pointText.setForeground(Color.black);
        pointText.setText("200 points for each regular Power-up\n" +
                "500 points for each Remote Control\n" +
                "1000 points for each remaining life\n" +
                "Plus a bonus from the remaining time");
        pointText.setFont(new Font("Arial",1,20));
        pointText.setPreferredSize(new Dimension((int) (getPreferredSize().getWidth()*0.5), (int) (getPreferredSize().getHeight()*0.15)));
        points.add(pointText);
    }

    @Override
    protected void setPanelPosition(){
        back.setPanelPoint();
    }
}
