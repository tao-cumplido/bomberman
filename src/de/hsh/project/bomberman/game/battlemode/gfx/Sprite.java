package de.hsh.project.bomberman.game.battlemode.gfx;

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

    private int gridWidth, gridHeight;

    public Sprite(String imagePath, int frameWidth, int frameHeight, int frameDuration) {
        try {
            this.spriteSheet = ImageIO.read(getClass().getResource(imagePath));
        } catch (Exception e) {
            e.printStackTrace();
        }

        init(frameWidth, frameHeight, frameDuration);
    }

    public Sprite(BufferedImage spriteSheet, int frameWidth, int frameHeight, int frameDuration) {
        this.spriteSheet = spriteSheet;
        init(frameWidth, frameHeight, frameDuration);
    }

    private void init(int frameWidth, int frameHeight, int frameDuration) {
        if (this.spriteSheet.getWidth() % frameWidth != 0 || this.spriteSheet.getHeight() % frameHeight != 0) {
            throw new IllegalArgumentException("Frames don't distribute equally over spritesheet.");
        }

        this.gridWidth = this.spriteSheet.getWidth() / frameWidth;
        this.gridHeight = this.spriteSheet.getHeight() / frameHeight;

        this.animations = new HashMap<>();
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.frameDuration = frameDuration;
    }

    public void addAnimation(AnimationID id, Integer ... frameOrder) {
        ArrayList<Integer> frames = new ArrayList<>(frameOrder.length);
        Collections.addAll(frames, frameOrder);
        animations.put(id, frames);
        currentFrameIndex = 0;
    }

    public void addAnimation(AnimationID id) {
        Integer[] frames = new Integer[gridWidth * gridHeight];

        for (int i = 0; i < frames.length; i++) {
            frames[i] = i;
        }

        addAnimation(id, frames);
    }

    public void playAnimation(AnimationID id, boolean loop) {
        ArrayList<Integer> animation = animations.get(id);
        if (currentAnimation != animation) {
            currentAnimation = animation;
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
        return getFrame(currentAnimation.get(currentFrameIndex));
    }

    public BufferedImage getFrame(int index) {
        int width = (index % gridWidth) * frameWidth;
        int height = (index / gridWidth) * frameHeight;
        return spriteSheet.getSubimage(width, height, frameWidth, frameHeight);
    }
}
