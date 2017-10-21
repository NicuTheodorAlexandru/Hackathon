package entities;

import graphics.Sprite;
import main.Main;

public class Tree extends Entity
{
	private int level;
	private long frame;
	private long growTime;
	
	public int getLevel()
	{
		return level;
	}
	
	public void update()
	{
		if(frame + growTime > Main.frame)
		{
			grow();
			frame = Main.frame;
		}
	}
	
	private void grow()
	{
		this.health += 5.0f;
		this.defence += 0.5f;
		this.damage += 1.5f;
		this.speed += 0.1f;
		level++;
		growTime += growTime / 2;
	}
	
	public Tree(Sprite sprite, float health, float defence, float damage, float speed) 
	{
		super(sprite, health, defence, damage, speed);
		level = 1;
		growTime = 1000;
		frame = Main.frame;
	}	
}
