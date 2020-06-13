# ovine-java-api说明书
1.后端采用技术：Spring+Springboot+MyBatis-Plus+MySQL
2.代码使用RESTful设计风格

## jar/框架对应版本
- Java: 1.8
- IO: 2.6
- Maven: 3.1
- SpringBoot：2.1.1.RELEASE
- MyBatis-Plus：3.2.0
- beanutils：1.9.1

## 本地部署项目
首先保证本地Java1.8能成功使用，有MySQL数据库
第一步. GitHub中clone项目
   > [源码地址][3]

第二步. 使用IDEA、Eclipse、MyEclipse等Java编程软件导入项目
   > [IDEA安装步骤][4]

第三步. 安装Lombok插件
   > [IDEA lombok插件安装][1]
   > [Eclipse lombok安装插件][2]

第四步. 修改配置文件yml，
   > rtadmin.file-store.file-store-dir配置中的文件路径必须存在，否则会报错

第五步. 安装MySQL，导入数据库
   > [本地部署MySQL][5]
   > sql文件与yml配置文件同级

## 源码介绍
### 源码参用多模块形式，将各模块进行分割具体如下
  - rt-admin
   > 1. aop            ----自定义aop和拦截器
		  GlobalExceptionHandler 异常捕获；LimitHandlerInterceptorAdapter权限拦截器；SystemLogHandlerInterceptorAdapter 系统登录拦截器；LoginUser必须登录注释；SkipLogin不需要登录注释；LoginUserArgumentResolver 引入自定义注释和用户信息注入参数中； LoginUserHandlerInterceptorAdapter注释登录拦截器；
    2. config         ---- 配置文件
		  GlobalCorsConfig 跨域配置； WebMvcConfig 拦截器配置
    3. controller     ----Controller层所有接口
    4. filter         ----过滤器
  - rt-admin-common
   > 1. Constant ---项目有关常量
    2. dto    ---request、response和UserInfo相关类
    3. enums  ---所有枚举
    4. file.exception ---自定义异常
    5. util  ---所有工具类（时间、md5等）
    6. vo  ---response返回格式
  - rt-admin-dao  dao层配置
   > 1. config  ---MyBatis配置
   MybatisPlusConfig MyBatis配置；MyMetaObjectHandler 注入值；
    2. entity  ---实体类
    3. mapper
    4. query
    5. result
  - rt-admin-file ---file文件相关配置
   > 
   1. core 文件上传，读取相关实现类
   2. exception 异常文件
  - rt-admin-service
   > 
    1. config  yml配置参数对应类
    2. service
	  impl 重新装载service;raw 原生service类


[1]: https://jingyan.baidu.com/article/0a52e3f4e53ca1bf63ed725c.html "IDEA lombok插件安装"
[2]: https://www.cnblogs.com/boonya/p/10691466.html "Eclipse安装lombok插件"
[3]: https://github.com/CareyToboo/ovine-java-api/tree/from000 "源码地址"
[4]: https://jingyan.baidu.com/article/25648fc1eb1916d191fd00ed.html "IDEA安装步骤"
[5]: https://jingyan.baidu.com/article/8065f87f527d8223312498af.html "本地MySQL部署"
