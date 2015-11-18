package com.diplomprogect.gameobject;

import com.diplomprogect.gamehelpers.AssetLoader;
import com.diplomprogect.gameworld.GameWorld;

public class ScrollHandler {
    private GameWorld gameWorld;
    private Floor frontFloor, backFloor;
    private Pipe pipe1, pipe2, pipe3;

    public static final int SCROLL_SPEED = -59;
    public static final int PIPE_GAP = 49;


    public ScrollHandler(GameWorld gameWorld,float yPos) {

        this.gameWorld = gameWorld;

        frontFloor = new Floor(0, yPos, 143, 11, SCROLL_SPEED);
        backFloor = new Floor(frontFloor.getTailX(), yPos, 143, 11, SCROLL_SPEED);


        pipe1 = new Pipe(210, 0, 22, 60, SCROLL_SPEED,yPos);
        pipe2 = new Pipe(pipe1.getTailX() + PIPE_GAP, 0, 22, 70, SCROLL_SPEED,yPos);
        pipe3 = new Pipe(pipe2.getTailX() + PIPE_GAP, 0, 22, 60, SCROLL_SPEED,yPos);
    }

    public void update(float delta) {
        frontFloor.update(delta);
        backFloor.update(delta);

        pipe1.update(delta);
        pipe2.update(delta);
        pipe3.update(delta);


        if (pipe1.isScrolledLeft()) {
            pipe1.reset(pipe3.getTailX() + PIPE_GAP);
        } else if (pipe2.isScrolledLeft()) {
            pipe2.reset(pipe1.getTailX() + PIPE_GAP);
        } else if (pipe3.isScrolledLeft()) {
            pipe3.reset(pipe2.getTailX() + PIPE_GAP);
        }


        if (frontFloor.isScrolledLeft()) {
            frontFloor.reset(backFloor.getTailX());
        } else if (backFloor.isScrolledLeft()){
            backFloor.reset(frontFloor.getTailX());
        }

    }

    public void stop(){
        frontFloor.stop();
        backFloor.stop();
        pipe1.stop();
        pipe2.stop();
        pipe3.stop();
    }

    public boolean collides(Plane plane) {
        if (!pipe1.isScored()
                && pipe1.getX() + (pipe1.getWidth() / 2) < plane.getX()
                + plane.getWidth()) {
            addScore(1);
            pipe1.setScored(true);
            AssetLoader.coin.play(.2f);
        } else if (!pipe2.isScored()
                && pipe2.getX() + (pipe2.getWidth() / 2) < plane.getX()
                + plane.getWidth()) {
            addScore(1);
            pipe2.setScored(true);
            AssetLoader.coin.play(.2f);

        } else if (!pipe3.isScored()
                && pipe3.getX() + (pipe3.getWidth() / 2) < plane.getX()
                + plane.getWidth()) {
            addScore(1);
            pipe3.setScored(true);
            AssetLoader.coin.play(.2f);

        }
        return (pipe1.collides(plane) || pipe2.collides(plane) || pipe3
                .collides(plane));}

    public Floor getFrontFloor() {
        return frontFloor;
    }

    public Floor getBackFloor() {
        return backFloor;
    }

    public Pipe getPipe1() {
        return pipe1;
    }

    public Pipe getPipe2() {
        return pipe2;
    }

    public Pipe getPipe3() {
        return pipe3;
    }

    public void addScore(int increment){
        gameWorld.addScore(increment);}

    public void onRestart() {
        frontFloor.onRestart(0, SCROLL_SPEED);
        backFloor.onRestart(frontFloor.getTailX(), SCROLL_SPEED);
        pipe1.onRestart(210, SCROLL_SPEED);
        pipe2.onRestart(pipe1.getTailX() + PIPE_GAP, SCROLL_SPEED);
        pipe3.onRestart(pipe2.getTailX() + PIPE_GAP, SCROLL_SPEED);
    }
}



