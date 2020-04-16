package com.xianguo.ftp.command;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import com.xianguo.ftp.core.FtpSender;
import com.xianguo.ftp.file.FileController;

public class PortHandle extends DataHandle {
	
	public PortHandle(String ip,Integer port,FtpSender ftpSender,FileController fileController) {
		this.ip = ip;
		this.port = port;
		this.ftpSender = ftpSender;
		this.fileController = fileController;
	}
	
	public void start() {
		try {
			this.socket = new Socket(ip, port);
			this.dataFtpSender = new FtpSender(socket);
			os = this.socket.getOutputStream();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	void close() {
		try {
			os.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
