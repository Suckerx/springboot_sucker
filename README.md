#  基于SpringBoot+Shiro+MyBatisPlus+Jwt的微服务快速开发框架 

 这个框架基于 Java8 , Spring Boot 2.6, Shiro , MyBatisPlus , Jwt , Spring-doc 等技术开发, 是一个半开箱即用,可二次开发的微服务框架。框架实现了权限管理功能, 能帮助你快速进行注解鉴权。

**注意事项：本项目与其他微服务开发框架不同，并没有包含非常全面的各种功能，作者只是一名普通大学生，考虑的使用场景是在校大学生学习前后端分离的后端开发。因此，本系统主要是实现了权限分离，其他功能自行添加即可。**

# 功能

1. **权限分离：**使用Shiro框架实现权限分离，校验token，对接口进行拦截
2. **日志模块：**使用自定义注解，结合AOP将调用接口的日志记录到数据库
3. **API文档：**使用Spring-doc实现书写API文档，在接口上使用注解即可生成文档，供前后端分离开发使用
4. **缓存：**使用Redis完成缓存，校验token
5. **快速开发：**集成MyBatisPlus，一键生成基本代码
6. **统一返回结果：**定义全局返回结果类，接口统一使用该类返回
7. **自定义异常处理：**定义异常处理类，可全局使用

# 使用说明

- **权限分离**

  若不使用该功能，无需做过多配置，系统默认不拦截所有接口。

  若使用该功能，请先在数据库建立基本的角色和权限表，按照以下示例可以直接运行，若更改则需要修改shiro配置

  权限表

  ```sql
  CREATE TABLE `perms` (
    `permission_id` int(11) NOT NULL AUTO_INCREMENT,
    `permission` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
    `permission_name` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
    PRIMARY KEY (`permission_id`) USING BTREE
  ) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;
  ```

  角色表

  ```sql
  CREATE TABLE `role` (
    `role_id` int(11) NOT NULL AUTO_INCREMENT,
    `role_name` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`role_id`) USING BTREE,
    UNIQUE KEY `role1` (`role_name`) USING BTREE
  ) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;
  ```

  角色权限表

  ```sql
  CREATE TABLE `role_perms` (
    `role_id` int(11) NOT NULL,
    `permission_id` int(11) NOT NULL,
    PRIMARY KEY (`role_id`,`permission_id`) USING BTREE,
    KEY `FKf8yllw1ecvwqy3ehyxawqa1qp` (`permission_id`) USING BTREE,
    CONSTRAINT `rp` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`) ON DELETE CASCADE ON UPDATE CASCADE
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;
  ```

  用户表（其他字段不重要，自行定义即可，主要是需要角色Id）

  ```sql
  CREATE TABLE `user` (
    `user_id` char(19) NOT NULL COMMENT '用户id',
    `username` varchar(20) NOT NULL COMMENT '用户姓名',
    `mobile` varchar(11) DEFAULT NULL COMMENT '手机号',
    `password` varchar(32) NOT NULL DEFAULT '' COMMENT '密码',
    `create_time` datetime NOT NULL COMMENT '创建时间',
    `update_time` datetime NOT NULL COMMENT '更新时间',
    `role_id` int(6) NOT NULL COMMENT '角色id',
    PRIMARY KEY (`user_id`)
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';
  ```

  这些内容对应Shiro配置中的类，若修改则需要修改对应内容

  ![](https://img-blog.csdnimg.cn/d87efa0f4a2f4af69a06917a66ca7b9a.png)

  然后需要在config目录下的ShiroConfig中，配置需要拦截的接口，一般情况下，除了基本接口外全部拦截，将这句代码替换为下面这行注释掉的代码

  ![](https://img-blog.csdnimg.cn/570737b0dcf84a03868bc5684f5b845e.png)

  至此配置完毕，即可开始使用注解鉴权

  例子：

  ```java
  @GetMapping("test")
  @RequiresPermissions("sys:all")
  public R test(){
      System.out.println("Test");
      return R.ok();
  }
  ```

----

- **日志模块**

  使用该模块同样需要在数据库建立表

  ```sql
  CREATE TABLE `sys_log` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `username` varchar(50) DEFAULT NULL COMMENT '用户名',
    `operation` varchar(50) DEFAULT NULL COMMENT '用户操作',
    `method` varchar(200) DEFAULT NULL COMMENT '请求方法',
    `params` varchar(5000) DEFAULT NULL COMMENT '请求参数',
    `time` bigint(20) NOT NULL COMMENT '执行时长(毫秒)',
    `ip` varchar(64) DEFAULT NULL COMMENT 'IP地址',
    `create_date` datetime DEFAULT NULL COMMENT '创建时间',
    `result` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
  ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='系统日志';
  ```

  配置后即可通过注解使用，若修改则请在以下目录中修改对应文件

  ![](https://img-blog.csdnimg.cn/a1bee1f8796442699b90303e5da72649.png)

- **API文档**

  在下图目录中进行配置，默认已经开启，直接使用即可，使用方法较简单不做赘述，但需要注意，若开启权限分离，则需要开启统一的安全认证，将登录后获得的token填写进去

  ![](https://img-blog.csdnimg.cn/d71f793a1e0847e2b0d9768c768086c0.png)

- **缓存**

  使用Redis做为缓存，检验token，在Shiro中配置开启

- **快速开发**

  使用MyBatisPlus进行快速开发，在common目录下的service_base目录下的config包和handler包里面进行配置，以及在service目录下的service-info目录下的test目录最底层的CodeGenerator中配置

- **统一返回结果**

  在common目录下的common_utils包中进行配置

  示例：

  ```java
      /**
       * 登录
       * 此接口仅做示例，测试会出错，请自行根据数据库生成实体类来测试
       */
      @Operation(summary = "登陆", description = "参数:用户名 密码")
      @PostMapping("/login")
      public R login(@Parameter(description = "用户登录类") @RequestBody User user){
  		.......//省略
          Map<String, Object> hash = new HashMap<>();
          hash.put("token", token);
          hash.put("expire_time", LocalDateTime.now().plusDays(3));
          return R.ok().data(hash).message("登录成功");
      }
  ```

- **自定义异常处理**

  在common包下的service_base包中的exceptionhandler目录下进行配置

# 可能遇到的问题

 [(21条消息) 解决shiro在前后端分离项目结合jwt鉴权出现的第一次请求限制资源报错404问题_shiro 404_Sucker_苏的博客-CSDN博客](https://blog.csdn.net/m0_51572714/article/details/125837182?spm=1001.2014.3001.5502) 

