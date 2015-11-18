package com.diplomprogect.gameobject;


import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public class Pipe extends Scrollable {

    private Random random;
    private Rectangle partUp,partDown,barUp, barDown;
    private float floorY;

    public static final int VERTICAL_GAP = 45;
    public static final int PART_WIDTH = 26;
    public static final int PART_HEIGHT = 12;

    private boolean isScored;

    public Pipe(float x, float y, int width, int height, float scrollSpeed, float floorY) {
        super(x, y, width, height, scrollSpeed);

        random = new Random();
        partUp = new Rectangle();
        partDown = new Rectangle();
        barUp = new Rectangle();
        barDown = new Rectangle();
        this.floorY = floorY;
        isScored = false;
    }

    @Override
    public void update(float delta) {
        super.update(delta);

       barUp.set(position.x,position.y,width,height);
       barDown.set(position.x, position.y + height + VERTICAL_GAP, width, floorY - (position.y + height + VERTICAL_GAP) );


        partUp.set(position.x - (PART_WIDTH - width) / 2, position.y + height
                - PART_HEIGHT, PART_WIDTH, PART_HEIGHT);
        partDown.set(position.x - (PART_WIDTH - width) / 2, barDown.y,
                PART_WIDTH, PART_HEIGHT);
    }

    @Override
    public void reset(float newX) {
        super.reset(newX);
        height = random.nextInt(90) + 15;
        isScored = false;
    }

    public boolean collides (Plane plane) {
        if (position.x < plane.getX() + plane.getWidth()) {
            return (Intersector.overlaps(plane.getBoundingRectangle(), barUp)
                    || Intersector.overlaps(plane.getBoundingRectangle(), barDown)
                    || Intersector.overlaps(plane.getBoundingRectangle(), partUp) || Intersector
                    .overlaps(plane.getBoundingRectangle(), partDown));
        }
        return false;}

    public Rectangle getPartUp() {
        return partUp;
    }

    public Rectangle getPartDown() {
        return partDown;
    }

    public Rectangle getBarUp() {
        return barUp;
    }

    public Rectangle getBarDown() {
        return barDown;
    }

    public boolean isScored() {
        return isScored;
    }

    public void setScored(boolean isScored) {
        this.isScored = isScored;
    }

    public void onRestart(float x, float scrollSpeed) {
        velocity.x = scrollSpeed;
        reset(x);
    }
}
