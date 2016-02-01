package de.hsh.project.bomberman.game.highscore;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by XER on 2016/1/27 0027.
 */
public class HighScoreFile {

    private static ObjectOutputStream outputStream = null;
    private static ObjectInputStream inputStream = null;
    private static ArrayList<HighScore> scores = new ArrayList<HighScore>();
    private static final String FILE_SCORE = "scores.dat";


    public static void addScore(HighScore punkt) {
        loadScoreFile();
        scores.add(punkt);

        sort();

        if (scores.size() > 10)
            scores.remove(10);

        updateScoreFile();
    }

    private static void sort() {
        Compare comparator = new Compare();
        Collections.sort(scores, comparator);
    }

    public static void loadScoreFile() {
        updateScoreFile();
        try {
            inputStream = new ObjectInputStream(new FileInputStream(FILE_SCORE));
            scores = (ArrayList<HighScore>) inputStream.readObject();
        }catch(FileNotFoundException e){
            System.out.println("[Load0] " + e.getMessage());
        } catch (IOException e) {
            System.out.println("[Load1] " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("[Load2] " + e.getMessage());
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (IOException e) {
                System.out.println("[Load3] " + e.getMessage());
            }
        }
    }

    public static void updateScoreFile() {
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

    public static ArrayList<HighScore> getScores() {
        loadScoreFile();
        return scores;
    }

}
