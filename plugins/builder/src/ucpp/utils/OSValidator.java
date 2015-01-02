package ucpp.utils;

/**
 * This class can be used to determine which OS the user is running
 */
public class OSValidator {

	public static final String OS = System.getProperty("os.name").toLowerCase();

	/**
	 * @return true if the OS is Windows false otherwise
	 */
	public static boolean isWindows() {
		// windows
		return (OS.indexOf("win") >= 0);

	}

	/**
	 * @return true if the system is a Mac false otherwise
	 */
	public static boolean isMac() {
		// Mac
		return (OS.indexOf("mac") >= 0);
	}

	/**
	 * @return true if the OS is Linux or Unix false otherwise
	 */
	public static boolean isUnix() {
		// linux or unix or mac (mac is unix-like os)
		return OS.contains("unix") || OS.contains("linux") || isMac();

	}
}
