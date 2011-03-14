package ucpp.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.eclipse.core.resources.IProject;

import ucpp.Activator;

public class Ucpp
{
	public static ReturnValue setup(String options, IProject project) throws Exception
	{
		return ucpp("setup " + options, project);
	}

	public static void setup(IProject project)
	{
		SetupConfigDialog setupdlg = new SetupConfigDialog(project);
		setupdlg.open();
	}

	public static ReturnValue init(int team, IProject project) throws Exception
	{
		return ucpp("init -t " + String.valueOf(team), project);
	}

	public static ReturnValue makefile(IProject project) throws Exception
	{
		return ucpp("configure " + (OSValidator.isWindows() ? "winpy" : "py"), project);
	}

	public static ReturnValue build(IProject project) throws Exception
	{
		return exec("make", project);
	}

	public static ReturnValue clean(IProject project) throws Exception
	{
		return exec("make clean", project);
	}

	private static ReturnValue ucpp(String command, IProject project) throws Exception
	{
		return exec("ucpp -y "+command, project);
	}
	
	private static ReturnValue exec(String command, IProject project) throws Exception
	{
		Activator.out.println("executing '" + command + "'");

		Runtime rt = Runtime.getRuntime();
		Process pr = null;
		
		if (OSValidator.isUnix() || OSValidator.isMac())
		{
			String path = System.getenv("PATH");
			if (!path.contains("ucpp"))
				pr = rt.exec("~/.bashrc && "+command, null, project.getLocation().toFile());
			else
				pr = rt.exec(command, null, project.getLocation().toFile());
		}
		else if (OSValidator.isWindows())
		{
			pr = rt.exec("C:\\Program Files\\Git\\bin\\bash.exe --login -i -c 'cd \"" + project.getLocation().toFile() + "\" && " + command + "'", null, project.getLocation().toFile());
		}
		else
		{
			Activator.out.println("UNSUPPORTED SYSTEM!");
			throw new Exception("UNSUPPORTED SYSTEM!");
		}

		BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));
		String line = null;
		String lines = "";

		while ((line = input.readLine()) != null)
		{
			Activator.out.println(line);
			lines += line + ReturnValue.lineSeparator;
		}

		int exitVal = pr.waitFor();
		if (exitVal != 0)
			Activator.out.println("Exited with error code " + exitVal);

		return new ReturnValue(exitVal, lines);
	}
}
