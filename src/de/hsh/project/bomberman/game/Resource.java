package de.hsh.project.bomberman.game;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.awt.image.BufferedImage;

/**
 * Created by Tao on 14.01.2016.
 */
public class Resource {

    public static AudioInputStream loadAudio(String file) {
        try {
            return AudioSystem.getAudioInputStream(Resource.class.getResource("/res/sfx/" + file));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static BufferedImage loadImage(String file) {
        try {
            return ImageIO.read(Resource.class.getResource("/res/images/" + file));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
