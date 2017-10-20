package level;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import main.Main;

public class LoadGame
{
	public static void loadGame(String filename)
	{
		try
		{
			filename = "src/saves/" + filename + ".save";
			//load level
			Level level = null;
			FileInputStream f = new FileInputStream(filename);
			ObjectInputStream o = new ObjectInputStream(f);
			try 
			{
				level = (Level)o.readObject();
			} catch (ClassNotFoundException e) 
			{
				System.err.println(e);
			}
			o.close();
			f.close();
			//load game
			level.load();
			Main.level = level;
		}
		catch(IOException err)
		{
			System.out.println(err);
		}
	}
}
