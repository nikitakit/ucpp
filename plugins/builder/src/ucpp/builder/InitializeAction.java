package ucpp.builder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Iterator;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import ucpp.utils.Uploader;

import com.enterprisedt.net.ftp.FileTransferClient;

public class InitializeAction implements IObjectActionDelegate
{
	private ISelection selection;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action)
	{
		if (selection instanceof IStructuredSelection)
		{
			for (Iterator it = ((IStructuredSelection) selection).iterator(); it.hasNext();)
			{
				Object element = it.next();
				IProject project = null;
				if (element instanceof IProject)
				{
					project = (IProject) element;
				}
				else if (element instanceof IAdaptable)
				{
					project = (IProject) ((IAdaptable) element).getAdapter(IProject.class);
				}
				if (project != null)
				{
					init(project);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action
	 * .IAction, org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection)
	{
		this.selection = selection;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.
	 * action.IAction, org.eclipse.ui.IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart)
	{
	}

	private void init(IProject project)
	{
		try
		{
			int team = ucpp.Activator.GetTeamNumber();
			if (OSValidator.isUnix() || OSValidator.isMac())
			{
				Runtime rt = Runtime.getRuntime();
				String path = System.getenv("PATH");
				if (!path.contains("ucpp"))
					path = System.getenv("HOME") + "/ucpp/ucpp:" + path;
				Process pr = rt.exec(new String[] { System.getenv("HOME") + "/ucpp/ucpp/ucpp", "init", "-t", String.valueOf(team) }, new String[] { "PATH=" + path }, project.getLocation().toFile());

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
				Process pr = rt.exec("C:\\cygwin\\bin\\bash.exe --login -i -c 'cd \""+project.getLocation().toFile()+"\"; ucpp init -t "+String.valueOf(team)+"'", null, project.getLocation().toFile());

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
		catch (Exception e)
		{
			System.out.println(e.toString());
			e.printStackTrace();
		}
	}

}
