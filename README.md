<p align="center">
	<img alt="路神logo" src="ruoyi-ui/src/assets/logo/lugod-logo.svg" width="200" height="200">
</p>
<h1 align="center" style="margin: 30px 0 30px; font-weight: bold;">路神后台管理框架 v1.0.0</h1>
<h4 align="center">基于 Vue/Element UI 和 Spring Boot/Spring Cloud & Alibaba 前后端分离的分布式微服务架构</h4>
<p align="center">
	<a href="https://github.com/ZEROdokiame/Lugod"><img src="https://img.shields.io/badge/Lugod-v1.0.0-brightgreen.svg"></a>
	<a href="https://github.com/ZEROdokiame/Lugod/blob/master/LICENSE"><img src="https://img.shields.io/github/license/mashape/apistatus.svg"></a>
</p>

## 平台简介

路神管理系统是一套基于开源框架二次开发的后台管理系统，适用于个人和企业使用。

* 采用前后端分离的模式，前端基于 Vue 和 Element UI。
* 后端采用Spring Boot、Spring Cloud & Alibaba。
* 注册中心、配置中心选型Nacos，权限认证使用Redis。
* 流量控制框架选型Sentinel，分布式事务选型Seata。

## 系统模块

~~~
com.ruoyi     
├── ruoyi-ui              // 前端框架 [80]
├── ruoyi-gateway         // 网关模块 [8080]
├── ruoyi-auth            // 认证中心 [9200]
├── ruoyi-api             // 接口模块
│       └── ruoyi-api-system                          // 系统接口
├── ruoyi-common          // 通用模块
│       └── ruoyi-common-core                         // 核心模块
│       └── ruoyi-common-datascope                    // 权限范围
│       └── ruoyi-common-datasource                   // 多数据源
│       └── ruoyi-common-log                          // 日志记录
│       └── ruoyi-common-redis                        // 缓存服务
│       └── ruoyi-common-seata                        // 分布式事务
│       └── ruoyi-common-security                     // 安全模块
│       └── ruoyi-common-sensitive                    // 数据脱敏
│       └── ruoyi-common-swagger                      // 系统接口
├── ruoyi-modules         // 业务模块
│       └── ruoyi-system                              // 系统模块 [9201]
│       └── ruoyi-gen                                 // 代码生成 [9202]
│       └── ruoyi-job                                 // 定时任务 [9203]
│       └── ruoyi-file                                // 文件服务 [9300]
├── ruoyi-visual          // 图形化管理模块
│       └── ruoyi-visual-monitor                      // 监控中心 [9100]
├──pom.xml                // 公共依赖
~~~

## 架构图

<p align="center">
	<img src="ruoyi-ui/src/assets/images/lugod-architecture.svg" alt="路神微服务架构图" width="750">
</p>

<p align="center">
    <b>核心技术栈</b>
</p>

<table align="center">
    <tr>
        <td align="center"><b>前端技术</b></td>
        <td align="center"><b>后端技术</b></td>
        <td align="center"><b>服务治理</b></td>
    </tr>
    <tr>
        <td>
            <ul>
                <li>Vue.js</li>
                <li>Element UI</li>
                <li>Axios</li>
                <li>Vuex</li>
            </ul>
        </td>
        <td>
            <ul>
                <li>Spring Boot</li>
                <li>Spring Cloud</li>
                <li>MyBatis</li>
                <li>JWT</li>
            </ul>
        </td>
        <td>
            <ul>
                <li>Nacos - 注册/配置中心</li>
                <li>Sentinel - 流量控制</li>
                <li>Seata - 分布式事务</li>
                <li>Redis - 缓存服务</li>
            </ul>
        </td>
    </tr>
</table>

## 内置功能

1.  用户管理：用户是系统操作者，该功能主要完成系统用户配置。
2.  部门管理：配置系统组织机构（公司、部门、小组），树结构展现支持数据权限。
3.  岗位管理：配置系统用户所属担任职务。
4.  菜单管理：配置系统菜单，操作权限，按钮权限标识等。
5.  角色管理：角色菜单权限分配、设置角色按机构进行数据范围权限划分。
6.  字典管理：对系统中经常使用的一些较为固定的数据进行维护。
7.  参数管理：对系统动态配置常用参数。
8.  通知公告：系统通知公告信息发布维护。
9.  操作日志：系统正常操作日志记录和查询；系统异常信息日志记录和查询。
10. 登录日志：系统登录日志记录查询包含登录异常。
11. 在线用户：当前系统中活跃用户状态监控。
12. 定时任务：在线（添加、修改、删除)任务调度包含执行结果日志。
13. 代码生成：前后端代码的生成（java、html、xml、sql）支持CRUD下载 。
14. 系统接口：根据业务代码自动生成相关的api接口文档。
15. 服务监控：监视当前系统CPU、内存、磁盘、堆栈等相关信息。
16. 在线构建器：拖动表单元素生成相应的HTML代码。
17. 连接池监视：监视当前系统数据库连接池状态，可进行分析SQL找出系统性能瓶颈。