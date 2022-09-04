## 目录结构
解压后生成以下两个子目录

* grow-service，包含各中间件的使用示例代码，代码在src/main/java目录下的com.alibaba.grow包中。
* grow-start，包含启动类`com.alibaba.grow.Application`。中间件使用示例的单元测试代码在`src/test/java`目录下的`com.alibaba.grow`包中。日志配置文件为`src/main/resources`目录下的logback-spring.xml。使用springmvc的代码在`src/main/java`目录下的`com.alibaba.grow`包中，velocity模板在`/src/main/resources/templates`目录中。

## 使用方式
### 在开发工具中执行
将工程导入eclipse或者idea后，直接执行包含main方法的类`com.alibaba.grow.Application`。

### 使用fat jar的方式
这也是pandora boot应用发布的方式。首先执行下列命令打包
   
```sh
mvn package
```

如果选择了auto-config，可在命令后加

```sh 
-Dautoconfig.userProperties={fullPath}/bootstrap-start/antx.properties
```

通过-D参数指定antx.properties的位置，否则会进入autoconfig的交互模式

然后进入`grow-start/target`目录，执行fat jar

```sh
java -Dpandora.location=${sar} -jar grow-start-1.0.0-SNAPSHOT.jar
```

其中${sar}为sar包的路径

### 通过mvn命令直接启动
第一次调用前先要执行

```sh
mvn install
```

如果maven工程的Artifact，group id，version等都未变化，只需执行一次即可。

然后直接通过命令执行start子工程

```sh
mvn -pl grow-start pandora-boot:run
```

以上两个命令，如果选择了auto-config，可在命令后加

```sh 
-Dautoconfig.userProperties={fullPath}/bootstrap-start/antx.properties
```

通过-D参数指定antx.properties的位置，否则会进入autoconfig的交互模式properties的位置

### 生成表映射
* 统一使用`aligenerator`插件生成表结构
* 且修改生成后的java、xml、不允许做任何修改 如果需要 则自己建一个以`Custome`开头
* 配置信息参照：![aligenerator1](doc/img/aligenerator1.png)
  ![aligenerator2](doc/img/aligenerator2.png)
* 直接点击生成即可

### 代码规约
* 必须按照Aone-Idea插件：https://topic.atatech.org/articles/72641

## 升级指南

* http://gitlab.alibaba-inc.com/middleware-container/pandora-boot/wikis/changelog

## Docker 模板

* APP-META 目录里
* http://gitlab.alibaba-inc.com/middleware-container/pandora-boot/wikis/docker

## aone发布
请参考文档 http://gitlab.alibaba-inc.com/middleware-container/pandora-boot/wikis/aone-guide

## 相关链接
### Pandora Boot
* gitbook ： http://mw.alibaba-inc.com/products/pandoraboot/_book/
* 钉钉交流群 ： 11701173
* wiki ： http://gitlab.alibaba-inc.com/middleware-container/pandora-boot/wikis/home
* FAQ: http://gitlab.alibaba-inc.com/middleware-container/pandora-boot/wikis/faq

### 开发者应用中心
* 线上 ： http://start.alibaba-inc.com
* 日常 ： http://start.taobao.net
* 文档 ： http://gitlab.alibaba-inc.com/middleware-container/pandora-boot/wikis/application-center

### Logger配置

* logger配置: http://gitlab.alibaba-inc.com/middleware-container/pandora-boot/wikis/log-config

### Docker相关链接
* 如果工程有docker模板，目录是 APP-META，docker模板的说明文件是：APP-META/README.md
* docker参考说明：http://gitlab.alibaba-inc.com/middleware-container/pandora-boot/wikis/docker

### HSF
* gitbook ： http://mw.alibaba-inc.com/products/hsf/_book/
* wiki ： http://gitlab.alibaba-inc.com/middleware/hsf2-0/wikis/home
* HSF用法详细说明 ： http://gitlab.alibaba-inc.com/middleware-container/pandora-boot/wikis/spring-boot-hsf

### TDDL
* gitbook ： http://mw.alibaba-inc.com/products/tddl/_book/
* wiki ： http://gitlab.alibaba-inc.com/middleware/tddl5-wiki/wikis/home
* TDDL用法详细说明 ： http://gitlab.alibaba-inc.com/middleware-container/pandora-boot/wikis/spring-boot-tddl

### ONS（METAQ）
* wiki ： 
  * http://gitlab.alibaba-inc.com/middleware/metaq3/wikis/howToUseMetaQ    
  * http://gitlab.alibaba-inc.com/middleware/messaging-example/wikis/ons-sdk
* ONS用法详细说明 ： http://gitlab.alibaba-inc.com/middleware-container/pandora-boot/wikis/spring-boot-ons

### Tair
* 文档中心 ： http://baike.corp.taobao.com/index.php/CS_RD/tair
* tair用法详细说明 ： http://gitlab.alibaba-inc.com/middleware-container/pandora-boot/wikis/spring-boot-tair

### Diamond
* gitbook ： http://mw.alibaba-inc.com/products/diamondserver/_book/
* wiki ： http://gitlab.alibaba-inc.com/middleware/diamond/wikis/home
* diamond用法详细说明 ： http://gitlab.alibaba-inc.com/middleware-container/pandora-boot/wikis/spring-boot-diamond

### Sentinel
* gitbook ： http://mw.alibaba-inc.com/products/sentinel/_book/index.html
* wiki ： http://gitlab.alibaba-inc.com/middleware-asp/sentinel/wikis/brief-introduction
* diamond用法详细说明 ： http://gitlab.alibaba-inc.com/middleware-container/pandora-boot/wikis/spring-boot-sentinel  
本仓库于 2021-02-02 14:24:31 使用了源码自动生成模板 pandora-boot-archetype-docker 。详情见template_info.md文件。
