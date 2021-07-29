基于纯Java编写的密码加解密工具包
==
一、项目介绍
--
* 工具包基于纯Java编写，采用DES对称加密算法对信息进行加解密，为了防止信息被篡改，再通过MD5摘要算进行信息摘要。
* 主要用于不同场景下对数据库密码的加解密，数据库配置以及数据库连接过程都应该采用密文方式，明文密码容易泄露，影响数据库安全。
* 整个工程可以被编译打包成一个`pwd.jar`加解密工具包，将工具包导入到自己项目或者放到服务器上进行使用，也可以基于源码修改定制自己的工具包。
* 工具包简单、实用、安全，可能也有一些不足或者需要完善的地方，欢迎小伙伴们提`Issues`～
* 最后，觉得有用的小伙伴记得`Star`支持一下哈

二、应用场景
--
### 1、项目
　　你的项目数据库连接配置，是不是还在用明文密码？如果是的话，那不妨试试使用本工具包，改成使用加密密码，只需要进行如下两个简单的配置改造。

* 数据库配置
    * 引入pwd.jar工具包到工程中
    * 通过明文密码获取密文，可以在工程里写个Test测试类，通过pwd.jar包的EncryptPass类的encryptStr方法获取到密文
    * 改造properties或yml配置的数据库明文密码为密文密码
* 连接池配置
    * 自定义一个自己的DataSource数据源类，如TomcatDataSource，继承原生的DataSource类，将xml配置中的数据源指向自己的TomcatDataSource。
    * 重写数据源类TomcatDataSource的setPassword方法
    * 在setPassword方法中，可以获取数据库密码密文(spring-mybatis.xml中数据源配置的password属性值从properties或yml配置中加载到密文)
    * 获取到密文后，在setPassword方法中使用pwd.jar包的DecryptPass类的getDepass对密文进行解密得到明文
    * 更新连接池的密码为得到的明文密码，不同连接池更新方式不同，一般都是通过this.poolProperties.setPassword("密文")方式更新
    * 完成`数据库配置`和`连接池配置`后，项目就完成了数据库密码加解密的整合~

### 2、shell脚本
　　写过shell脚本的童鞋应该知道，如果shell脚本中需要跟远程数据库打交道，比如每天根据外系统下发的批量数据文件，批量的去更新或插入数据到MySQL数据表，
在操作MySQL数据库之前，需要先通过数据库用户密码建立连接，这时如果直接在脚本中使用数据库明文密码，就不够安全啦， 
这时不妨试试使用本工具包，改成使用加密密码，只需要进行以下简单的两个操作即可。

* 生成密文文件
    * 将pwd.jar加解密工具包放到服务器，如/home/tomcat/password/pwd.jar
    * 生成密文文件encrypt.pass，生成命令为`java -jar pwd.jar host user password |tail -n 1`
    * 生成的密文文件在pwd.jar当前目录下，如/home/tomcat/password/encrypt.pass
* 配置sh脚本
    * 读取密文文件得到明文密码，读取命令为`java -jar pwd.jar host user |tail -n 1` 其中`|tail -n 1`表示取最后一行输出的明文密码
    * 修改sh脚本中的数据库密码为通过命令`java -jar pwd.jar host user |tail -n 1`方式获取

三、快速上手
--
* 关于本加解密工具包，上面的应用场景只是大致介绍了思路和实现步骤~
* 具体如何使用和快速上手，可以参考我的这篇博客文章学习：
https://blog.csdn.net/JustinQin/article/details/119132853?utm_source=app&app_version=4.12.0

 
