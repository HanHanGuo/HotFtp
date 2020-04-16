package com.xianguo.ftp.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

import com.xianguo.ftp.command.CommandHandle;

public class FtpHandle extends Thread {

	private Socket socket;

	private CommandHandle commandHandle;

	public FtpHandle(Socket socket, CommandHandle commandHandle) {
		this.socket = socket;
		this.commandHandle = commandHandle;
	}

	@Override
	public void run() {
		try {
			InputStream inputStream = socket.getInputStream();
			commandHandle.getFtpSender().sendCommand(220, "Welcome to Guoguo FTP");
			byte[] bytes = new byte[1024];
			StringBuilder requestInfoSb = new StringBuilder();
			int bytesIndex = -1;
			byte temp;
			byte[] tempNewLine = new byte[2];
			while ((temp = (byte) inputStream.read()) != -1) {
				if (bytesIndex == bytes.length - 1) {
					bytesIndex = -1;
					requestInfoSb.append(new String(bytes, 0, bytes.length - 1, "UTF-8"));
					bytes = new byte[1024];
				}

				bytesIndex++;
				bytes[bytesIndex] = temp;

				if (temp == 13 || temp == 10) {
					if (tempNewLine[0] == 0)
						tempNewLine[0] = temp;
					else
						tempNewLine[1] = temp;
				} else {
					tempNewLine = tempNewLine[0] == 0 ? tempNewLine : new byte[2];
				}
				if (tempNewLine[0] == 13 && tempNewLine[1] == 10) {
					requestInfoSb.append(new String(bytes, 0, bytesIndex - 1, "UTF-8"));
					String commandAndArg = requestInfoSb.toString();
					System.out.println("Receive ==> " + commandAndArg);
					String[] commands = commandAndArg.split(" ");

					String command = commands[0];
					String[] commandArgs = new String[1];
					if(commands.length > 1) {
						commandArgs[0] = commandAndArg.substring(commandAndArg.indexOf(" ")+1).replace("/", "\\");
					}
					String respons = commandHandle.Handle(command, commandArgs);
					commandHandle.getFtpSender().senMessage(respons);
					requestInfoSb = new StringBuilder();
					bytesIndex = -1;
					bytes = new byte[1024];
				}
			}
			inputStream.close();
			socket.close();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
