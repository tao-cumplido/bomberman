package de.hsh.project.bomberman.game.gfx;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by taocu on 30.10.2015.
 */
public class Sprite {

    private BufferedImage spriteSheet;
    private HashMap<AnimationID, ArrayList<Integer>> animations;
    private ArrayList<Integer> currentAnimation;

    private boolean animationIsPlaying = false;
    private boolean animationLoops = false;

    private int currentFrameIndex, ticks;
    private int frameWidth, frameHeight, frameDuration;

    public Sprite(String imagePath, int frameWidth, int frameHeight, int frameDuration) {
        try {
            this.spriteSheet = ImageIO.read(getClass().getResource(imagePath));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (this.spriteSheet.getWidth() % frameWidth != 0 || this.spriteSheet.getHeight() % frameHeight != 0) {
            throw new IllegalArgumentException("Frames don't distribute equally over spritesheet.");
        }

        this.animations = new HashMap<>();
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.frameDuration = frameDuration;
    }

    public void addAnimation(AnimationID id, Integer ... frameOrder) {
        ArrayList<Integer> frames = new ArrayList<>(frameOrder.length);
        Collections.addAll(frames, frameOrder);
        animations.put(id, frames);
    }

    public void playAnimation(AnimationID id, boolean loop) {
        ArrayList<Integer> animation = animations.get(id);
        if (currentAnimation != animation) {
            currentAnimation = animations.get(id);
            animationIsPlaying = true;
            animationLoops = loop;
            ticks = 0;
            currentFrameIndex = 0;
        }
    }

    public void update() {
        if (animationIsPlaying) {
            ticks++;

            if (ticks > frameDuration) {
                ticks = 0;

                if (++currentFrameIndex >= currentAnimation.size()) {
                    currentFrameIndex = 0;
                    if (!animationLoops) {
                        animationIsPlaying = false;
                    }
                }
            }
        }
    }

    public BufferedImage getCurrentFrame() {
        return spriteSheet.getSubimage(currentAnimation.get(currentFrameIndex) * frameWidth, 0, frameWidth, frameHeight);
    }
}
