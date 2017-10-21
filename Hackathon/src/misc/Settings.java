package misc;

import org.lwjgl.glfw.GLFW;

public class Settings 
{
	public static boolean debugMode;
	public static boolean fullscreen;
	public static boolean windowed;
	public static boolean borderless;
	public static int windowWidth;
	public static int windowHeight;
	public static float cameraSpeed;
	//keys
	public static int moveLeft = GLFW.GLFW_KEY_A;
	public static int moveRight = GLFW.GLFW_KEY_D;
	public static int moveUp = GLFW.GLFW_KEY_W;
	public static int moveDown = GLFW.GLFW_KEY_S;
	public static int sprint = GLFW.GLFW_KEY_LEFT_SHIFT;
	public static int jump = GLFW.GLFW_KEY_SPACE;
	
	public static void init()
	{
		debugMode = false;
		fullscreen = false;
		windowed = false;
		borderless = true;
		windowWidth = 640;
		windowHeight = 640;
		cameraSpeed = 0.1f;
	}
}
