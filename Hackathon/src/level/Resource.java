package level;

import entities.Entity;
import graphics.Renderer;
import graphics.Sprite;
import gui.guiTooltip;

public class Resource 
{
	private float harvestAmount;
	private float resourceAmount;
	private boolean depleted;
	private Sprite sprite;
	private guiTooltip amountLeft;
	
	public void update()
	{
		amountLeft.update();
	}
	
	public void render()
	{
		Renderer.models.add(sprite.getModel());
		amountLeft.render();
	}
	
	public boolean getDepleted()
	{
		return depleted;
	}
	
	public void harvest(Entity entity)
	{
		float amount = harvestAmount;
		if(resourceAmount < harvestAmount)
			amount = resourceAmount;
		amount = entity.carry(amount);
		resourceAmount -= amount;
	}
	
	public Resource(Sprite sprite, float harvestAmount, float resourceAmount)
	{
		depleted = false;
		this.sprite = sprite;
		this.harvestAmount = harvestAmount;
		this.resourceAmount = resourceAmount;
	}
}
