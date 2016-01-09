package de.hsh.project.bomberman.game.help;

import de.hsh.project.bomberman.game.menu.FontImage;
import de.hsh.project.bomberman.game.menu.MenuState;


/**
 * Created by taocu on 26.10.2015.
 */
public class HelpMenuState extends MenuState{

    private FontImage back;

    public HelpMenuState(){
        FontImage title = new FontImage("bomberman",10,false);
        back = new FontImage("back",5,true);
        this.add(back);
        this.add(title);
        this.setLayout(null);
        title.setBounds((int) (getPreferredSize().getWidth()*0.5-5*9*8), (int) (getPreferredSize().getHeight()*0.05),10*8*9,10*8);
        back.setBounds((int) (getPreferredSize().getWidth()*0.05), (int) (getPreferredSize().getHeight()*0.92),5*8*4,5*8);

        setBackButton(back);
    }


    @Override
    protected void setPanelPosition(){
        back.setPanelPoint();
    }
}
