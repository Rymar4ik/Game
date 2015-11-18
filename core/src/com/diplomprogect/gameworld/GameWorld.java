package com.diplomprogect.gameworld;


import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.diplomprogect.gameobject.Plane;
import com.diplomprogect.gameobject.ScrollHandler;
import com.diplomprogect.gamehelpers.AssetLoader;

public class GameWorld {

    private Plane plane;
    private ScrollHandler scroller;
    private Rectangle ground;
    private int score;
    private int midPointY;
    private GameState currentState;

    // конструктор класу World . створеня наголовніших частин нашої гри.
    public GameWorld(int midPointY) {
        plane = new Plane(33 , midPointY - 5, 18, 9);
        scroller = new ScrollHandler(this, midPointY + 66);
        ground = new Rectangle(0, midPointY + 66, 167, 11);
        currentState = GameState.READY;
        this.midPointY = midPointY;
    }
    //оновлення гри, та перевирка на зіткнення с трубами та підлогою
    public void update(float delta) {

        if (currentState == GameState.RUNNING) {
                updateRunning(delta);
        }

    }

    public void updateRunning(float delta) {
        if (delta > .15f) {
            delta = .15f;
        }

        plane.update(delta);
        scroller.update(delta);

        if (scroller.collides(plane) && plane.isAlive()) {
            scroller.stop();
            plane.die();
            AssetLoader.dead.play();
            currentState = GameState.GAMEOVER;

            if(score > AssetLoader.getHighScore()){
                AssetLoader.setHighScore(score);
                currentState = GameState.HIGHSCORE;
            }

        }

        if (Intersector.overlaps(plane.getBoundingRectangle(), ground)) {
            scroller.stop();
            plane.die();
            plane.decelerate();
            AssetLoader.dead.play();
            currentState = GameState.GAMEOVER;


            if(score > AssetLoader.getHighScore()){
                AssetLoader.setHighScore(score);
                currentState = GameState.HIGHSCORE;
            }

        }
    }
    // створея ігрових станів гри
    public enum GameState {READY, RUNNING, GAMEOVER, HIGHSCORE,}

    // геттери та сеттери для доступу до захищенних данних
    public Plane getPlane(){
        return plane;
    }

    public ScrollHandler getScroller(){
        return scroller;
    }

    public int getScore() {
        return score;
    }

    //метод додавання очків
    public void addScore(int increment){
        score += increment;
    }

    //перевірка чи стартовала гра
    public boolean isReady(){
        if (GameState.READY == currentState){
            return true;
        }
            return false;
    }
    //перевірка чи гра закінчиласся
    public boolean isGameOver(){

           return currentState == GameState.GAMEOVER;
    }

    //  перевірка чи було набрано максимальна кількість очків
    public boolean isHighScore() {

        return currentState == GameState.HIGHSCORE;
    }

    // визивается при старті гри
    public void start(){
        currentState = GameState.RUNNING;
    }

    //це метод визивае у всіх головних екземплярах класів метод оновлення. спрацевую по дотику коли гра закінченна
    public void restart() {
        currentState = GameState.READY;
        score = 0;
        plane.onRestart(midPointY - 5);
        scroller.onRestart();
        currentState = GameState.READY;

    }
}
