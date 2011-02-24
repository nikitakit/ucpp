package ucpp.utils;

import java.io.IOException;

import com.enterprisedt.net.ftp.FTPException;
import com.enterprisedt.net.ftp.FileTransferClient;

public class Uploader
{
	public static void Upload(String src, int team) throws FTPException, IOException
	{
		FileTransferClient ftp = new FileTransferClient();
		ftp.setRemoteHost("10." + (team / 100) + "." + (team % 100) + ".2");
		ftp.connect();
		// (project)/PPC603gnu/projectname/Debug/projectname.out
		ftp.uploadFile(src, "/ni-rt/system/FRC_UserProgram.out");
		ftp.disconnect();
	}
	public static void Delete(int team) throws FTPException, IOException
	{
		FileTransferClient ftp = new FileTransferClient();
		ftp.setRemoteHost("10." + (team / 100) + "." + (team % 100) + ".2");
		ftp.connect();
		// (project)/PPC603gnu/projectname/Debug/projectname.out
		ftp.deleteFile("/ni-rt/system/FRC_UserProgram.out");
		ftp.disconnect();
	}
}
