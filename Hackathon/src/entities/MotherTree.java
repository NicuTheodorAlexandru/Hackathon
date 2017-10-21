package entities;

import org.joml.Vector3f;

import graphics.Sprite;
import graphics.Texture;
import main.Main;

public class MotherTree extends Entity
{
	private float essence;
	private float saplingCost;
	
	public void deposit(float amount)
	{
		essence += amount;
	}
	
	public float getEssence()
	{
		return essence;
	}
	
	public void plantTree()
	{
		if(essence >= saplingCost)
		{
			essence -= saplingCost;
			Main.level.addEntity(new Tree(new Sprite(new Texture("/images/sprCone.png")), 1, 0, 0.5f, 0.01f));
			Main.level.getLastEntity().setPosition(new Vector3f(4, -4, 0));
		}
	}
	
	public MotherTree(Sprite sprite, float health, float defence, float damage, float speed) 
	{
		super(sprite, health, defence, damage, speed);
		essence = 10.0f;
		saplingCost = 5.0f;
		this.id = "mothertree";
	}
}
