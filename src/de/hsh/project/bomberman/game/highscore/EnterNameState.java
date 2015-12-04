package de.hsh.project.bomberman.game.highscore;

import de.hsh.project.bomberman.game.menu.MenuState;

import javax.swing.*;
import java.util.ArrayList;

import java.io.*;
import java.util.Collections;

/**
 * Created by taocu on 26.10.2015.
 */
public class EnterNameState {


    private ArrayList<HighScore> scores;
    private static final String FILE_SCORE = "scores.dat";

    ObjectOutputStream outputStream = null;
    ObjectInputStream inputStream = null;

    public EnterNameState() {
        scores = new ArrayList<HighScore>();
    }

    public ArrayList<HighScore> getScores() {
        loadScoreFile();
        return scores;
    }

    private void sort() {
        Compare comparator = new Compare();
        Collections.sort(scores, comparator);
    }

    public void addScore(String name, int score, String level, int lives, double time) {
        loadScoreFile();
        scores.add(new HighScore(name, score, level, lives, time));

        sort();

        if (scores.size() > 10)
            scores.remove(10);

        updateScoreFile();
    }

    public void loadScoreFile() {
        try {
            inputStream = new ObjectInputStream(new FileInputStream(FILE_SCORE));
            scores = (ArrayList<HighScore>) inputStream.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("[Load] " + e.getMessage());
        } catch (IOException e) {
            System.out.println("[Load] " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("[Load] " + e.getMessage());
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (IOException e) {
                System.out.println("[Load] " + e.getMessage());
            }
        }
    }

    public void updateScoreFile() {
        try {
            outputStream = new ObjectOutputStream(new FileOutputStream(FILE_SCORE));
            outputStream.writeObject(scores);
        } catch (FileNotFoundException e) {
            System.out.println("[Update] " + e.getMessage() + ",the program will try and make a new file");
        } catch (IOException e) {
            System.out.println("[Update] " + e.getMessage());
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (IOException e) {
                System.out.println("[Update] " + e.getMessage());
            }
        }
    }
}