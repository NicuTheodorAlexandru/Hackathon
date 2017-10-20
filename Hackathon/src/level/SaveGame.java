package level;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;

import main.Main;

public class SaveGame
{
	public static void saveGame()
	{
		String filename = "";
		filename += LocalDateTime.now().getHour();
		filename += ".";
		filename += LocalDateTime.now().getMinute();
		filename += ".";
		filename += LocalDateTime.now().getDayOfMonth();
		filename += ".";
		filename += LocalDateTime.now().getMonthValue();
		filename += ".";
		filename += LocalDateTime.now().getYear();
		SaveGame.saveGame(filename);
	}
	
	public static void saveGame(String filename)
	{
		try 
		{
			File file = new File("src/saves/" + filename + ".save");
			if(file.exists())
			{
				file.delete();
			}
			file.createNewFile();
			//save level
			FileOutputStream f = new FileOutputStream("src/saves/" + filename + ".save");
			ObjectOutputStream o = new ObjectOutputStream(f);
			o.writeObject(Main.level);
			o.flush();
			o.close();
			f.close();
		} catch (IOException e) 
		{
			System.out.println(e);
		}
	}
}
