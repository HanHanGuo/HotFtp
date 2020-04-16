/**
* 文件名：PasvHandle.java
* 版权：Company Technologies Co.,Ltd.Copyright YYYY-YYYY,All rights reserved
* 版权：Copyright (c) 2020, Danielchen521234@gmail.com All Rights Reserved.
* 描述：<描述>
* 修改人：Administrator
* 修改时间：2020/04/14
* 修改内容：<修改内容>
*/

package com.xianguo.ftp.command;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

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
public class PasvHandle extends DataHandle {
	
	public PasvHandle(String ip,Integer port,FtpSender ftpSender,FileController fileController) {
		this.ip = ip;
		this.port = port;
		this.ftpSender = ftpSender;
		this.fileController = fileController;
	}
	
	public void start() {
		try {
			this.server = new ServerSocket(port);
			this.socket = server.accept();
			this.dataFtpSender = new FtpSender(socket);
			os = socket.getOutputStream();
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
			server.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
