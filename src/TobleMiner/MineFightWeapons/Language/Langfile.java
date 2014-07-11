package TobleMiner.MineFightWeapons.Language;

import java.io.File;
import java.io.InputStream;
import java.util.logging.Level;

import TobleMiner.MineFight.Util.IO.File.FileUtil;
import TobleMiner.MineFightWeapons.Main;

public class Langfile
{

	private final File langwpdir;
	private final File langmiscdir;
	
	public Langfile(File basedir)
	{
		File langdir = new File(basedir, "lang");
		langwpdir = new File(langdir, "weapon");
		if(!langwpdir.exists()) langwpdir.mkdirs();
		langmiscdir = new File(langdir, "misc");
		if(!langmiscdir.exists()) langmiscdir.mkdirs();
		String[] files = {"DE_de.lang","EN_uk.lang","EN_us.lang"};
		try
		{
			for(String s : files)
			{
				File langFile = new File(langwpdir, s);
				if(!(langFile.exists() && langFile.isFile()))
				{
					if(!langFile.isFile())
					{
						langFile.delete();
					}
					InputStream is = this.getClass().getResourceAsStream("Files/Misc/"+s);
					FileUtil.copyFromInputStreamToFileUtf8(langFile, is);
				}
				langFile = new File(langmiscdir, s);
				if(!(langFile.exists() && langFile.isFile()))
				{
					if(!langFile.isFile())
					{
						langFile.delete();
					}
					InputStream is = this.getClass().getResourceAsStream("Files/Weapons/"+s);
					FileUtil.copyFromInputStreamToFileUtf8(langFile, is);
				}
			}
		}
		catch(Exception ex)
		{
			Main.logger.log(Level.SEVERE, "Could not write language files. Make sure the server has write permissions for the plugin folder:");
			ex.printStackTrace();
		}
	}
	
	public File getLangWeapons()
	{
		return new File(this.langwpdir, Main.config.getLang());
	}
	
	public File getLangMisc()
	{
		return new File(this.langmiscdir, Main.config.getLang());
	}
}