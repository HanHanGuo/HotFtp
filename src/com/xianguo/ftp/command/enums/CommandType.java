package com.xianguo.ftp.command.enums;

public enum CommandType {
	USER("USER"),
	PASS("PASS"),
	CWD("CWD"),
	XPWD("XPWD"),
	//未知命令
	UNKNOWN("UNKNOWN"),
	//退出
	QUIT("QUIT"),
	//主动模式
	PORT("PORT"),
	//粗略目录列表
	NLST("NLST"),
	AUTH("AUTH"),
	//当前工作目录
	PWD("PWD"),
	//详细目录列表
	MLSD("MLSD"),
	//返回上层目录
	CDUP("CDUP"),
	//获取目录列表，带权限
	LIST("LIST"),
	//被动模式
	PASV("PASV"),
	//下载文件
	RETR("RETR"),
	//上传文件
	STOR("STOR"),
	//设置传输类型
	TYPE("TYPE"),
	//创建文件夹
	MKD("MKD"),
	//删除文件夹
	RMD("RMD"),
	//删除文件
	DELE("DELE"),
	//服务器特性列表
	FEAT("FEAT"),
	//设置服务特性
	OPTS("OPTS");
	
	private String command;
	
	CommandType(String command) {
		this.command = command;
	}
	
	public static CommandType getType(String command) {
		CommandType[] comms = values();
		for(CommandType type : comms) {
			if(type.getCommand().equals(command)) {
				return type;
			}
		}
		return null;
	}

	public String getCommand() {
		return command;
	}
	
}
