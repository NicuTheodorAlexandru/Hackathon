package graphics;

import gui.guiButton;
import level.Level;
import main.Main;

public class MenuHUD 
{
	private guiButton newGame;
	private guiButton exitGame;
	private guiButton loadGame;
	
	public void update()
	{
		newGame.update();
		if(newGame.getActivated())
		{
			Main.level = new Level();
		}
		loadGame.update();
		if(loadGame.getActivated())
		{
			//load game here
		}
		exitGame.update();
		if(exitGame.getActivated())
		{
			Main.running = false;
		}
	}
	
	public void render()
	{
		
	}
	
	public MenuHUD()
	{
		newGame = new guiButton(300, 300, "New game");
		loadGame = new guiButton(300, 400, "Load game");
		exitGame = new guiButton(300, 500, "Exit game");
	}
}
