package ucpp.utils;

public class ReturnValue
{
	public static final String lineSeparator = System.getProperty("line.separator");

	private int exit;
	private String message;

	public ReturnValue(int exitVal, String lines)
	{
		exit = exitVal;
		message = lines;
	}

	public boolean wasSuccessful()
	{
		return exit == 0;
	}

	public String getOutput()
	{
		return message;
	}
}
