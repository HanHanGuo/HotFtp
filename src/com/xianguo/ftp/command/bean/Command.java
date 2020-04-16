package com.xianguo.ftp.command.bean;

public class Command {
	
	private int code;
	
	private String message;
	
	public static Command SUCCESS() {
		Command command = new Command();
		command.code = 200;
		command.message = "Command Ok";
		return command;
	}
	
	public static Command SUCCESS(String message) {
		Command command = new Command();
		command.code = 200;
		command.message = message;
		return command;
	}
	
	public static Command ERROR() {
		Command command = new Command();
		command.code = -1;
		command.message = "Command ERROR";
		return command;
	}
	
	public static Command ERROR(String message) {
		Command command = new Command();
		command.code = -1;
		command.message = message;
		return command;
	}

	public static Command COMMAND(int code,String message) {
		Command command = new Command();
		command.code = code;
		command.message = message;
		return command;
	}
	
	@Override
	public String toString() {
		return code+" "+message+"\r\n";
	}
}
