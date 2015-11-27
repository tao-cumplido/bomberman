package de.hsh.project.bomberman.game.battlemode.player;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.function.Consumer;

/**
 * Created by taocu on 27.11.2015.
 */
public class KeyAction extends AbstractAction {

    private Consumer<ActionEvent> action;

    public KeyAction(Consumer<ActionEvent> action) {
        this.action = action;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        action.accept(e);
    }
}
