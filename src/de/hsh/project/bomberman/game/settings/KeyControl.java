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
        String text = KeyEvent.getKeyText(code);
        if(setCode(code)){
            switch (code){
                case KeyEvent.VK_BACK_SPACE:
                    jTextField.setText(null);
                    setPlayerValue(code);
                    break;
                case KeyEvent.VK_TAB:
                    JOptionPane.showMessageDialog(null, "Please do not use this button!", "Warning", JOptionPane.WARNING_MESSAGE);
                    break;
                case KeyEvent.VK_SHIFT:
                    JOptionPane.showMessageDialog(null, "Please do not use this button!", "Warning", JOptionPane.WARNING_MESSAGE);
                    break;
                case KeyEvent.VK_ALT:
                    JOptionPane.showMessageDialog(null, "Please do not use this button!", "Warning", JOptionPane.WARNING_MESSAGE);
                    break;
                default:
                    setPlayerValue(code);
                    jTextField.setText(text);
            }

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        super.keyReleased(e);
    }

    protected abstract boolean setCode(int code);

    protected abstract void setPlayerValue(int code);

    public int getCode(){
        return code;
    }
}
