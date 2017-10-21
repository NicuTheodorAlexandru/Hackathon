package graphics;

import org.joml.Vector3f;

import gui.guiButton;
import gui.guiSprite;
import main.Main;
import misc.Utils;
import structures.Wall;

public class HUD 
{
	private guiButton plantTree;
	private guiButton buildWall;
	private Text essence;
	private boolean wallExists;
	
	public void update()
	{
		plantTree.update();
		if(plantTree.getActivated())
		{
			Main.level.player.plantTree();
		}
		int amount = (int)Main.level.player.getEssence();
		essence.setText(Integer.toString(amount) + " Essence");
		buildWall.update();
		if(buildWall.getActivated() && !wallExists)
		{
			wallExists = true;
			Wall w = new Wall(new Sprite(new Texture("/images/sprWall.png")), 100, 1);
			w.setPosition(new Vector3f(20, 0, 0));
			Main.level.addWall(w);
		}
	}
	
	public void render()
	{
		plantTree.render();
		essence.render();
	}
	
	public HUD()
	{
		plantTree = new guiButton(0, Main.window.getHeight() - 30,  new guiSprite(Utils.getNanoVGImage("/images/sprPlantTree.png", 32 * 1024)));
		buildWall = new guiButton(40, Main.window.getHeight() - 30, new guiSprite(Utils.getNanoVGImage("/images/sprHammer.png", 32 * 1024)));
		essence = new Text(0, 0, "");
		wallExists = false;
	}
}
