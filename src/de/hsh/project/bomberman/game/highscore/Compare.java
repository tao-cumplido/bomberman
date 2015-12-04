package de.hsh.project.bomberman.game.highscore;

import java.util.Comparator;

/**
 * Created by Santiago on 02/12/2015.
 */
public class Compare implements Comparator<HighScore> {

    public int compare(HighScore score1, HighScore score2) {

        int sc1 = score1.getScore();
        int sc2 = score2.getScore();

        if (sc1 > sc2){
            return -1;
        }else if (sc1 < sc2){
            return +1;
        }else{
            return 0;
        }
    }
}