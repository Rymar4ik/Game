package com.diplomprogect.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.diplomprogect.game.Diplom;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Clone FlappyBird";
        config.width = 272;
        config.height = 408;
        new LwjglApplication(new Diplom(), config);
	}
}
