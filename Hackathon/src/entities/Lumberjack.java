package entities;

import org.joml.Vector3f;
import graphics.Sprite;
import graphics.Texture;
import main.Main;
import misc.Utils;

public class Lumberjack extends Entity
{
	private void attack()
	{
		Entity target = Main.level.getClosestForest(this.getPosition());
		
		if(target == null)
			target = Main.level.player;
			
		if(Utils.dist(this.getPosition().x, target.getPosition().x) < 2.0f)
		{
			super.attack(target);
		}
		else
			super.moveTowardsEntity(target);
	}
	
	public void update()
	{
		if(health <= 0)
			super.dead = true;;
		attack();
	}
	
	public Lumberjack(Vector3f pos) 
	{
		super(new Sprite(new Texture("/images/sprLumberjack1.png")), 20.0f, 0.5f, 4.0f, 0.1f);
		this.setPosition(pos);
		this.id = "lumberjack";
	}
}
