package entities;

import graphics.Sprite;
import main.Main;

public class MotherTree extends Entity
{
	private float essence;
	private float saplingCost;
	
	public void plantTree()
	{
		if(essence >= saplingCost)
		{
			essence -= saplingCost;
			Main.level.addEntity(new Tree(sprite, essence, essence, essence, essence));
		}
	}
	
	public MotherTree(Sprite sprite, float health, float defence, float damage, float speed) 
	{
		super(sprite, health, defence, damage, speed);
		essence = 10.0f;
	}
}
