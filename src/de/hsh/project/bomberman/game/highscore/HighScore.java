package de.hsh.project.bomberman.game.highscore;

import java.io.Serializable;

/**
 * Created by taocu on 13.11.2015.
 */
public class HighScore implements Serializable {

    private int score;
    private String name;
    private int lives;
    private double time;
    private String level;

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

    public HighScore(String name, int score, String level, int lives, double time) {
        this.score = score;
        this.name = name;
        this.level = level;
        this.lives = lives;
        this.time = time;
    }

}