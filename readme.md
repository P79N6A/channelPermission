#测试#

# 开发框架使用说明 #
### 1、如何使用互联互通开发框架？ ###
  互联互通开发框架提供了一键生成代码包的功能，请访问：http://quickstart.c.haier.net

### 2、开发框架下载下来后都需要哪些配置？ ###
（1）配置Dubbo
（2）配置zookeeper
（3）监控相关配置
### 3、如何配置Dubbo？
  在application.yml里添加如下配置：   

``dubbo:``  
&ensp;&ensp;``registry: zookeeper://127.0.0.1:2181``     
&ensp;&ensp;``port: 20889``     #服务提供者服务端口，默认-1   
&ensp;&ensp;``threads: 100``    #服务线程池大小(固定大小)，默认为2   
&ensp;&ensp;``heartBeats: 10000`` #心跳间隔，检查连接是否已断开，默认30000   
&ensp;&ensp;``serialization: hessian2``    #协议序列化方式（可选配置，Dubbo有默认值）;
###4、在哪配置zookeeper？
  如何把服务发布到特定zookeeper？这种情况下，zookeeper需要配置在dubbo的provider.xml或者consumer.xml里，定义id属性，并且要取消yml里的dubbo.registry配置，添加如下节点：   

``<dubbo:registry id="demoRegistry" address="zookeeper://127.0.0.1:2181"/>``   
``<dubbo:service retries="0" interface="com.haier.interconn.monitor.notify.UserReadService"
               ref="userReadServiceImpl" registry="demoRegistry"/>``
###5、如何区分zookeeper的prod和test环境？
在dubbo的provider.xml或者consumer.xml里添加如下配置：    
``<beans profile="prod">``   
``<dubbo:registry id="demoRegistry_prod" address="zookeeper://127.0.0.1:2181"/>``   
``<beans>``   
``<beans profile="test">``
``<dubbo:registry id="demoRegistry_test" address="zookeeper://127.0.0.1:2181"/>``  
``</beans>``   
同时需要在application.yml里添加：   
``spring:``  
&ensp;&ensp;``profiles:``   
&ensp;&ensp;&ensp;&ensp;``active: prod``  
或者  
``spring:``  
&ensp;&ensp;``profiles:``  
&ensp;&ensp;&ensp;&ensp;``active: test``
###6、zookeeper生产、测试环境地址在哪？
  测试zookeeper地址：10.162.162.43:2181,10.162.162.44:2181,10.162.162.45:2181   
  生产zookeeper集群地址：10.138.8.223:2181,10.138.8.224:2181,10.138.8.225:2181   
  zk服务治理平台地址：http://10.162.162.2:15302/svc/index.htm#/login （测试环境）   
&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;http://10.138.25.214:8180/svc/index.htm#/login （生产环境）
###7、怎么配置应用监控？
  project:项目名称  
  app：当前应用名称  
  url：监控收集器server地址  
  enable：是否开启监控  
 
``interconn:``  
&ensp;&ensp;``project: framework-demo``  
&ensp;&ensp;``app: framework-service``  
&ensp;&ensp;``monitor:``  
&ensp;&ensp;&ensp;&ensp;``url: http://collector.c.haier.net``  
&ensp;&ensp;&ensp;&ensp;``enable: true``  
&ensp;&ensp;&ensp;&ensp;``allowResponceStatus: (401|402|403)   #允许响应的错误码，正则表达式``  
&ensp;&ensp;``trace:``  
&ensp;&ensp;&ensp;&ensp;``url: http://collector-trace.c.haier.net  #trace上报server地址``  
&ensp;&ensp;&ensp;&ensp;``sample: 0.1 #抽样率，0：关闭，1：全采样，sample<1：部分采样``  
###8、我都需要特别注意哪些地方？
（1）不同的应用要用不同的package、不同的应用名称   
（2）开发工具一定要安装lombok插件   
（3）热部署的jar包要清理掉，比如spring-boot-devtools包