# HotFtp
FTP协议服务器JAVA实现，实现主动模式被动模式，纯java没有任何第三方包，多线程处理，方便嵌入项目。
# 运行
使用
  ```java
  FtpCommandCore commandCore = new FtpCommandCore();
  commandCore.start();
  ```
即可运行
# 配置
com.xianguo.ftp.core.FtpStreamCore可以更改端口号,关联目录地址.
# 权限
目前未开发权限功能，每个项目需求不一致，如果想要添加权限，更改com.xianguo.ftp.command.CommandHandle类即可。
