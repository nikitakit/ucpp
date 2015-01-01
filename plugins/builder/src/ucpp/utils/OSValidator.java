package ucpp.utils;

/**
 * This class can be used to determine which OS the user is running
 */
public class OSValidator
{

	/**
	 * @return true if the OS is Windows false otherwise
	 */
	public static boolean isWindows()
	{

		String os = System.getProperty("os.name").toLowerCase();
		// windows
		return (os.indexOf("win") >= 0);

	}

	/**
	 * @return true if the system is a Mac false otherwise
	 */
	public static boolean isMac()
	{

		String os = System.getProperty("os.name").toLowerCase();
		// Mac
		return (os.indexOf("mac") >= 0);

	}

	/**
	 * @return true if the OS is Linux or Unix false otherwise
	 */
	public static boolean isUnix()
	{

		String os = System.getProperty("os.name").toLowerCase();
		// linux or unix
		return os.contains("unix") || os.contains("linux");

	}
}
