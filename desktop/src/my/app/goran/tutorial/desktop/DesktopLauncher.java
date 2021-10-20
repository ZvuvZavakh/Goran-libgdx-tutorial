package my.app.goran.tutorial.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import my.app.goran.tutorial.Demo;
import my.app.goran.tutorial.config.GameConfig;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.width = GameConfig.WIDTH;
		config.height = GameConfig.HEIGHT;

		new LwjglApplication(new Demo(), config);
	}
}
