package ucpp.utils;

public class ReturnValue
{
	public static final String lineSeparator = System.getProperty("line.separator");
	
	int exit;
	String message;

	public ReturnValue(int exitVal, String lines)
	{
		exit = exitVal;
		message = lines;
	}
}
