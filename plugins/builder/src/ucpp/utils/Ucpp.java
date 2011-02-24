package ucpp.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.eclipse.core.resources.IProject;

public class Ucpp
{
	public static ReturnValue setup(String options, IProject project) throws Exception
	{
		return exe("setup " + options, project);
	}

	public static void setup(IProject project)
	{
		SetupConfigDialog setupdlg = new SetupConfigDialog(project);
		setupdlg.open();
	}

	public static ReturnValue init(int team, IProject project) throws Exception
	{
		return exe("init -t " + String.valueOf(team), project);
	}

	public static ReturnValue makefile(IProject project) throws Exception
	{
		return exe("configure " + (OSValidator.isWindows() ? "winpy" : "py"), project);
	}

	public static ReturnValue build(IProject project) throws Exception
	{
		return exec("make", project);
	}

	public static ReturnValue clean(IProject project) throws Exception
	{
		return exec("make clean", project);
	}

	private static ReturnValue exe(String command, IProject project) throws Exception
	{
		System.out.println("executing '" + command + "'");

		Runtime rt = Runtime.getRuntime();
		Process pr = null;
		
		if (OSValidator.isUnix() || OSValidator.isMac())
		{
			String path = System.getenv("PATH");
			if (!path.contains("ucpp"))
				path = System.getenv("HOME") + "/ucpp/ucpp:" + path;
			pr = rt.exec("ucpp -s " + command, new String[] { "PATH=" + path }, project.getLocation().toFile());
		}
		else if (OSValidator.isWindows())
		{
			pr = rt.exec("C:\\Program Files\\Git\\bin\\bash.exe --login -i -c 'cd \"" + project.getLocation().toFile() + "\"; ucpp -s " + command + "'", null, project.getLocation().toFile());
		}
		else
		{
			System.out.println("UNSUPPORTED SYSTEM!");
			throw new Exception("UNSUPPORTED SYSTEM!");
		}

		BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));
		String line = null;
		String lines = "";

		while ((line = input.readLine()) != null)
		{
			System.out.println(line);
			lines += line + ReturnValue.lineSeparator;
		}

		int exitVal = pr.waitFor();
		if (exitVal != 0)
			System.out.println("Exited with error code " + exitVal);

		return new ReturnValue(exitVal, lines);
	}
	
	private static ReturnValue exec(String command, IProject project) throws Exception
	{
		System.out.println("executing '" + command + "'");

		Runtime rt = Runtime.getRuntime();
		Process pr = null;
		
		if (OSValidator.isUnix() || OSValidator.isMac())
		{
			String path = System.getenv("PATH");
			pr = rt.exec(command, new String[] { "PATH=" + path }, project.getLocation().toFile());
		}
		else if (OSValidator.isWindows())
		{
			pr = rt.exec("C:\\Program Files\\Git\\bin\\bash.exe --login -i -c 'cd \"" + project.getLocation().toFile() + "\"; " + command + "'", null, project.getLocation().toFile());
		}
		else
		{
			System.out.println("UNSUPPORTED SYSTEM!");
			throw new Exception("UNSUPPORTED SYSTEM!");
		}

		BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));
		String line = null;
		String lines = "";

		while ((line = input.readLine()) != null)
		{
			System.out.println(line);
			lines += line + ReturnValue.lineSeparator;
		}

		int exitVal = pr.waitFor();
		if (exitVal != 0)
			System.out.println("Exited with error code " + exitVal);

		return new ReturnValue(exitVal, lines);
	}
}
