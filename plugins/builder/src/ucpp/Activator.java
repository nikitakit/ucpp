package ucpp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IProject;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin
{

	// The plug-in ID
	public static final String PLUGIN_ID = "ucpp"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;

	public static MessageConsoleStream out = findConsole("Universal C++").newMessageStream();


	/**
	 * The constructor
	 */
	public Activator()
	{
	}
	
	private static MessageConsole findConsole(String name) {
	      ConsolePlugin plugin = ConsolePlugin.getDefault();
	      IConsoleManager conMan = plugin.getConsoleManager();
	      IConsole[] existing = conMan.getConsoles();
	      for (int i = 0; i < existing.length; i++)
	         if (name.equals(existing[i].getName()))
	            return (MessageConsole) existing[i];
	      //no console found, so create a new one
	      MessageConsole myConsole = new MessageConsole(name, null);
	      conMan.addConsoles(new IConsole[]{myConsole});
	      return myConsole;
	   }

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(BundleContext context) throws Exception
	{
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
	 * )
	 */
	public void stop(BundleContext context) throws Exception
	{
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static Activator getDefault()
	{
		return plugin;
	}

	public static int GetTeamNumber(IProject project)
	{
		// export DEPLOY_IP=10.4.51.2
		String f = null;
		try
		{
			f = GetFileContents(project.getLocation().toString() + "/.ucpp");
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
		Pattern p = Pattern.compile("export DEPLOY_IP=10\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.2");
		Matcher m = p.matcher(f);
		m.find();
		int r = Integer.valueOf(m.group(1))*100;
		r += Integer.valueOf(m.group(2));
		return r;
	}
	
	public static int GetTeamNumber()
	{
		return getDefault().getPreferenceStore().getInt("Team Number");
	}

	public static String GetFileContents(String fileName)
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
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
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

	public static String GetDefaultFile()
	{
		return getDefault().getPreferenceStore().getString("File");
	}

}
