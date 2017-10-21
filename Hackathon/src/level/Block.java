package level;

import org.joml.Vector3f;

import graphics.Renderer;
import graphics.Sprite;

public class Block 
{
	private boolean solid;
	private Sprite sprite;
	
	public void update()
	{
		
	}
	
	public void render()
	{
		Renderer.models.add(sprite.getModel());
	}
	
	public void setPosition(Vector3f position)
	{
		sprite.getModel().setPosition(position);
	}
	
	public Vector3f getPosition()
	{
		return sprite.getModel().getPosition();
	}
	
	public void move(Vector3f move)
	{
		sprite.getModel().movePosition(move);
	}
	
	public Sprite getSprite()
	{
		return sprite;
	}
	
	public boolean getSolid()
	{
		return solid;
	}
	
	public Block(Sprite sprite, boolean solid)
	{
		this.sprite = sprite;
		this.solid = solid;
	}
}
