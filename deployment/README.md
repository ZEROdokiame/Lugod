# Kubernates 部署Ruoyi-Cloud项目

本部署所用到的k8s配置均为简易版，仅作为初步入门学习使用，有不合理的地方还请见谅。

### 后期考虑功能：
* HPA自动扩缩容
* MySQL主从、Redis集群、服务集群
* ELK日志收集

## 容器分布
~~~
namespace     
├── redis              
│       └── redis           // StatefulSet
├── kube-ruoyi
│       └── ruoyi-mysql     // Deployment                
│       └── ruoyi-nacos                              
│       └── ruoyi-nginx                               
│       └── ruoyi-sentinel                            
│       └── ruoyi-gateway                             
│       └── ruoyi-auth                               
│       └── ruoyi-system                             
│       └── ruoyi-gen                                 
│       └── ruoyi-job                                
│       └── ruoyi-file                                
│       └── ruoyi-monitor                            
│       └── elasticsearch                            
│       └── skywalking                                
│       └── skywalking-ui                             
├── minio              
│       └── minio
├── monitor-sa              
│       └── node-exporter         
│       └── prometheus-server
│       └── monitoring-grafana                                  
~~~

## 部署顺序及要点
1. Redis
2. MySQL，手动Dockerfile打包镜像，初始化准备好的数据脚本。使用了Secret加密密码等配置。
3. Nacos, 配置好MODE=standalone，使用configMap设置数据库。
4. Sentinel
5. Gateway，本服务需要用Dockerfile打包镜像，否则会报错。其他RuoYi服务可以直接使用spring-boot的image命令一键打包生成镜像[每个服务都有使用额外的SpringBoot配置文件（bootstrap-k8s.yml）加入到服务中，同时在原配置文件中把active换成${SPRING_PROFILES_ACTIVE:dev}后再打包，以读取deployment的env变量。启动服务前记得提前在nacos中克隆，新增服务配置以把dev改成k8s（deployment中启动的环境是SPRING_PROFILES_ACTIVE=k8s），修改好Redis地址（redis.redis）、MySQL地址(ruoyi-mysql)、Sentinel地址(ruoyi-sentinel)]
6. Nginx，部署之前先打包好前端静态文件，使用本地磁盘方式挂载到容器 (注：我使用的是Window的DockerDesktop启动的K8s，挂载磁盘路径必须带/run/desktop/mnt/host)，修改好nginx配置ruoyi-gateway就可以启动（启动后输入localhost:30080就可以访问前端项目）。
7. Auth
8. System，配置好后就可以登录了
9. Gen
10. Job
11. File，改成minio服务之后需要修改静态文件中读取文件的端口地址
12. Monitor，需要修改前端静态文件中的Monitor端口地址
13. Minio，挂载本地文件。修改ruoyi-file服务中@Primary注释改到MinioServer上以使用Minio服务（记得修改nacos配置）
14. Elasticsearch，部署文件一并放入到了skywalking文件夹中
15. skywalking，需要在Java服务中使用agent进行跟踪（自制agent镜像然后使用initContainers方式挂载到同一个容器，模板放到了agent-in-server文件）
16. skywalking-ui