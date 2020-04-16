package com.xianguo.ftp.file;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.xianguo.ftp.command.bean.Command;

public class FileController {

	private String root;//根目录
	
	private String cwd;//当前工作目录
	
	private File cwdFile;//当前工作目录对象
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");//格式化
	
	public FileController(String root,String cwd) {
		this.root = root;
		this.cwd = cwd;
		cwdFile = new File(this.root + this.cwd);
	}
	
	public File getFile(String path) {
		return new File(this.root + path);
	}

	public File getCwdFile() {
		return cwdFile;
	}
	
	public Command changeCwd(String cwd) {
		if(cwd.startsWith("\\")) {
			if(new File(root+cwd).exists()) {
				this.cwd = cwd;
				this.cwdFile = new File(root+cwd);
				return Command.SUCCESS();
			}else {
				return Command.ERROR("目录不存在");
			}
		}else if(new File(cwdFile.getAbsolutePath() + "\\" + cwd).exists()) {
			cwdFile = new File(cwdFile.getAbsolutePath() + "\\" + cwd);
			if(!cwd.equals(this.cwd)) {
				if("\\".equals(this.cwd)) {
					this.cwd += cwd;
				} else {
					this.cwd += "\\" + cwd;
				}
			}
			return Command.SUCCESS();
		}else {
			return Command.ERROR("目录不存在");
		}
	}
	
	public void cdUp() {
		String[] paths = this.cwd.split("\\\\");
		String cwd = "\\";
		for(int i = 0;i<paths.length - 1;i++) {
			cwd+=paths[i].equals("") ? "" : cwd.equals("\\") ? paths[i] : "\\"+paths[i];
		}
		cwdFile = cwdFile.getParentFile();
		this.cwd = cwd;
	}
	
	public String getCwd() {
		return this.cwd;
	}
	
	public String getNameList() {
		StringBuilder sb = null;
		File[] files = cwdFile.listFiles();
		for(File item : files) {
			if(item.isDirectory()) {
				if(sb == null) {
					sb = new StringBuilder();
					sb.append(item.getName());
				}else {
					sb.append("\r\n" + item.getName());
				}
			}
		}
		for(File item : files) {
			if(!item.isDirectory()) {
				if(sb == null) {
					sb = new StringBuilder();
					sb.append(item.getName());
				}else {
					sb.append("\r\n" + item.getName());
				}
			}
		}
		return sb == null ? "" : sb.toString();
	}
	
	public String getDetailList() {
		StringBuilder sb = null;
		File[] files = cwdFile.listFiles();
		for(File item : files) {
			if(item.isDirectory()) {
				if(sb == null) {
					sb = new StringBuilder();
					sb.append("type=dir;modify="+sdf.format(new Date(item.lastModified()))+"; "+item.getName());
				}else {
					sb.append("\r\ntype=dir;modify="+sdf.format(new Date(item.lastModified()))+"; "+item.getName());
				}
			}else {
				if(sb == null) {
					sb = new StringBuilder();
					sb.append("type=file;modify="+sdf.format(new Date(item.lastModified()))+";size="+ item.length() +" "+item.getName());
				}else {
					sb.append("\r\ntype=file;modify="+sdf.format(new Date(item.lastModified()))+";size="+ item.length() +" "+item.getName());
				}
			}
		}
		return sb == null ? "" : sb.toString();
	}
	
	public void createFolder(String name) {
		String path = cwdFile.getAbsolutePath() + "\\" + name;
		File file = new File(path);
		if (!file.exists()){
			file.mkdir();
		}
	}

	public void deleteFolder(String name) {
		String path = cwdFile.getAbsolutePath() + "\\" + name;
		File file = new File(path);
		if (file.exists()){
			file.delete();
		}
	}
	
	public void deleteFile(String name) {
		File file = new File(cwdFile, name);
		if (file.exists()){
			file.delete();
		}
	}
}
