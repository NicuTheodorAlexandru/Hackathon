package level;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector3f;
import org.lwjgl.openal.AL11;

import entities.Entity;
import entities.MotherTree;
import graphics.HUD;
import graphics.Sprite;
import graphics.Texture;
import main.Main;
import sound.SoundBuffer;
import sound.SoundListener;
import sound.SoundSource;

public class Level 
{
	private HUD hud;
	private List<Entity> entities;
	private MotherTree player;
	
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
		player.render();
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
		hud.update();
	}
	
	private void setupSounds()
	{
		SoundBuffer buffer = new SoundBuffer("/art/Demon-Hunter-Someone-To-Hate.ogg");
		Main.soundManager.addSoundBuffer(buffer);
		SoundSource source = new SoundSource(true, false);
		source.setPosition(new Vector3f(0, 0, 0));
		source.setBuffer(buffer.getBufferID());
		Main.soundManager.addSoundSource("Music", source);
		Main.soundManager.playSoundSource("Music");
	}
	
	public Level()
	{
		hud = new HUD();
		entities = new ArrayList<>();
		player = new MotherTree(new Sprite(new Texture("/images/sprMotherTree.png")), 100, 10, 1, 0);
		player.setPosition(new Vector3f(0, 0, 0));
		Main.soundManager.setListener(new SoundListener(new Vector3f(0, 0, 0)));
		Main.soundManager.setAttenuationModel(AL11.AL_EXPONENT_DISTANCE);
		setupSounds();
	}
}
