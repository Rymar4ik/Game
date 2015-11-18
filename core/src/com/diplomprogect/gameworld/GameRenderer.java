package com.diplomprogect.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.diplomprogect.gamehelpers.AssetLoader;
import com.diplomprogect.gamehelpers.InputHandler;
import com.diplomprogect.gameobject.Plane;
import com.diplomprogect.gameobject.Floor;
import com.diplomprogect.gameobject.Pipe;
import com.diplomprogect.gameobject.ScrollHandler;


public class GameRenderer {

    //Оголошення змінних для іх подальшого створення та малювання.
    //Створення ортографічної камери

    private GameWorld myWorld;
    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;

    private SpriteBatch batcher;

    private int midPointY;
    private int gameHeight;

    private Plane plane;
    private ScrollHandler scroller;
    private Floor frontFloor, backFloor;
    private Pipe pipe1,pipe2,pipe3;

    private TextureRegion background, floor;
    private Animation planeAnimations;
    private TextureRegion planeMid, planeDown, planeUp;
    private TextureRegion partUp, partDown, bar;


    //ініціалізація об'єктів які взаємодіють зі ігровим простором
    private void initObject(){
        plane = myWorld.getPlane();
        scroller = myWorld.getScroller();
        frontFloor = scroller.getFrontFloor();
        backFloor = scroller.getBackFloor();
        pipe1 = scroller.getPipe1();
        pipe2 = scroller.getPipe2();
        pipe3 = scroller.getPipe3();
    }

    // ініціалізація текстур за папки assets
    private void initAssets(){
        background = AssetLoader.background;
        floor = AssetLoader.floor;
        planeAnimations = AssetLoader.planeAnimation;
        planeMid = AssetLoader.plane;
        planeUp = AssetLoader.planeUp;
        planeDown = AssetLoader.planeDown;
        partUp = AssetLoader.partUp;
        partDown = AssetLoader.partDown;
        bar = AssetLoader.bar;

    }

    //метод для створення полу
    private void drawFloor() {

        batcher.draw(floor, frontFloor.getX(), frontFloor.getY(),
                frontFloor.getWidth(), frontFloor.getHeight());
        batcher.draw(floor, backFloor.getX(), backFloor.getY(),
                backFloor.getWidth(), backFloor.getHeight());
    }
    //метод для створення верхівок труб
    private void drawPipeHeads() {


        batcher.draw(partUp, pipe1.getX() - 1,
                pipe1.getY() + pipe1.getHeight() - 14, 24, 14);
        batcher.draw(partDown, pipe1.getX() - 1,
                pipe1.getY() + pipe1.getHeight() + 45, 24, 14);

        batcher.draw(partUp, pipe2.getX() - 1,
                pipe2.getY() + pipe2.getHeight() - 14, 24, 14);
        batcher.draw(partDown, pipe2.getX() - 1,
                pipe2.getY() + pipe2.getHeight() + 45, 24, 14);

        batcher.draw(partUp, pipe3.getX() - 1,
                pipe3.getY() + pipe3.getHeight() - 14, 24, 14);
        batcher.draw(partDown, pipe3.getX() - 1,
                pipe3.getY() + pipe3.getHeight() + 45, 24, 14);
    }
    //метод для створення труб
    private void drawPipes() {

        batcher.draw(bar, pipe1.getX(), pipe1.getY(), pipe1.getWidth(),
                pipe1.getHeight());
        batcher.draw(bar, pipe1.getX(), pipe1.getY() + pipe1.getHeight() + 45,
                pipe1.getWidth(), midPointY + 66 - (pipe1.getHeight() + 45));

        batcher.draw(bar, pipe2.getX(), pipe2.getY(), pipe2.getWidth(),
                pipe2.getHeight());
        batcher.draw(bar, pipe2.getX(), pipe2.getY() + pipe2.getHeight() + 45,
                pipe2.getWidth(), midPointY + 66 - (pipe2.getHeight() + 45));

        batcher.draw(bar, pipe3.getX(), pipe3.getY(), pipe3.getWidth(),
                pipe3.getHeight());
        batcher.draw(bar, pipe3.getX(), pipe3.getY() + pipe3.getHeight() + 45,
                pipe3.getWidth(), midPointY + 66 - (pipe3.getHeight() + 45));
    }
    //контруктор классу
    public GameRenderer(GameWorld world, int gameHeight, int midPointY){
        myWorld = world;

        this.gameHeight = gameHeight;
        this.midPointY = midPointY;

        camera = new OrthographicCamera();
        camera.setToOrtho(true,136,gameHeight);

        batcher = new SpriteBatch();
        batcher.setProjectionMatrix(camera.combined);
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(camera.combined);

        initAssets();
        initObject();

        Gdx.input.setInputProcessor(new InputHandler(world));

    }


