package entities;

import org.joml.Vector3f;

import graphics.Sprite;
import input.Keyboard;
import main.Main;
import misc.Settings;

public class Player 
{
	private Sprite sprite;
	private float speed = 0.1f;
	private float sprint = 2.0f;
	
	public void render()
	{
		
	}
	
	public void update()
	{
		move();
		
		camera();
	}
	
	private void move()
	{
		Vector3f move = new Vector3f();
		float speed = this.speed;
		if(Keyboard.getKey(Settings.sprint))
			speed *= this.sprint;
		if(Keyboard.getKey(Settings.moveLeft)) 
        {
            move.x = -speed;
        } else if (Keyboard.getKey(Settings.moveRight)) 
        {
            move.x = speed;
        }
        if (Keyboard.getKey(Settings.moveDown)) {
            move.y = -speed;
        } else if (Keyboard.getKey(Settings.moveUp)) 
        {
            move.y = speed;
        }
        sprite.getModel().movePosition(move);
	}
	
	private void camera()
	{
		Main.camera.setPosition(new Vector3f(sprite.getModel().getX(), sprite.getModel().getY(), Main.camera.getPosition().z));
	}
	
	public float getY()
	{
		return sprite.getModel().getY();
	}
	
	public float getX()
	{
		return sprite.getModel().getX();
	}
	
	public Sprite getSprite()
	{
		return sprite;
	}
	
	public Player(float x, float y, Sprite sprite)
	{
		this.sprite = sprite;
		sprite.getModel().setX(x);
		sprite.getModel().setY(y);
		sprite.getModel().setZ(0.0f);
	}
}
