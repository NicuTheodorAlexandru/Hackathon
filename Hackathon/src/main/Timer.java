package main;

public class Timer 
{
	private double lastLoopTime;
	
	public void init()
	{
		lastLoopTime = getTime();
	}
	
	public double getElapsedTime()
	{
		double time = getTime();
		float elapsedTime = (float)(time - lastLoopTime);
		lastLoopTime = time;
		
		return elapsedTime;
	}
	
	public double getTime()
	{
		return System.nanoTime() / 1000000000.0d;
	}
	
	public double getLastLoopTime()
	{
		return lastLoopTime;
	}
}
