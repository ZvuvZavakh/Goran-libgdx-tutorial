package my.app.goran.tutorial;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import my.app.goran.tutorial.screens.GameScreen;

public class Demo extends Game {
	private Screen gameScreen = new GameScreen();
	
	@Override
	public void create () {
		setScreen(gameScreen);
		System.out.println(Gdx.graphics.getDisplayMode());
	}
}
