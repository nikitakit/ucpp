package ucpp.utils;

public class Console
{
	public static void main(String[] args)
	{
		try
		{
			if (args.length > 1)
			{
				if (args[0] == "upload" && args.length >= 3)
				{
					Uploader.Upload(args[1], Integer.parseInt(args[2]));
					System.out.println("Uploaded");
					return;
				}
			}
		}
		catch (Exception e1)
		{
			e1.printStackTrace();
		}
		System.out.println("you need args, try upload path teamnumber");
	}
}
