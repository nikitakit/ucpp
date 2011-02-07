package ucpp.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.eclipse.core.resources.IProject;

public class Ucpp
{
	public static void setup(String options, IProject project) throws Exception
	{
		exe("setup " + options, project);
	}

	public static void setup(IProject project)
	{
		SetupConfigDialog setupdlg = new SetupConfigDialog(project);
		setupdlg.open();
	}

	public static void init(int team, IProject project) throws Exception
	{
		exe("init -t " + String.valueOf(team), project);
	}

	public static void makefile(IProject project) throws Exception
	{
		exe("configure" + (OSValidator.isWindows() ? "winpy" : "py"), project);
	}

	private static void exe(String command, IProject project) throws Exception
	{
		System.out.println("executing '"+command+"'");
		if (OSValidator.isUnix() || OSValidator.isMac())
		{
			Runtime rt = Runtime.getRuntime();
			String path = System.getenv("PATH");
			if (!path.contains("ucpp"))
				path = System.getenv("HOME") + "/ucpp/ucpp:" + path;
			Process pr = rt.exec(new String[] { "ucpp -s " + command }, new String[] { "PATH=" + path }, project.getLocation().toFile());

			BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));

			String line = null;

			while ((line = input.readLine()) != null)
			{
				System.out.println(line);
			}

			int exitVal = pr.waitFor();
			System.out.println("Exited with error code " + exitVal);
		}
		else if (OSValidator.isWindows())
		{
			Runtime rt = Runtime.getRuntime();
			Process pr = rt.exec("C:\\cygwin\\bin\\bash.exe --login -i -c 'cd \"" + project.getLocation().toFile() + "\"; ucpp -s " + command + "'", null, project.getLocation().toFile());
			BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));
			String line = null;

			while ((line = input.readLine()) != null)
			{
				System.out.println(line);
			}

			int exitVal = pr.waitFor();
			System.out.println("Exited with error code " + exitVal);
		}
		else
		{
			System.out.println("UNSUPPORTED SYSTEM!");
		}
	}
}
