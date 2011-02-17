/**
 * 
 */
package ucpp.utils;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.core.resources.IProject;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;

/**
 * @author Patrick Plenefisch
 *
 */
public class SetupConfigDialog
{

	private Shell sShell = null;  //  @jve:decl-index=0:visual-constraint="10,10"
	private Combo setupType = null;
	private Label infoa = null;
	private Text options = null;
	private Label label = null;
	private Button Go = null;

	/**
	 * This method initializes sShell
	 */
	private void createSShell()
	{
		GridData gridData1 = new GridData();
		gridData1.horizontalAlignment = GridData.FILL;
		gridData1.verticalAlignment = GridData.END;
		GridData gridData = new GridData();
		gridData.verticalSpan = 2;
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 4;
		sShell = new Shell(Display.getCurrent());
		sShell.setText("Setup Universal C++ for FRC");
		infoa = new Label(sShell, SWT.NONE);
		infoa.setText("Mode && options:");
		createSetupType();
		sShell.setLayout(gridLayout);
		sShell.setSize(new Point(562, 443));
		options = new Text(sShell, SWT.BORDER);
		label = new Label(sShell, SWT.NONE);
		label.setText("Command: linux-windriver\r\n  Set up Linux system that relies on an existing WindRiver or\r\n  gccdist install\r\n    -c <path-to-c-drive>\r\n        Set the path equivalent to the C:/ drive\r\n        (the parent directory of the WindRiver folder)\r\n        NOTE: requires trailing slash (/)\r\n    -t <team-number>\r\n        Set default team number for deploying code\r\n\r\nCommand: linux-gccdist\r\n  Set up Linux system that automatically downloads the compiler\r\n  and WPILib\r\n    -i\r\n        Install Linux/gccdist platform\r\n    -t <team-number>\r\n        Set default team number for deploying code\r\n    -b\r\n        Update gccdist and/or recompile WPILib\r\n\r\nCommand: windows-cygwin\r\n  Set up a Windows build system that uses Cygwin\r\n   -t <team-number>\r\n       Set default team number for deploying code\r\n\r\nCommand: windows-git\r\n  Set up a Windows build system that relies on WindRiver and\r\n  MSYSgit\r\n    -t <team-number>\r\n        Set default team number for deploying code");
		label.setLayoutData(gridData);
		Go = new Button(sShell, SWT.NONE);
		Go.setText("Configure");
		Go.setLayoutData(gridData1);
		Go.addMouseListener(new MouseAdapter()
		{
			public void mouseUp(MouseEvent e)
			{
				try
				{
					ReturnValue rv = Ucpp.setup(setupType.getText()+" "+options.getText(), prj);
					if (!rv.wasSuccessful())
					{
						MessageBox mb = new MessageBox(sShell);
						mb.setText("Error initializing UC++!"+ReturnValue.lineSeparator+rv.getOutput());
					}
					else
					{
						sShell.close();
					}
				}
				catch (Exception e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}

	/**
	 * This method initializes setupType	
	 *
	 */
	private void createSetupType()
	{
		setupType = new Combo(sShell, SWT.NONE);
		setupType.setText("cygwin");
		if (OSValidator.isMac()||OSValidator.isUnix())
			setupType.setItems(new String[]{"linux-windriver", "linux-gccdist"});
		else if (OSValidator.isWindows())
			setupType.setItems(new String[]{"windows-cygwin", "windows-git"});
		else
			setupType.setItems(new String[]{"linux-windriver", "linux-gccdist", "windows-cygwin", "windows-git"});
		setupType.select(0);
	}
	
	IProject prj = null;
	
	public SetupConfigDialog(IProject project)
	{
		createSShell();
		prj = project;
	}

	public void open()
	{
		sShell.open();
	}
}
