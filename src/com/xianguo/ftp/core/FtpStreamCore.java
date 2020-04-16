package com.xianguo.ftp.core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import com.xianguo.ftp.command.CommandHandle;
import com.xianguo.ftp.file.FileController;

public class FtpStreamCore extends Thread {
	
	@Override
	public void run() {
		try {
			int port = 20;
			ServerSocket server = new ServerSocket(port);
			System.out.println("server启动成功:20");
			CommandHandle commandHandle;
			Map<String, CommandHandle> commandHandles = new HashMap<>();
			while (true) {
				Socket socket = server.accept();
				commandHandle = commandHandles.get(socket.getInetAddress().toString());
				if(commandHandle == null) {
					commandHandle = new CommandHandle();
					FtpSender ftpSender = new FtpSender(socket);
					commandHandle.setFtpSender(ftpSender);
					commandHandle.setFileController(new FileController("D:", "\\"));
					commandHandles.put(socket.getInetAddress().toString(), commandHandle);
				}
				FtpHandle handle = new FtpHandle(socket, commandHandle);
				handle.start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
