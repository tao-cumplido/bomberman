package de.hsh.project.bomberman.game.battlemode.gfx;

import de.hsh.project.bomberman.game.Resource;
import de.hsh.project.bomberman.game.battlemode.board.GameBoard;

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

    private Callback callback;

    public Sprite(String imagePath, int frameWidth, int frameHeight) {
        this.spriteSheet = Resource.loadImage(imagePath);

        init(frameWidth, frameHeight);
    }

    public Sprite(BufferedImage spriteSheet, int frameWidth, int frameHeight) {
        this.spriteSheet = spriteSheet;
        init(frameWidth, frameHeight);
    }

    public Sprite(BufferedImage spriteSheet) {
        this(spriteSheet, GameBoard.TILE_SIZE, GameBoard.TILE_SIZE);
    }

    private void init(int frameWidth, int frameHeight) {
        if (this.spriteSheet.getWidth() % frameWidth != 0 || this.spriteSheet.getHeight() % frameHeight != 0) {
            throw new IllegalArgumentException("Frames don't distribute equally over spritesheet.");
        }

        this.gridWidth = this.spriteSheet.getWidth() / frameWidth;
        this.gridHeight = this.spriteSheet.getHeight() / frameHeight;

        this.animations = new HashMap<>();
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
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

    private void playAnimation(AnimationID id, int frameDuration, boolean loop, Callback callback) {
        ArrayList<Integer> animation = animations.get(id);
        if (currentAnimation != animation) {
            this.currentAnimation = animation;
            this.animationIsPlaying = true;
            this.animationLoops = loop;
            this.frameDuration = frameDuration;
            this.callback = callback;
            this.ticks = 0;
            this.currentFrameIndex = 0;
        }
    }

    public void playAnimation(AnimationID id, int frameDuration, Callback callback) {
        playAnimation(id, frameDuration, false, callback);
    }

    public void playAnimation(AnimationID id, int frameDuration, boolean loop) {
        playAnimation(id, frameDuration, loop, null);
    }

    public void playAnimation(AnimationID id, boolean loop) {
        playAnimation(id, 0, loop, null);
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
                        if (callback != null) callback.invoke();
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
