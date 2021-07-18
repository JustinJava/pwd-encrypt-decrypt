基于纯Java编写的加解密工具包
==
一、项目介绍
--
* 基于纯Java编写，采用DES对称加密算法对信息进行加解密，为了防止信息被篡改，再通过MD5摘要算进行信息摘要。
* 主要用于不同场景下对数据库密码的加解密，数据库配置以及数据库连接过程都应该采用密文方式，预防密码泄露，保证数据库的连接安全。
* 可以将整个工程编译打包成一个`pwd.jar`加解密工具包，导入到自己项目或者放到服务器上进行使用，也可以基于源码修改定制自己的工具包。
* 工具包简单、实用、安全，觉得有用的小伙伴记得`Star`支持一下哈
* 个人能力有限，有不足或者可以完善的地方，欢迎小伙伴们提`Issues`～

二、应用场景
--
###1、项目  
检查下你项目的数据库配置，看下数据库密码是不是还是明文密码？
如果是的话，那建议你不妨试试使用本工具包，改成使用加密密码，只需要进行如下两个配置的简单改造。
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

###2、shell脚本
写过shell脚本的童鞋应该知道，如果shell脚本中有跟远程数据库打交道，比如根据数据文件批量更新或批量插入MySQL数据表，在操作数据库之前，需要通过用户密码建立连接，这时如果直接在脚本中使用明文密码，就不够安全啦
同样，不妨试试通过本工具包，改成使用加密密码，只需要进行以下简单的几个操作即可
* 上传服务器 
  * 将pwd.jar加解密工具包放到服务器，如/home/tomcat/password/pwd.jar
  * 生成密文文件，生成命令为`java -jar pwd.jar host user password`
  * 密钥encrypt.pass生成在当前目录即/home/tomcat/password/encrypt.pass
* 配置sh脚本
  * 通过密文文件，命令为`java -jar pwd.jar host user`
  * 修改sh脚本中的数据库密码为通过第一步的命令获取




