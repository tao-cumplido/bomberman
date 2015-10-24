package de.hsh.project.bomberman;

import de.hsh.project.bomberman.game.Game;

import javax.swing.*;

public class Application {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Game::new);
    }
}
