/**
* 文件名：OptsType.java
* 版权：Company Technologies Co.,Ltd.Copyright YYYY-YYYY,All rights reserved
* 版权：Copyright (c) 2020, Danielchen521234@gmail.com All Rights Reserved.
* 描述：<描述>
* 修改人：Administrator
* 修改时间：2020年9月1日
* 修改内容：<修改内容>
*/

package com.xianguo.ftp.command.enums;
/**
* <一句话功能简述>
* <功能详细描述>
* 
* @author Administrator
* @version [版本号,2020年9月1日]
* @see [相关类/方法]
* @since [产品/模块版本]
*/
public enum OptsType {
	//未知命令
	UNKNOWN("UNKNOWN"),
	//对服务器UFT8编码进行设置
	UTF8("UTF8");
	
	private String command;
	
	OptsType(String command) {
		this.command = command;
	}
	
	public static OptsType getType(String command) {
		OptsType[] comms = values();
		for(OptsType type : comms) {
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
