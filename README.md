# bluewind-boot

### 项目介绍
📚bluewind-boot 一个基于SpringBoot + MyBatis 的后台权限管理系统，代码简洁易懂、界面美观大方，内部封装了权限系统常用的功能，可直接作为快速开发JavaWEB项目的脚手架使用。

### 演示地址
01.  地址：<a target="_blank" href="http://127.0.0.1:8090/bluewind-boot">http://127.0.0.1:8090/bluewind-boot</a>
01.  账户：admin
01.  密码：123456a?

### 技术选型
- SpringBoot 2.2.12
- thymeleaf 模板引擎
- Quartz 定时任务调度方案
- druid 1.2.5 德鲁伊连接池
- mybatis-spring-boot-starter 2.1.2 持久层框架
- pagehelper 1.3.0 mybatis分页插件
- knife4j 2.0.5 swagger-ui接口文档美化方案
- jasypt 2.1.1 配置文件加密的解决方案
- itextpdf 5.2.0 pdf处理工具包
- redisson 3.15.4 redis分布式锁工具包
- anji-plus 1.3.0 anji-plus行为验证码工具包
- LayUI 前端框架，官方文档：https://www.layui.com/doc/
- layui-mini 前框模板项目，项目地址：https://gitee.com/zhongshaofa/layuimini

### 运行环境
- jdk8
- Mysql5.6+
- redis3.0+

### 启动教程

- 导入sql文件夹下的数据库脚本到MySQL
- 如果你不需要使用jasypt加密数据库用户名密码，则直接修改application-dev.yml中MySQL数据库的连接信息即可（ENC部分也替换掉）
- 如需加密数据库用户名密码，请使用工具类com.bluewind.boot.common.utils.JasyptUtils进行加密后再配置连接信息（替换掉ENC包裹的部分），
  然后在application-dev.yml的jasypt.encryptor.password处配置上你的密钥。
  可参考文章：https://juejin.cn/post/6844904137423847438
- 修改配置文件中application-dev.yml中Redis的连接信息
- 运行启动类BluewindBootApplication，即可正常启动项目

### 内置功能
权限管理
01.  用户登录：用户输入账户密码登录系统
02.  用户管理：用户是系统操作者，该功能主要完成系统用户配置
03.  角色管理：角色菜单权限分配
04.  菜单管理：配置系统菜单，操作权限，按钮权限标识等

系统设置
01.  数据字典：对系统中经常使用的一些较为固定的数据进行维护
02.  业务流水号：配置生成指定规则的业务流水号
03.  网页配置：配置网站信息
04.  定时任务调度：基于Qurtaz 在线（添加、修改、删除)任务调度包含执行结果日志

系统监控
01.  Druid监控：通过Druid监视当前系统数据库连接池状态
02.  登录日志：对每个用户的登录信息进行监控，获取用户的 IP，地理位置等信息
03.  操作日志：通过自定义注解，对用户的操作进行记录
04.  服务器监控：监视当前系统CPU、内存、磁盘、堆栈等相关信息
05.  在线用户监控：监控系统当前在线用户

邮件服务
01.  邮件发送：邮件发送共有五种方式，文本、HTML、图片、附件、模板

Itfc服务管理
01.  服务秘钥：给itfc服务使用方颁发秘钥，用于访问服务时鉴权
02.  服务权限：给不同的秘钥分配不同的接口权限，保证接口的安全性


### 页面展示
![输入图片说明](https://images.gitee.com/uploads/images/2021/0928/194325_04527e94_5304908.png "登陆20210928190004.png")
![输入图片说明](https://images.gitee.com/uploads/images/2021/0928/194411_0de6e6c6_5304908.png "行为验证码20210928190124.png")
![输入图片说明](https://images.gitee.com/uploads/images/2021/0928/194428_1d3201e2_5304908.png "用户管理20210928190218.png")
![输入图片说明](https://images.gitee.com/uploads/images/2021/0928/194448_72e9ee18_5304908.png "角色管理20210928190248.png")
![输入图片说明](https://images.gitee.com/uploads/images/2021/0928/194504_5d7c3d7f_5304908.png "菜单管理20210928190323.png")
![输入图片说明](https://images.gitee.com/uploads/images/2021/0928/194552_2ca2b92e_5304908.png "业务流水号20210928190704.png")
![输入图片说明](https://images.gitee.com/uploads/images/2021/0928/194621_db9a1d1b_5304908.png "数据字典20210928190647.png")
![输入图片说明](https://images.gitee.com/uploads/images/2021/0928/194638_6eda7fe8_5304908.png "定时任务20210928190719.png")
![输入图片说明](https://images.gitee.com/uploads/images/2021/0928/194712_6b7724b1_5304908.png "服务器监控20210928190500.png")
![输入图片说明](https://images.gitee.com/uploads/images/2021/0928/194734_e8e855eb_5304908.png "在线用户管理20210928190630.png")
![输入图片说明](https://images.gitee.com/uploads/images/2021/0928/194748_81140ee1_5304908.png "操作日志20210928190425.png")
![输入图片说明](https://images.gitee.com/uploads/images/2021/0928/194808_fc3af25b_5304908.png "Druid监控20210928190607.png")
![输入图片说明](https://images.gitee.com/uploads/images/2021/0928/194821_57606555_5304908.png "服务秘钥20210928190345.png")
![输入图片说明](https://images.gitee.com/uploads/images/2021/0928/194834_97216cc4_5304908.png "服务权限20210928190403.png")
### bluewind-boot

[![leisureLXY/bluewind-boot](https://gitee.com/leisureLXY/bluewind-boot/widgets/widget_card.svg?colors=ffffff,1e252b,323d47,455059,d7deea,99a0ae)](https://gitee.com/leisureLXY/bluewind-boot)

