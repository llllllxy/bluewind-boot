# bluewind-boot

### 项目介绍
📚bluewind-boot 一个基于SpringBoot + MyBatis 的后台权限管理系统，代码简洁易懂、界面美观大方，内部封装了权限系统常用的功能，可直接作为快速开发JavaWEB项目的脚手架使用。

### 演示地址
软件架构说明
01.  地址：<a target="_blank" href="http://127.0.0.1:8090/bluewind-boot">http://127.0.0.1:8090/bluewind-boot</a>
01.  账户：admin
01.  密码：123456

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
- 修改配置文件application-dev.yml中MySQL数据库的连接信息（ENC部分也替换掉）
- 如需加密数据库密码，请使用工具类com.bluewind.boot.common.utils.JasyptUtils进行加密后再配置连接信息，然后启动前需要将加密秘钥配置在环境变量中，如下图所示
    
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
<table>
    <tr>
        <td><img src="http://halo.lxyccc.top/%E7%99%BB%E9%99%8620210928190004.png"/></td>
        <td><img src="http://halo.lxyccc.top/%E8%A1%8C%E4%B8%BA%E9%AA%8C%E8%AF%81%E7%A0%8120210928190124.png"/></td>
    </tr>
    <tr>
        <td><img src="http://halo.lxyccc.top/%E7%94%A8%E6%88%B7%E7%AE%A1%E7%90%8620210928190218.png"/></td>
        <td><img src="http://halo.lxyccc.top/%E8%A7%92%E8%89%B2%E7%AE%A1%E7%90%8620210928190248.png"/></td>
    </tr>
    <tr>
        <td><img src="http://halo.lxyccc.top/%E8%8F%9C%E5%8D%95%E7%AE%A1%E7%90%8620210928190323.png"/></td>
        <td><img src="http://halo.lxyccc.top/%E5%9C%A8%E7%BA%BF%E7%94%A8%E6%88%B7%E7%AE%A1%E7%90%8620210928190630.png"/></td>
    </tr>
    <tr>
        <td><img src="http://halo.lxyccc.top/%E8%8F%9C%E5%8D%95%E7%AE%A1%E7%90%8620210928190323.png"/></td>
        <td><img src="http://halo.lxyccc.top/%E5%AE%9A%E6%97%B6%E4%BB%BB%E5%8A%A120210928190719.png"/></td>
    </tr>
    <tr>
        <td><img src="http://halo.lxyccc.top/%E6%95%B0%E6%8D%AE%E5%AD%97%E5%85%B820210928190647.png"/></td>
        <td><img src="http://halo.lxyccc.top/%E6%93%8D%E4%BD%9C%E6%97%A5%E5%BF%9720210928190425.png"/></td>
    </tr>
    <tr>
        <td><img src="http://halo.lxyccc.top/%E6%9C%8D%E5%8A%A1%E7%A7%98%E9%92%A520210928190345.png"/></td>
        <td><img src="http://halo.lxyccc.top/Druid%E7%9B%91%E6%8E%A720210928190607.png"/></td>
    </tr>
    <tr>
        <td><img src="http://halo.lxyccc.top/%E6%9C%8D%E5%8A%A1%E5%99%A8%E7%9B%91%E6%8E%A720210928190500.png"/></td>
        <td><img src="http://halo.lxyccc.top/%E6%9C%8D%E5%8A%A1%E6%9D%83%E9%99%9020210928190403.png"/></td>
    </tr>
</table>


### bluewind-boot

[![leisureLXY/bluewind-boot](https://gitee.com/leisureLXY/bluewind-boot/widgets/widget_card.svg?colors=ffffff,1e252b,323d47,455059,d7deea,99a0ae)](https://gitee.com/leisureLXY/bluewind-boot)

