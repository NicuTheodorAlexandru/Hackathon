package level;

import org.joml.Vector3f;
import org.lwjgl.openal.AL11;

import graphics.HUD;
import main.Main;
import sound.SoundBuffer;
import sound.SoundListener;
import sound.SoundSource;

public class Level 
{
	private HUD hud;
	
	public void load()
	{
		
	}
	
	public void cleanup()
	{
		
	}
	
	public void render()
	{
		
		hud.render();
	}
	
	public void update()
	{
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
		Main.soundManager.setListener(new SoundListener(new Vector3f(0, 0, 0)));
		Main.soundManager.setAttenuationModel(AL11.AL_EXPONENT_DISTANCE);
		setupSounds();
	}
}
