package com.xianguo.ftp.core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.xianguo.ftp.command.CommandHandle;
import com.xianguo.ftp.file.FileController;

public class FtpCommandCore extends Thread {
	
	public FtpCommandCore() {
		
	}
	
	@Override
	public void run() {
		try {
			int port = 21;
			ServerSocket server = new ServerSocket(port);
			System.out.println("server启动成功:21");
			while (true) {
				Socket socket = server.accept();
				CommandHandle commandHandle = new CommandHandle();
				FtpSender ftpSender = new FtpSender(socket);
				commandHandle.setFtpSender(ftpSender);
				commandHandle.setFileController(new FileController("D:", "\\"));
				FtpHandle handle = new FtpHandle(socket, commandHandle);
				handle.start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
