package structures;

import entities.Entity;
import graphics.Sprite;

public class Wall extends Entity
{
	public Wall(Sprite sprite, float health, float defence) 
	{
		super(sprite, health, defence, 0.0f, 0.0f);
	}
}