    //Стандартний метод оновлення екрану

    public void render(float runTime){
        // Заповнюемо задній фон одним кольором
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Стартуємо ShapeRenderer
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Відмалюємо Background колір
        shapeRenderer.setColor(114 / 255.0f, 210 / 255.0f, 191 / 255.0f, 1);
        shapeRenderer.rect(0, 0, 136, midPointY - 106);


        // намалюем землю
        shapeRenderer.setColor(147 / 255.0f, 80 / 255.0f, 27 / 255.0f, 1);
        shapeRenderer.rect(0, midPointY + 77, 136, 52);

        // закінчимо ShapeRenderer
        shapeRenderer.end();

        // Стартуємо SpriteBatch
        batcher.begin();
        // відмиінимо прозорість
        // це добре для швидкості роботи коли малюемо картинки без прозорості
        batcher.disableBlending();
        batcher.draw(background, 0, midPointY - 106, 136, 172);

        drawFloor();

        drawPipes();
        batcher.enableBlending();

        drawPipeHeads();

        //якщо літак падае ,то ліхтарики на кінці літака на мерихтять
        if(plane.doNotFlap()) {
            batcher.draw(planeMid, plane.getX(), plane.getY(),
                    plane.getWidth() / 2.0f, plane.getHeight() / 2.0f , plane.getWidth(), plane.getHeight(), 1, 1, plane.getRotation());
        }else {
            batcher.draw(planeAnimations.getKeyFrame(runTime), plane.getX(), plane.getY(),
                    plane.getWidth() / 2.0f, plane.getHeight() / 2.0f, plane.getWidth(), plane.getHeight(), 1, 1, plane.getRotation());
        }


        if (myWorld.isReady()) {
            // намалюем спочатку тінь
            AssetLoader.shadow.draw(batcher, "Touch me", (136 / 2) - (42), 76);
            // намалюем сами слово
            AssetLoader.font.draw(batcher, "Touch me", (136 / 2) - (42 - 1), 75);
        } else {

            if (myWorld.isGameOver() || myWorld.isHighScore()) {

                if (myWorld.isGameOver()) {
                    AssetLoader.shadow.draw(batcher, "Game Over", 25, 56);
                    AssetLoader.font.draw(batcher, "Game Over", 24, 55);

                    AssetLoader.shadow.draw(batcher, "High Score:", 23, 106);
                    AssetLoader.font.draw(batcher, "High Score:", 22, 105);

                    String highScore = AssetLoader.getHighScore() + "";

                    AssetLoader.shadow.draw(batcher, highScore, (136 / 2)
                            - (3 * highScore.length()), 128);
                    AssetLoader.font.draw(batcher, highScore, (136 / 2)
                            - (3 * highScore.length() - 1), 127);
                } else {
                    AssetLoader.shadow.draw(batcher, "High Score!", 19, 56);
                    AssetLoader.font.draw(batcher, "High Score!", 18, 55);
                }

                AssetLoader.shadow.draw(batcher, "Try again?", 23, 76);
                AssetLoader.font.draw(batcher, "Try again?", 24, 75);

                // Конвертуємо Integer в String
                //використовується конкотенація
                String score = myWorld.getScore() + "";

                AssetLoader.shadow.draw(batcher, score,
                        (136 / 2) - (3 * score.length()), 12);
                AssetLoader.font.draw(batcher, score,
                        (136 / 2) - (3 * score.length() - 1), 11);
            }

            String score = myWorld.getScore() + "";

            AssetLoader.shadow.draw(batcher, "" + myWorld.getScore(), (136 / 2)
                    - (3 * score.length()), 12);
            AssetLoader.font.draw(batcher, "" + myWorld.getScore(), (136 / 2)
                    - (3 * score.length() - 1), 11);
        }
        batcher.end();
    }
}
