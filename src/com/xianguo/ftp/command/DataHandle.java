/**
* 文件名：DataHandle.java
* 版权：Company Technologies Co.,Ltd.Copyright YYYY-YYYY,All rights reserved
* 版权：Copyright (c) 2020, Danielchen521234@gmail.com All Rights Reserved.
* 描述：<描述>
* 修改人：Administrator
* 修改时间：2020/04/14
* 修改内容：<修改内容>
*/

package com.xianguo.ftp.command;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.xianguo.ftp.core.FtpSender;
import com.xianguo.ftp.file.FileController;

/**
* <一句话功能简述>
* <功能详细描述>
* 
* @author Administrator
* @version [版本号,2020/04/14]
* @see [相关类/方法]
* @since [产品/模块版本]
*/
abstract class DataHandle {
	
	protected String ip;
	
	protected Integer port;
	
	protected FtpSender ftpSender;
	
	protected FileController fileController;
	
	protected FtpSender dataFtpSender;
	
	protected OutputStream os;

	protected Socket socket;

	protected ServerSocket server;
	
	protected void DataHanDle() {}
	
	abstract void close();
	
	abstract void start();
	
	public void NLST_HANDLE() {
		try {
			ftpSender.sendCommand(150, "开始传输目录列表");
			dataFtpSender.senMessage(fileController.getNameList());
			dataFtpSender.close();
			close();
			ftpSender.sendCommand(226, "目录列表传输完成");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void MLST_HANDLE() {
		try {
			ftpSender.sendCommand(150, "开始传输目录列表");
			dataFtpSender.senMessage(fileController.getDetailList());
			dataFtpSender.close();
			close();
			ftpSender.sendCommand(226, "目录列表传输完成");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void RETR_HANDLE(String path) {
		try {
			path = fileController.getCwd().equals("\\") ? fileController.getCwd() + path : fileController.getCwd() + "\\" + path;
			ftpSender.sendCommand(150, "开始下载文件:\""+path+"\"");
			File file = fileController.getFile(path);
			if(file.exists()) {
				FileInputStream is = new FileInputStream(file);
				byte[] temp = new byte[1024];
				int len = -1;
				while((len = is.read(temp)) != -1) {
					os.write(temp, 0, len);
				}
				os.flush();
				is.close();
				close();
				ftpSender.sendCommand(226, "文件下载完成:\""+path+"\"");
			}else {
				ftpSender.sendCommand(-1, "文件不存在");
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void STOR_HANDLE(String name) {
		try {
			File file = new File(fileController.getCwdFile(),name);
			if(!file.exists()) {
				file.createNewFile();
			}
			ftpSender.sendCommand(150, "开始上传文件:\""+name+"\"");
			FileOutputStream fos = new FileOutputStream(file);
			InputStream is = socket.getInputStream();
			byte[] temp = new byte[1024];
			int len = -1;
			while((len = is.read(temp)) != -1) {
				fos.write(temp, 0, len);
			}
			os.flush();
			is.close();
			fos.close();
			close();
			ftpSender.sendCommand(226, "文件上传完成:\""+name+"\"");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
