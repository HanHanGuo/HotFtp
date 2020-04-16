package com.xianguo.ftp.core;

public class FtpCore {
	public static void main(String[] args) throws Exception{
		FtpCommandCore commandCore = new FtpCommandCore();
		commandCore.start();
		/*FtpStreamCore streamCore = new FtpStreamCore();
		streamCore.start();*/
	}
}
