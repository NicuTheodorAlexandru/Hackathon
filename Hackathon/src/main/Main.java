package main;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.nanovg.NanoVGGL2;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import graphics.Camera;
import graphics.MenuHUD;
import graphics.Renderer;
import input.Keyboard;
import input.Mouse;
import level.Level;
import misc.Assets;
import misc.Defines;
import misc.Misc;
import misc.Settings;
import sound.SoundManager;

public class Main 
{
	public static long vg;
	public static boolean running;
	private Timer timer;
	public static Window window;
	public static Camera camera;
	public static Renderer renderer;
	public static SoundManager soundManager;
	public static Level level;
	private Keyboard keyboard;
	private Mouse mouse;
	private MenuHUD menuHud;
	public static long frame;
	public static int frames;
	public static int updates;
	
	private void init()
	{
		frame = 0;
		frames = updates = 0;
		GLFW.glfwInit();
		Settings.init();
		Defines.init();
		window = new Window(Settings.windowWidth, Settings.windowHeight, Defines.title);
		GL.createCapabilities();
		vg = NanoVGGL2.nvgCreate(0);
		Assets.init();
		GL11.glClearColor(0.0f, 0.192f, 0.325f, 1.0f);
		flags();
		
		mouse = new Mouse(window);
		keyboard = new Keyboard(window);
		
		camera = new Camera();
		renderer = new Renderer();
		soundManager = new SoundManager();
		level = null;
		menuHud = new MenuHUD();
		
		timer = new Timer();
		timer.init();
	}
	
	private void sync()
	{
		float loopSlot = 1.0f / (float)Defines.FRAMES_PER_SECOND;
		double endTime = timer.getLastLoopTime() + loopSlot;
		
		while(timer.getTime() < endTime)
		{
			try
			{
				Thread.sleep(1);
			}
			catch(InterruptedException err)
			{
				System.err.println(err);
			}
		}
	}
	
	private void flags()
	{
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	}
	
	private void shutdown()
	{
		GLFW.glfwTerminate();
		renderer.cleanup();
		soundManager.cleanup();
		level.cleanup();
	}
	
	public void start()
	{
		running = true;
		
		init();
		run();
	}
	
	private void render()
	{
		window.clear();
		
		if(level != null)
			level.render();
		else
			menuHud.render();
		
		renderer.render();
		window.render();
	}
	
	private void update()
	{
		window.update();
		Mouse.update();
		Misc.update();
		
		if(level != null)
			level.update();
		else
			menuHud.update();
		soundManager.updateListener(camera);
	}
	
	private void run()
	{
		float elapsedTime;
		float accumulator = 0.0f;
		float interval = 1.0f / (float)Defines.FRAMES_PER_SECOND;
		float lastFps = 0;
		frames = 0;
		updates = 0;
		
		while(running)
		{
			if(window.shouldClose())
				running = false;
			mouse.input();
			keyboard.update();
			
			elapsedTime = (float)timer.getElapsedTime();
			accumulator += elapsedTime;
			
			while(accumulator >= interval)
			{
				accumulator -= interval;
				update();
				updates++;
				frame++;
			}
			
			if(Defines.debugMode && timer.getLastLoopTime() - lastFps > 1)
			{
				lastFps = (float)timer.getLastLoopTime();
				updates = 0;
				frames = 0;
				System.out.println("Updates: " + updates + "; Frames: " + frames);
			}
			
			render();
			frames++;
			sync();
		}
		
		shutdown();
	}
	
	public static void main(String[] args)
	{
		Main main = new Main();
		main.start();
	}
}
