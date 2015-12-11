package de.hsh.project.bomberman.game.highscore;

/**
 * Created by XER on 2015/12/1 0001.
 */
public class ScoreInfo {
    private int score;
    private String name;
    private int pTime;
    private boolean pBoard;
    private boolean pLevel;
    private int pLife;
    private int pPlayer;

    public ScoreInfo(int score, String name, int pTime, boolean pBoard, boolean pLevel, int pLife, int pPlayer) {
        this.score = score;
        this.name = name;
        this.pTime = pTime;
        this.pBoard = pBoard;
        this.pLevel = pLevel;
        this.pLife = pLife;
        this.pPlayer = pPlayer;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getpTime() {
        return this.pTime;
    }

    public void setpTime(int pTime) {
        this.pTime = pTime;
    }

    public boolean ispBoard() {
        return this.pBoard;
    }

    public void setpBoard(boolean pBoard) {
        this.pBoard = pBoard;
    }

    public boolean ispLevel() {
        return this.pLevel;
    }

    public void setpLevel(boolean pLevel) {
        this.pLevel = pLevel;
    }

    public int getpLife() {
        return this.pLife;
    }

    public void setpLife(int pLife) {
        this.pLife = pLife;
    }

    public int getpPlayer() {
        return this.pPlayer;
    }

    public void setpPlayer(int pPlayer) {
        this.pPlayer = pPlayer;
    }
}
