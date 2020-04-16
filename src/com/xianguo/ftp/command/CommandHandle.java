package com.xianguo.ftp.command;

import java.util.Random;

import com.xianguo.ftp.command.bean.Command;
import com.xianguo.ftp.command.enums.CommandType;
import com.xianguo.ftp.command.enums.ConnectionType;
import com.xianguo.ftp.core.FtpSender;
import com.xianguo.ftp.file.FileController;

public class CommandHandle {
	
	private String user;//当前用户
	
	private String password;//当前用户密码
	
	private ConnectionType connectionType;//连接类型
	
	private String clientIp;//客户端ip
	
	private Integer clientPort;//客户端端口
	
	private DataHandle dataHandle;//主动模式处理器
	
	private FtpSender ftpSender;//ftp消息发送器
	
	private FileController fileController;//文件控制器
	
	public String Handle(String command,String[] arg) {
		CommandType commandType = CommandType.getType(command);
		commandType = commandType == null ? CommandType.UNKNOWN : commandType;
		switch (commandType) {
			case USER:
				return USER_HANDLE(command, arg).toString();
			case PASS:
				return PASS_HANDLE(command, arg).toString();
			case XPWD:
				return XPWD_HANDLE(command, arg).toString();
			case CWD:
				return CWD_HANDLE(command, arg).toString();
			case QUIT:
				return QUIT_HANDLE(command,arg).toString();
			case PORT:
				return PORT_HANDLE(command,arg).toString();
			case NLST:
				return NLST_HANDLE(command,arg).toString();
			case AUTH:
				return AUTH_HANDLE(command,arg).toString();
			case PWD:
				return PWD_HANDLE(command,arg).toString();
			case MLSD:
				return MLSD_HANDLE(command,arg).toString();
			case CDUP:
				return CDUP_HANDLE(command,arg).toString();
			case LIST:
				return LIST_HANDLE(command,arg).toString();
			case PASV:
				return PASV_HANDLE(command,arg).toString();
			case RETR:
				return RETR_HANDLE(command,arg).toString();
			case TYPE:
				return TYPE_HANDLE(command,arg).toString();
			default:
				return DEFAULT_HANDLE(command, arg).toString();
		}
	}
	
	private Command TYPE_HANDLE(String command, String[] arg) {
		if(arg[0].equals("I")) {
			return Command.SUCCESS("传输类型设置为I");
		}else {
			return Command.ERROR("传输类型只支持I");
		}
	}

	private Command RETR_HANDLE(String command, String[] arg) {
		if(connectionType == ConnectionType.PAVS) {
			dataHandle.start();
		}
		dataHandle.RETR_HANDLE(arg[0]);
		return Command.COMMAND(421, "命令执行完成");
	}

	private Command PASV_HANDLE(String command, String[] arg) {
		Random rand = new Random();
		Integer protA = rand.nextInt(255 - 4 + 1) + 4;
		Integer protB = rand.nextInt(255 - 1 + 1) + 1;
		Integer port = (protA&0xFF)<<8^(protB&0xFF);
		dataHandle = new PasvHandle("127.0.0.1", port, ftpSender, fileController);
		this.connectionType = ConnectionType.PAVS;
		return Command.COMMAND(227,"127,0,0,1,"+protA+","+protB);
	}

	private Command LIST_HANDLE(String command, String[] arg) {
		return MLSD_HANDLE(command,arg);
	}

	private Command CDUP_HANDLE(String command, String[] arg) {
		fileController.cdUp();
		return Command.SUCCESS("返回上层目录");
	}

	private Command MLSD_HANDLE(String command, String[] arg) {
		if(connectionType == ConnectionType.PAVS) {
			dataHandle.start();
		}
		dataHandle.MLST_HANDLE();
		return Command.COMMAND(421 , "命令执行完成");
	}

	private Command PWD_HANDLE(String command, String[] arg) {
		String cwd = fileController.getCwd();
		return Command.COMMAND(257, "\""+cwd+"\" 是当前目录");
	}

	private Command AUTH_HANDLE(String command, String[] arg) {
		return Command.COMMAND(502 , "不支持加密");
	}

	private Command NLST_HANDLE(String command, String[] arg) {
		if(connectionType == ConnectionType.PAVS) {
			dataHandle.start();
		}
		dataHandle.NLST_HANDLE();
		return Command.COMMAND(421 , "命令执行完成");
	}

	private Command PORT_HANDLE(String command, String[] arg) {
		arg = arg[0].split(",");
		Integer portIntA = Integer.parseInt(arg[4]);
		Integer portIntB = Integer.parseInt(arg[5]);
		Integer port = (portIntA&0xFF)<<8^(portIntB&0xFF);
		String ip = arg[0] + "." + arg[1] + "." + arg[2] + "." + arg[3];
		this.clientPort = port;
		this.clientIp = ip;
		dataHandle = new PortHandle(this.clientIp,this.clientPort,this.ftpSender,this.fileController);
		dataHandle.start();
		this.connectionType = ConnectionType.PORT;
		return Command.SUCCESS();
	}

	private Command QUIT_HANDLE(String command, String[] arg) {
		return Command.SUCCESS("注销成功");
	}	

	private Command PASS_HANDLE(String command, String[] arg) {
		this.password = arg[0];
		return Command.COMMAND(230, "登录成功");
	}

	private Command XPWD_HANDLE(String command, String[] arg) {
		return Command.SUCCESS();
	}

	private Command CWD_HANDLE(String command, String[] arg) {
		return fileController.changeCwd(arg[0]);
	}

	public Command USER_HANDLE(String command,String[] arg) {
		this.user = arg[0];
		return Command.COMMAND(331,"请输入密码");
	}
	
	public Command DEFAULT_HANDLE(String command,String[] arg) {
		return Command.COMMAND(230, "指令未实现");
	}

	public void setFtpSender(FtpSender ftpSender) {
		this.ftpSender = ftpSender;
	}
	
	public void setFileController(FileController fileController) {
		this.fileController = fileController;
	}
	
	public FtpSender getFtpSender() {
		return this.ftpSender;
	}
}
