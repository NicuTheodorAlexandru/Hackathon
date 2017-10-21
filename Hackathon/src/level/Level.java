package level;

import java.util.ArrayList;
import java.util.List;
import org.joml.Vector3f;
import org.lwjgl.openal.AL11;
import entities.Entity;
import entities.Lumberjack;
import entities.MotherTree;
import graphics.HUD;
import graphics.Renderer;
import graphics.Sprite;
import graphics.Texture;
import main.Main;
import sound.SoundBuffer;
import sound.SoundListener;
import sound.SoundSource;
import structures.Wall;

public class Level 
{
	private HUD hud;
	private List<Entity> entities;
	private List<Block> blocks;
	private List<Resource> resources;
	private List<Wall> walls;
	public MotherTree player;
	private Sprite[] background;
	private Sprite bck;
	private int enemies;
	private long lastSpawn;
	private int enemiesToSpawn;
	private long gracePeriod;
	
	private void background()
	{
		int frame = Main.frames % 35;
		frame /= 5;
		bck = background[frame];
		bck.getModel().setPosition(new Vector3f(Main.camera.getPosition().x - 14, Main.camera.getPosition().y - 6, 0));
	}
	
	public void addWall(Wall wall)
	{
		walls.add(wall);
	}
	
	public Entity getClosestEnemy(Vector3f pos)
	{
		float closest = Float.POSITIVE_INFINITY;
		Entity entity = null;
		
		for(Entity e: entities)
		{
			if(e.getID() != "lumberjack")
				continue;
			float dist = e.getPosition().x - pos.x;
			if(dist < 0)
				dist *= -1;
			if(dist < closest)
			{
				closest = dist;
				entity = e;
			}
		}
		return entity;
	}
	
	public Entity getClosestForest(Vector3f pos)
	{
		float closest = Float.POSITIVE_INFINITY;
		Entity entity = null;
		
		for(Entity e: entities)
		{
			if(e.getID() != "mothertree" && e.getID() != "tree")
				continue;
			float dist = e.getPosition().x - pos.x;
			if(dist < 0)
				dist *= -1;
			if(dist < closest)
			{
				closest = dist;
				entity = e;
			}
		}
		
		return entity;
	}
	
	public Resource getClosestResource(Vector3f pos)
	{
		float closest = Float.POSITIVE_INFINITY;
		Resource res = null;
		
		for(Resource r: resources)
		{
			float dist = r.getSprite().getModel().getPosition().x - pos.x;
			if(dist < 0)
				dist *= -1;
			if(dist < closest)
			{
				closest = dist;
				res = r;
			}
		}
		
		return res;
	}
	
	public Resource getLastResource()
	{
		return resources.get(resources.size() - 1);
	}
	
	public void addResource(Resource res)
	{
		resources.add(res);
	}
	
	public List<Resource> getResources()
	{
		return resources;
	}
	
	public Entity getLastEntity()
	{
		return entities.get(entities.size() - 1);
	}
	
	public void addEntity(Entity entity)
	{
		entities.add(entity);
	}
	
	public void load()
	{
		
	}
	
	public void cleanup()
	{
		
	}
	
	public void render()
	{
		Renderer.models.add(bck.getModel());
		for(int i = 0; i < blocks.size(); i++)
		{
			Block block = blocks.get(i);
			block.render();
		}
		for(int i = 0; i < walls.size(); i++)
		{
			Wall w = walls.get(i);
			w.render();
		}
		player.render();
		for(int i = 0; i < resources.size(); i++)
		{
			Resource res = resources.get(i);
			res.render();
		}
		for(int i = 0; i < entities.size(); i++)
		{
			Entity entity = entities.get(i);
			
			entity.render();
		}
		hud.render();
	}
	
	public void update()
	{
		Main.camera.update();
		background();
		spawnEnemies();
		for(int i = 0; i < entities.size(); i++)
		{
			Entity entity = entities.get(i);
			entity.update();
			if(entity.isDead())
			{
				i--;
				entities.remove(entity);
			}
		}
		for(int i = 0; i < walls.size(); i++)
		{
			Wall w = walls.get(i);
			w.update();
		}
		for(int i = 0; i < resources.size(); i++)
		{
			Resource res = resources.get(i);
			res.update();
		}
		for(int i = 0; i < blocks.size(); i++)
		{
			Block block = blocks.get(i);
			block.update();
		}
		hud.update();
	}
	
	private void spawnEnemies()
	{
		float startingX = 50f;
		
		if(lastSpawn + gracePeriod < Main.frame)
		{
			if(Main.frame % 100 == 0)
			{
				Main.level.addEntity(new Lumberjack(new Vector3f(startingX, -6f, 0)));
				enemiesToSpawn--;
			}
		}
		if(enemiesToSpawn == 0)
		{
			lastSpawn = Main.frame;
			enemies++;
			enemiesToSpawn = enemies;
		}
	}
	
	public void addBlock(Block block)
	{
		blocks.add(block);
	}
	
	private void genWorld()
	{
		float start = -10.0f;
		int amount = 100;
		float x = start;
		for(int i = 1; i <= amount; i++)
		{
			this.addBlock(new Block(new Sprite(new Texture("/images/sprDirt.png")), true));
			blocks.get(blocks.size() - 1).setPosition(new Vector3f(x, -6, 0));
			x += blocks.get(blocks.size() - 1).getSprite().getModel().getSize();
			//x += 3.0f;
		}
		
		resources.add(new Resource(new Sprite(new Texture("/images/sprResource.png")), 0.1f, 1000.0f));
		this.getLastResource().getSprite().getModel().setPosition(new Vector3f(-10, 0, 0));
	}
	
	private void setupSounds()
	{
		SoundBuffer buffer = new SoundBuffer("/art/song.ogg");
		Main.soundManager.addSoundBuffer(buffer);
		SoundSource source = new SoundSource(true, false);
		source.setPosition(new Vector3f(0, 0, 0));
		source.setBuffer(buffer.getBufferID());
		Main.soundManager.addSoundSource("Music", source);
		Main.soundManager.playSoundSource("Music");
	}
	
	public Level()
	{
		lastSpawn = Main.frame;
		enemies = 0;
		gracePeriod = 5000;
		entities = new ArrayList<>();
		blocks = new ArrayList<>();
		walls = new ArrayList<>();
		resources = new ArrayList<>();
		background = new Sprite[8];
		background[0] = new Sprite(new Texture("/images/bck1.png"));
		background[1] = new Sprite(new Texture("/images/bck2.png"));
		background[2] = new Sprite(new Texture("/images/bck3.png"));
		background[3] = new Sprite(new Texture("/images/bck4.png"));
		background[4] = new Sprite(new Texture("/images/bck5.png"));
		background[5] = new Sprite(new Texture("/images/bck6.png"));
		background[6] = new Sprite(new Texture("/images/bck7.png"));
		background[7] = new Sprite(new Texture("/images/bck8.png"));
		for(int i = 0; i <= 7; i++)
			background[i].getModel().setScale(0.8f);
		bck = background[0];
		genWorld();
		player = new MotherTree(new Sprite(new Texture("/images/sprMotherTree.png")), 100, 10, 1, 0);
		player.setPosition(new Vector3f(0, -4, 0));
		Main.soundManager.setListener(new SoundListener(new Vector3f(0, 0, 0)));
		Main.soundManager.setAttenuationModel(AL11.AL_EXPONENT_DISTANCE);
		hud = new HUD();
		setupSounds();
	}
}
