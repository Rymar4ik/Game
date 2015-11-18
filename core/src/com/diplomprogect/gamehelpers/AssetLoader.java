package com.diplomprogect.gamehelpers;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetLoader {

    public static Texture texture;
    public static TextureRegion playUpB,playDownB,credits;
    public static TextureRegion background , floor;

    public static Animation planeAnimation;
    public static TextureRegion planeUp, plane, planeDown;

    public static TextureRegion partUp, partDown, bar;

    public static Sound dead;
    public static Sound flap;
    public static Sound coin;

    public static BitmapFont shadow, font;

    public static Preferences pref;

    public static void load() {

        texture = new Texture(Gdx.files.internal("data/texture2.png"));
        texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);


        playUpB = new TextureRegion(texture, 0, 211, 53, 31);
        playDownB = new TextureRegion(texture, 54, 211, 53, 31);
        credits = new TextureRegion(texture, 108, 198, 53, 31);

        playDownB.flip(false,true);
        playUpB.flip(false,true);
        credits.flip(false,true);

        background = new TextureRegion(texture, 0, 0, 136, 172);
        background.flip(false, true);

        floor = new TextureRegion(texture, 0, 173, 167, 11);
        floor.flip(false, true);

        planeDown = new TextureRegion(texture, 1, 201, 18, 9);
        planeDown.flip(false, true);

        plane = new TextureRegion(texture, 20, 201, 18, 9);
        plane.flip(false, true);

        planeUp = new TextureRegion(texture, 39, 201, 18, 9);
        planeUp.flip(false, true);

        TextureRegion[] birdsMove = {planeDown, plane, planeUp};
        planeAnimation = new Animation(0.06f,birdsMove);
        planeAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        partUp = new TextureRegion(texture, 110, 185, 26, 12 );

        partDown = new TextureRegion(partUp);
        partDown.flip(false, true);

        bar = new TextureRegion(texture,87, 185, 22, 8);
        bar.flip(false, true);

        dead = Gdx.audio.newSound(Gdx.files.internal("data/dead.wav"));
        flap = Gdx.audio.newSound(Gdx.files.internal("data/flap.wav"));
        coin = Gdx.audio.newSound(Gdx.files.internal("data/coin.wav"));

        font = new BitmapFont(Gdx.files.internal("data/text.fnt"));
        font.setScale(.25f,-.25f);
        shadow = new BitmapFont(Gdx.files.internal("data/shadow.fnt"));
        shadow.setScale(.25f, -.25f);

        pref = Gdx.app.getPreferences("FlappyPlane");
        if(!pref.contains("highScore")){
            pref.putInteger("highScore", 0);
        }

    }

    public static void dispose(){
            texture.dispose();
            dead.dispose();
            flap.dispose();
            coin.dispose();
            font.dispose();
            shadow.dispose();

        }

    // Ролучает на вход значение для hishScore и сохраняет в файл
    public static void setHighScore(int val) {
        pref.putInteger("highScore", val);
        pref.flush();
    }

    // Возвращает текущее значение hishScore
    public static int getHighScore() {
        return pref.getInteger("highScore");
    }
}
