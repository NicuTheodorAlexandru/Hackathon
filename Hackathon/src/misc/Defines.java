package misc;

public class Defines 
{
	public static int FRAMES_PER_SECOND;
	public static boolean debugMode;
	public static float widthRatio;
	public static float heightRatio;
	public static String title;
	
	public static void init()
	{
		FRAMES_PER_SECOND = 60;
		debugMode = false;
		widthRatio = 1.0f;
		heightRatio = 1.0f;
		title = "Hackathon";
	}
}
