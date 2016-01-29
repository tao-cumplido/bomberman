package de.hsh.project.bomberman.game.highscore;

import de.hsh.project.bomberman.game.settings.Settings;
import de.hsh.project.bomberman.game.settings.SettingsTyp;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by taocu on 13.11.2015.
 */
public class HighScore implements Serializable {

    private int score;
    private String name;
    private int lives;
    private double time;
    private String level;
    private String board;

    public int getScore() {
        return score;
    }

    public int getLives() {
        return lives;
    }

    public double getTime() {
        return time;
    }

    public String getLevel() {
        return level;
    }

    public void setLives(int lives) { this.lives = lives; }

    public void setTime(double time) {
        this.time = time;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getNam() {
        return name;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public HighScore(String name, int score, String level, int lives, double time,String board) {
        this.score = score;
        this.name = name;
        this.level = level;
        this.lives = lives;
        this.time = time;
        this.board = board;
    }

    public HighScore(int score) {
        this.score = score;

        Map<SettingsTyp, Integer> settings = Settings.getBasicSetting();

        this.level = settings.get(SettingsTyp.LEVEL).toString();
        this.lives = settings.get(SettingsTyp.LIFE);
        this.time =  settings.get(SettingsTyp.TIME);
        this.board = settings.get(SettingsTyp.BOARD).toString();
    }

}