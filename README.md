# 考勤系统后端服务（Spring Boot 项目）

> 主要对接的门禁机， 型号 IFACE

# 默认统一在 windows 64 位操作系统上操作

## 一、安装 JAVA 环境
1. 云盘下载 [jdk-8u191-windows-x64.exe](https://pan.d.dfS)
2. 安装 jdk-8u191-windows-x64.exe，安装目录第三步用到
3. cmd 命令 java -version 查看jdk是否安装成功

## 二、使用《中控脱机通讯开发包开发手册》开发步骤

1. 管理员权限选择 SDK/x64/ 文件夹下的 Register_SDK x64.bat 文件执行注册开发包，[开发包地址](http://widsfviewpage.asf020)
   ，如果已经执行可以忽略

## 三、配置 jacob.jar 的运行环境
> jacob.jar 主要用于打通 Java 对 dll 文件的调用，这里即对中控 SDK 的调用

1. 附件下载 jacob.zip 依赖包, 解压
2. 将 jacob-1.18-M2-x64.dll 复制到 jre 的安装目录 bin 文件夹下面
    * `注意`： 如果安装的 jdk 有两个 jre，即 jdk/jre（开发专用jre） 以及 jre（与jdk同一级目录, 公用jre），把 jacob-1.18-M2-x64.dll 文件都放进去

## 四、开发人员打包考勤机Java服务项目
1. 将该[项目](http://gitlab.dm-ai.cn/mis/admin/work-attendance)拉下来, 使用 maven 打包
2. 将编译打包好的文件（默认是 `target/work-attendance-0.0.1-SNAPSHOT.jar`）上传到 windows 机器上
3. 使用 cmd 命令行执行 `java -jar work-attendance-0.0.1-SNAPSHOT.jar`
4. 默认使用 8080 端口提供 Restful API
5. 部署到生产时 cmd: `start javaw -Xmx1g -Xms1g -jar work-attendance-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod`


# 整个服务数据流向

1. 当 Java 服务启动时会通过 SDK 与两台考勤机（192.sf1）建立 TCP 连接
2. 建立连接后，Java 服务将历史考勤记录同步到 MongoDB , 以 考勤号（即考勤机上面显示的工号）+考勤时间戳 为主键
3. 同步完成历史数据后，Java 服务注册 OnAttTransactionEx 事件，当考勤机验证指纹通过时会触发该事件，然后实时将生成的考勤数据写入到 MongoDB
4. Java 服务提供 Restful API 查询 MongoDB 中的考勤数据，可提供给其他服务导出考勤报表或者页面展示考勤数据的入口
