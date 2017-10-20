package misc;

import java.nio.ByteBuffer;

import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.system.MemoryUtil;

import main.Main;

public class Assets 
{
	public static void init()
	{
		ByteBuffer fonts = null;
		fonts = Utils.ioResourceToByteBuffer("/fonts/Consolas.ttf", 450 * 1024);
		NanoVG.nvgCreateFontMem(Main.vg, "Consolas", fonts, 0);
		
		MemoryUtil.memFree(fonts);
	}
}
