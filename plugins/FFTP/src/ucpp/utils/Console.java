package ucpp.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Console
{
	public static void main(String[] args)
	{
		try
		{
			if (args.length > 1)
			{
				if (args[0].equals("upload") && args.length >= 3)
				{
					if (args[2].equals("ucpp") || args[2].equals("u") || args[2].equals("-u"))
						Uploader.Upload(args[1], GetTeamNumber());
					else
						Uploader.Upload(args[1], Integer.parseInt(args[2]));
					System.out.println("Uploaded");
					return;
				}
			}
			else
				System.out.println("you need args, try 'upload file/path ucpp'");
		}
		catch (Exception e1)
		{
			System.out.println("ERROR!");
			e1.printStackTrace();
		}
	}
	
	public static int GetTeamNumber()
	{
		String f = null;
		try
		{
			f = GetFileContents(".ucpp");
		}
		catch (Exception e)
		{
			try
			{
				f = GetFileContents(".."+File.separator+".."+File.separator+".."+File.separator+".ucpp");
			}
			catch (Exception xe)
			{
				try
				{
					f = GetFileContents(System.getProperty("user.home")+File.separator+".ucpp"+File.separator+"settings");
				}
				catch (Exception ex)
				{
					System.out.println("using team 0, invalid find");
					return 0;
				}
			}
		}
		Pattern p = Pattern.compile("export DEPLOY_IP=10\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.2");
		Matcher m = p.matcher(f);
		m.find();
		int r = Integer.valueOf(m.group(1))*100;
		r += Integer.valueOf(m.group(2));
		System.out.println("using team "+r+" to upload");
		return r;
	}
	
	public static String GetFileContents(String fileName) throws Exception
	{
		File file = new File(fileName);
		StringBuffer contents = new StringBuffer();
		BufferedReader reader = null;

		try
		{
			reader = new BufferedReader(new FileReader(file));
			String text = null;
			
			while ((text = reader.readLine()) != null)
			{
				contents.append(text).append(System.getProperty("line.separator"));
			}
		}
		finally
		{
			try
			{
				if (reader != null)
				{
					reader.close();
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		return contents.toString();
	}
}
