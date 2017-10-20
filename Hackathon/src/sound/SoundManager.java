package sound;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALC10;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.system.MemoryUtil;
import graphics.Camera;
import graphics.Renderer;

public class SoundManager 
{
	private long device;
	private long context;
	private SoundListener listener;
	private final List<SoundBuffer> soundBufferList;
	private final Map<String, SoundSource> soundSourceMap;
	private final Matrix4f cameraMatrix;
	
	public void cleanup()
	{
		for(SoundSource source: soundSourceMap.values())
		{
			source.cleanup();
		}
		soundSourceMap.clear();
		for(SoundBuffer buffer: soundBufferList)
		{
			buffer.cleanup();
		}
		soundBufferList.clear();
		if(context != MemoryUtil.NULL)
			ALC10.alcDestroyContext(context);
		if(device != MemoryUtil.NULL)
			ALC10.alcCloseDevice(device);
	}
	
	public void setAttenuationModel(int model)
	{
		AL10.alDistanceModel(model);
	}
	
	public void updateListener(Camera camera)
	{
		Renderer.transformation.getViewMatrix(camera, cameraMatrix);
		listener.setPosition(camera.getPosition());
		Vector3f at = new Vector3f();
		cameraMatrix.positiveZ(at).negate();
		Vector3f up = new Vector3f();
		cameraMatrix.positiveY(up);
		listener.setOrientation(at, up);
	}
	
	public void setListener(SoundListener listener)
	{
		this.listener = listener;
	}
	
	public SoundListener getSoundListener()
	{
		return listener;
	}
	
	public void addSoundBuffer(SoundBuffer buffer)
	{
		soundBufferList.add(buffer);
	}
	
	public void removeSoundSource(String name)
	{
		soundSourceMap.remove(name);
	}
	
	public void playSoundSource(String name)
	{
		SoundSource source = soundSourceMap.get(name);
		if(source != null && !source.isPlaying())
			source.play();
	}
	
	public SoundSource getSoundSource(String name)
	{
		return soundSourceMap.get(name);
	}
	
	public void addSoundSource(String name, SoundSource source)
	{
		soundSourceMap.put(name, source);
	}
	
	public void init()
	{
		String deviceName = ALC10.alcGetString(0, ALC10.ALC_DEFAULT_DEVICE_SPECIFIER);
		device = ALC10.alcOpenDevice(deviceName);
		if(device == MemoryUtil.NULL)
			System.err.println("Could not open default openal device");
		int[] attributes = {0};
		context = ALC10.alcCreateContext(device, attributes);
		if(context == MemoryUtil.NULL)
			System.err.println("Could not create openal context");
		ALC10.alcMakeContextCurrent(context);
		ALCCapabilities deviceCaps = ALC.createCapabilities(device);
		AL.createCapabilities(deviceCaps);
	}
	
	public SoundManager()
	{
		soundBufferList = new ArrayList<>();
		soundSourceMap = new HashMap<>();
		cameraMatrix = new Matrix4f();
		init();
	}
}
