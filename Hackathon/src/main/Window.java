package main;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryUtil;

import misc.Defines;
import misc.Settings;
import misc.Utils;

public class Window 
{
	private int width, height;
	private float widthRatio, heightRatio;
	private long windowID;
	private String title;
	private GLFWVidMode vidmode;
	private long cursor;
	
	public void setImage(String filename)
	{
		IntBuffer w = MemoryUtil.memAllocInt(1);
		IntBuffer h = MemoryUtil.memAllocInt(1);
		IntBuffer comp = MemoryUtil.memAllocInt(1);
		ByteBuffer png = Utils.ioResourceToByteBuffer(filename, 64 * 1024);
		ByteBuffer pixels = STBImage.stbi_load_from_memory(png, w, h, comp, 0);
		GLFWImage image = GLFWImage.malloc().set(w.get(0), h.get(0), pixels);
		cursor = GLFW.glfwCreateCursor(image, 0, 8);
		GLFW.glfwSetCursor(windowID, cursor);
	}
	
	public long getID()
	{
		return windowID;
	}
	
	public boolean shouldClose()
	{
		return GLFW.glfwWindowShouldClose(windowID);
	}
	
	public void setSize(int width, int height)
	{
		resize(width, height);
	}
	
	public void setWidth(int width)
	{
		resize(width, height);
	}
	
	public void setHeight(int height)
	{
		resize(width, height);
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public void setTitle(String title)
	{
		this.title = title;
		GLFW.glfwSetWindowTitle(windowID, title);
	}
	
	public String getTitle()
	{
		return title;
	}
	
	private void resize(int width, int height)
	{
		this.width = width;
		this.height = height;
		widthRatio = width / Settings.windowWidth;
		heightRatio = height / Settings.windowHeight;
		Defines.widthRatio = widthRatio;
		Defines.heightRatio = heightRatio;
		GLFW.glfwSetWindowSize(windowID, width, height);
		center();
	}
	
	private void center()
	{
		GLFW.glfwSetWindowPos(windowID, (vidmode.width() - width) / 2, (vidmode.height() - height) / 2);
	}
	
	public void clear()
	{
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT);
	}
	
	public void update()
	{
		GLFW.glfwPollEvents();
	}
	
	public void render()
	{
		GLFW.glfwSwapBuffers(windowID);
	}
	
	public Window(int width, int height, String title)
	{
		vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
		GLFW.glfwDefaultWindowHints();
		this.title = title;
		
		if(Settings.borderless)
		{
			GLFW.glfwWindowHint(GLFW.GLFW_DECORATED, GL11.GL_FALSE);
			GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GL11.GL_FALSE);
			GLFW.glfwWindowHint(GLFW.GLFW_RED_BITS, vidmode.redBits());
			GLFW.glfwWindowHint(GLFW.GLFW_GREEN_BITS, vidmode.greenBits());
			GLFW.glfwWindowHint(GLFW.GLFW_BLUE_BITS, vidmode.blueBits());
			GLFW.glfwWindowHint(GLFW.GLFW_REFRESH_RATE, vidmode.refreshRate());
			this.width = vidmode.width();
			this.height = vidmode.height();
			windowID = GLFW.glfwCreateWindow(this.width, this.height, this.title, 0, 0);
		}
		else if(Settings.windowed)
		{
			
		}
		else if(Settings.fullscreen)
		{
			
		}
		
		GLFW.glfwMakeContextCurrent(windowID);
		GLFW.glfwFocusWindow(windowID);
	}
}
