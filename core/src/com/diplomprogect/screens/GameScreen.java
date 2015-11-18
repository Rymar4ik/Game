package com.diplomprogect.screens;

//импорти
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.diplomprogect.gamehelpers.InputHandler;
import com.diplomprogect.gameworld.GameRenderer;
import com.diplomprogect.gameworld.GameWorld;


public class GameScreen implements Screen {


    private GameWorld world;
    private GameRenderer renderer;
    private float runTime;

    public GameScreen() {
        Gdx.app.log("GameScreen", "Attached");

        //визначення розмірів ігрового екрану
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float gameWidth = 137;
        float gameHeight = screenHeight / (screenWidth / gameWidth);

        //центральна лінія відносно якої буде малюватися ігровий світ
        int midPointY = (int) gameHeight / 2;

        // створення World и встановлення йому центральну лінію
        world = new GameWorld(midPointY);

        // ми говоримо об'єкту render щоб він намалював об'єкт world включаючи висоту екрану gameHeight и центральну
        // лінію midPointy
        renderer = new GameRenderer(world, (int) gameHeight, midPointY);
        // установити обробник дотиків на наш об'єкт world
        Gdx.input.setInputProcessor(new InputHandler(world));

    }

    @Override
    public void render(float delta) {
        //обновлення світу і відрисовщика
        runTime += delta;
        world.update(delta);
        renderer.render(delta);

    }

    @Override
    public void resize(int width, int height) {
        Gdx.app.log("GameScreen", "resizing");
    }

    @Override
    public void show() {
        Gdx.app.log("GameScreen", "show called");
    }

    @Override
    public void hide() {
        Gdx.app.log("GameScreen", "hide called");
    }

    @Override
    public void pause() {
        Gdx.app.log("GameScreen", "pause called");
    }

    @Override
    public void resume() {
        Gdx.app.log("GameScreen", "resume called");
    }

    @Override
    public void dispose() {
        // Leave blank
    }



}
