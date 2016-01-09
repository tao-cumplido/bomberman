package de.hsh.project.bomberman.game.menu;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.awt.image.BufferedImage;

/**
 * Created by XER on 2016/1/6 0006.
 */
public class FontImage extends JPanel{
    private BufferedImage image;
    private String text;
    private int scale;
    Point panelPoint;
    public FontImage(String s,int scale,boolean select) {
        text = s;
        this.scale = scale;

        setImage(s,scale);

        if(select)
            setListener();
    }

    private void setImage(String s,int scale){
        setPreferredSize(new Dimension(8*scale*s.length(),8*scale));
        image = FontState.drawString(s,scale);
    }

    private void setListener(){
        MouseAdapter mo = new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                panelMove();
            }
        };
        this.addMouseListener(mo);
        this.addMouseMotionListener(mo);

    }

    private void panelMove(){
        Point currentMousePoint =this.getMousePosition();
        if(panelPoint ==null)
            panelPoint = this.getLocation();
        if(currentMousePoint.getX()<(8*scale*text.length())|| currentMousePoint.getY()<(8*scale))
            this.setLocation((int)panelPoint.getX()-5,(int)panelPoint.getY()-5);
    }

    public void setPanelPoint(){

        if(panelPoint ==null)
            panelPoint = this.getLocation();
        this.setLocation(panelPoint);
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(image, 0, 0, null);
    }
}
