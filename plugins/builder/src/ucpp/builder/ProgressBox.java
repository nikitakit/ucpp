package ucpp.builder;

import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.progress.IProgressService;

public class ProgressBox
{
	public ProgressBox(IRunnableWithProgress ptr) throws Exception
	{
		IProgressService service = PlatformUI.getWorkbench().getProgressService();
		service.run(true, false, ptr);
	}
}
