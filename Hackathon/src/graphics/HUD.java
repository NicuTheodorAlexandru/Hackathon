package graphics;

import gui.guiButton;
import gui.guiSprite;
import main.Main;
import misc.Utils;

public class HUD 
{
	private guiButton plantTree;
	private Text essence;
	
	public void load()
	{
		
	}
	
	public void update()
	{
		plantTree.update();
		if(plantTree.getActivated())
		{
			Main.level.player.plantTree();
		}
		int amount = (int)Main.level.player.getEssence();
		essence.setText(Integer.toString(amount) + " Essence");
	}
	
	public void render()
	{
		plantTree.render();
		essence.render();
	}
	
	public HUD()
	{
		plantTree = new guiButton(0, Main.window.getHeight() - 30,  new guiSprite(Utils.getNanoVGImage("/images/sprPlantTree.png", 32 * 1024)));
		essence = new Text(0, 0, "blabla");
	}
}
