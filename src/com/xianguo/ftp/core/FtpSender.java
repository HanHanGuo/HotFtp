package com.xianguo.ftp.core;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import com.xianguo.ftp.command.bean.Command;
import com.xianguo.ftp.command.bean.Constant;

public class FtpSender implements Closeable{
	
	private OutputStream os;
	
	public FtpSender(Socket socket) {
		try {
			this.os = socket.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendCommand(int code,String message) {
		try {
			String command = Command.COMMAND(code, message).toString();
			System.out.println("sned <== "+command);
			os.write(new String(command.getBytes(),Constant.EN_CODE).getBytes());
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void senMessage(String message) {
		try {
			System.out.println("sned <== "+message);
			os.write(new String(message.getBytes(),Constant.EN_CODE).getBytes());
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void close() throws IOException {
		os.close();
	}
	
}
