package de.hsh.project.bomberman.game.settings;


import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by XER on 2015/11/15 0015.
 */
public abstract class KeyControl extends KeyAdapter {

    private int code;
    private JTextField jTextField;

    public KeyControl(JTextField j){
        this.jTextField = j;
        code = -1;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        e.consume();

    }

    @Override
    public void keyPressed(KeyEvent e) {
        code = e.getKeyCode();
        String text = e.getKeyText(code);
        if(setCode(code)){
            if(code==e.VK_BACK_SPACE){
                text = null;
            }
            setPlayerWert(code);
            jTextField.setText(text);
        }


    }

    @Override
    public void keyReleased(KeyEvent e) {

        super.keyReleased(e);
    }

    protected abstract boolean setCode(int code);

    protected abstract void setPlayerWert(int code);

    public int getCode(){
        return code;
    }
}
