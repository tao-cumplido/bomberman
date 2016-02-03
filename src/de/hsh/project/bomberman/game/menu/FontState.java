package de.hsh.project.bomberman.game.menu;

import de.hsh.project.bomberman.game.Resource;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by XER on 2016/1/6 0006.
 */
public class FontState {
    private static BufferedImage[][] font = load("font.gif", 8, 8);
    public static BufferedImage[] text;

    public static BufferedImage[][] load(String path, int w, int h) {
        BufferedImage[][] ret;
        try {
            BufferedImage spritesheet = Resource.loadImage(path);
            int width = spritesheet.getWidth() / w;
            int height = spritesheet.getHeight() / h;
            ret = new BufferedImage[height][width];
            for(int i = 0; i < height; i++) {
                for(int j = 0; j < width; j++) {
                    ret[i][j] = spritesheet.getSubimage(j * w, i * h, w, h);
                }
            }
            return ret;
        }
        catch(Exception e) {
            e.printStackTrace();
            System.out.println("Error loading graphics.");
            System.exit(0);
        }
        return null;
    }

    public static BufferedImage drawString(String s, int scale) {
        BufferedImage newImage = new BufferedImage(8*scale*s.length(),8*scale,BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) newImage.getGraphics();
        newImage = g.getDeviceConfiguration().createCompatibleImage(8*scale*s.length(),8*scale, Transparency.TRANSLUCENT);
        g.dispose();
        g = newImage.createGraphics();
        s = s.toUpperCase();
        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if(c == 47) c = 36; // slash
            if(c == 58) c = 37; // colon
            if(c == 32) c = 38; // space
            if(c >= 65 && c <= 90) c -= 65; // letters
            if(c >= 48 && c <= 57) c -= 22; // numbers
            int row = c / font[0].length;
            int col = c % font[0].length;
            g.drawImage(font[row][col], 8 * i * scale, 0, 8 * scale, 8 * scale, null);
        }
        return newImage;
    }

    public static BufferedImage[] getText(String s){
        text = new BufferedImage[s.length()];
        s = s.toUpperCase();
        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if(c == 47) c = 36; // slash
            if(c == 58) c = 37; // colon
            if(c == 32) c = 38; // space
            if(c >= 65 && c <= 90) c -= 65; // letters
            if(c >= 48 && c <= 57) c -= 22; // numbers
            int row = c / font[0].length;
            int col = c % font[0].length;
            text[i] = font[row][col];

        }
        return text;
    }

}


