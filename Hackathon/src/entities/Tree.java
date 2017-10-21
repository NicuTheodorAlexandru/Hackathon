package entities;

import org.joml.Vector3f;
import graphics.Sprite;
import graphics.Texture;
import level.Resource;
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
	
	private void transport()
	{
		float dist = Main.level.player.getPosition().x - this.getPosition().x;
		if(dist < 0)
		{
			if(dist > -1f)
			{
				Main.level.player.deposit(carry);
				this.carry = 0;
			}
			else
			{
				this.move(new Vector3f(-this.speed, 0, 0));
			}
		}
		else
		{
			if(dist < 1f)
			{
				Main.level.player.deposit(carry);
				this.carry = 0;
			}
			else
			{
				this.move(new Vector3f(this.speed, 0, 0));
			}
		}
	}
	
	private void gatherResources()
	{
		if(this.carry >= this.carryLimit)
			transport();
		else
		{
			Resource resource = Main.level.getClosestResource(this.getPosition());
			if(resource == null)
				return;
			Vector3f pos = resource.getSprite().getModel().getPosition();
			float dir = pos.x - this.getPosition().x;
			if(dir < 0)
			{
				if(dir > -1f)
					resource.harvest(this);
				else
					this.move(new Vector3f(-this.speed, 0, 0));
			}
			else
			{
				if(dir < 1f)
					resource.harvest(this);
				else
					this.move(new Vector3f(this.speed, 0, 0));
			}
		}
	}
	
	public void update()
	{
		if(level > 1)
			gatherResources();
		if(frame + growTime < Main.frame)
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
		this.speed += 0.01f;
		level++;
		growTime += growTime / 2;
		Vector3f pos = new Vector3f(sprite.getModel().getPosition());
		if(level == 2)
		{
			sprite = new Sprite(new Texture("/images/sprTree1.png"));
		} else if(level == 3)
		{
			sprite = new Sprite(new Texture("/images/sprTree2.png"));
		} else if(level == 4)
		{
			sprite = new Sprite(new Texture("/images/sprTree3.png"));
		} else if(level == 5)
		{
			sprite = new Sprite(new Texture("/images/sprTree4.png"));
		}
		sprite.getModel().setPosition(pos);
	}
	
	public Tree(Sprite sprite, float health, float defence, float damage, float speed) 
	{
		super(sprite, health, defence, damage, speed);
		level = 1;
		growTime = 300;
		frame = Main.frame;
	}	
}
