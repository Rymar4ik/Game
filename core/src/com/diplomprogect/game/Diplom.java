package com.diplomprogect.game;

// импорти
        import com.badlogic.gdx.Game;
        import com.badlogic.gdx.Gdx;
        import com.diplomprogect.gamehelpers.AssetLoader;
        import com.diplomprogect.screens.GameScreen;

public class Diplom extends Game {

    @Override
    public void create () {

        // логиювання
        Gdx.app.log("DiplomGame", "created");
        // ініціалізація текстур,звуків.
        AssetLoader.load();
        //встановлюемо екран який повинен показуватися при запуску програми
        setScreen(new GameScreen());
    }

    @Override
    public void dispose() {
        //методи с позначкою @Override е методами які уніслідувалися від класу Game, без них робота гри неможлива

        // вигруження всі текстур и звуків с JVM
        super.dispose();
        AssetLoader.dispose();
    }
}
