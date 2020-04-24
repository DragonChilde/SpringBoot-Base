<a href="http://120.77.237.175:9080/photos/springboot">SpringBoot</a>

# SpringBoot入门 #

## SpringBoot HelloWorld ##

**1. 导入spring boot相关的依赖**

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.2.5.RELEASE</version>
    </parent>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
    </dependencies>

	<!-- 这个插件，可以将应用打包成一个可执行的jar包；-->
	 <!--将这个应用打成jar包，直接使用java -jar的命令进行执行；-->
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

**2. 编写一个主程序；启动Spring Boot应用**

	/**
	 *  @SpringBootApplication 来标注一个主程序类，说明这是一个Spring Boot应用
	 */
	@SpringBootApplication
	public class HelloWorldMainApplication {
	    public static void main(String[] args) {
	        // Spring应用启动起来
	        SpringApplication.run(HelloWorldMainApplication.class);
	    }
	}

**3. 编写相关的Controller、Service**

	@RestController
	public class HelloController {
	
	    @RequestMapping("/hello")
	    public String hello()
	    {
	      return  "Hello world";
	    }
	}

**注意:Application必须使用如下的架构,放在相应的包下才可以正常启动(不能只放在java目录下)**

![](http://120.77.237.175:9080/photos/springboot/01.png)

## Hello World探究 ##

### POM文件 ###

1. 父项目

		<parent>
		    <groupId>org.springframework.boot</groupId>
		    <artifactId>spring-boot-starter-parent</artifactId>
		    <version>1.5.9.RELEASE</version>
		</parent>

	他的父项目是
	
		<parent>
		  <groupId>org.springframework.boot</groupId>
		  <artifactId>spring-boot-dependencies</artifactId>
		  <version>1.5.9.RELEASE</version>
		  <relativePath>../../spring-boot-dependencies</relativePath>
		</parent>
		他来真正管理Spring Boot应用里面的所有依赖版本；点击进去可以看到很多已经定义好版本的依赖

	Spring Boot的版本仲裁中心；

	以后我们导入依赖默认是不需要写版本；（没有在dependencies里面管理的依赖自然需要声明版本号）

2. 启动器

	  	<dependency>
	        <groupId>org.springframework.boot</groupId>
	        <artifactId>spring-boot-starter-web</artifactId>
	    </dependency>
		<!--点击进去可以看到已经配置好所需的MVC启动依赖-->

	**spring-boot-starter**-==web==：
	
​spring-boot-starter：spring-boot场景启动器；帮我们导入了web模块正常运行所依赖的组件；
	
Spring Boot将所有的功能场景都抽取出来，做成一个个的starters（启动器），只需要在项目里面引入这些starter相关场景的所有依赖都会导入进来。要用什么功能就导入什么场景的启动器

### 主程序类，主入口类 ###

	/**
	 *  @SpringBootApplication 来标注一个主程序类，说明这是一个Spring Boot应用
	 */
	@SpringBootApplication
	public class HelloWorldMainApplication {
	    public static void main(String[] args) {
	        // Spring应用启动起来
	        SpringApplication.run(HelloWorldMainApplication.class);
	    }
	}


@**SpringBootApplication**:    Spring Boot应用标注在某个类上说明这个类是SpringBoot的主配置类，SpringBoot就应该运行这个类的main方法来启动SpringBoot应用；

	@Target({ElementType.TYPE})
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	@Inherited
	@SpringBootConfiguration
	@EnableAutoConfiguration
	@ComponentScan(
	    excludeFilters = {@Filter(
	    type = FilterType.CUSTOM,
	    classes = {TypeExcludeFilter.class}
	), @Filter(
	    type = FilterType.CUSTOM,
	    classes = {AutoConfigurationExcludeFilter.class}
	)}
	)
	public @interface SpringBootApplication {
		....
	}

- @**SpringBootConfiguration**:Spring Boot的配置类；标注在某个类上，表示这是一个Spring Boot的配置类；
	- @**Configuration**:配置类上来标注这个注解；配置类 -----  配置文件；配置类也是容器中的一个组件；@Component
- @**EnableAutoConfiguration**：开启自动配置功能；

​以前我们需要配置的东西，Spring Boot帮我们自动配置；@**EnableAutoConfiguration**告诉SpringBoot开启自动配置功能；这样自动配置才能生效；


	@AutoConfigurationPackage
	@Import({AutoConfigurationImportSelector.class})
	public @interface EnableAutoConfiguration {
		....
	}

​- @**AutoConfigurationPackage**：自动配置包

- @**Import**(AutoConfigurationPackages.Registrar.class)：

	Spring的底层注解@Import，给容器中导入一个组件；导入的组件由AutoConfigurationPackages.Registrar.class；

	将主配置类（@SpringBootApplication标注的类）的所在包及下面所有子包里面的所有组件扫描到Spring容器；

	![](http://120.77.237.175:9080/photos/springboot/02.jpg)
	
	从上图可以看到把当前应用下的所有子包注册进去

​- @**Import**(EnableAutoConfigurationImportSelector.class)；给容器中导入组件？

​	**EnableAutoConfigurationImportSelector**：导入哪些组件的选择器；
	
​	将所有需要导入的组件以全类名的方式返回；这些组件就会被添加到容器中；

​	会给容器中导入非常多的自动配置类（xxxAutoConfiguration）；就是给容器中导入这个场景需要的所有组件，并配置好这些组件；

	//首先进入EnableAutoConfigurationImportSelector.process()
	public void process(AnnotationMetadata annotationMetadata, DeferredImportSelector deferredImportSelector)
	//2.0以上版本断点显示所有的自动加载组作
	protected AutoConfigurationEntry getAutoConfigurationEntry(AutoConfigurationMetadata autoConfigurationMetadata,
			AnnotationMetadata annotationMetadata) 

![](http://120.77.237.175:9080/photos/springboot/03.jpg)
			
	//然后进入此方法	
	List<String> configurations = getCandidateConfigurations(annotationMetadata, attributes);

![](http://120.77.237.175:9080/photos/springboot/04.png)

![](http://120.77.237.175:9080/photos/springboot/05.jpg)

![](http://120.77.237.175:9080/photos/springboot/06.jpg)

有了自动配置类，免去了我们手动编写配置注入功能组件等的工作；

==Spring Boot在启动的时候从类路径下的META-INF/spring.factories中获取EnableAutoConfiguration指定的值，将这些值作为自动配置类导入到容器中，自动配置类就生效，帮我们进行自动配置工作；==以前我们需要自己配置的东西，自动配置类都帮我们；

J2EE的整体整合解决方案和自动配置都在spring-boot-autoconfigure-1.2.5.RELEASE.jar；

## 使用Spring Initializer快速创建Spring Boot项目 ##

**IDEA：使用 Spring Initializer快速创建项目**

IDE都支持使用Spring的项目创建向导快速创建一个Spring Boot项目；

选择我们需要的模块；向导会联网创建Spring Boot项目；

默认生成的Spring Boot项目；

- 主程序已经生成好了，我们只需要我们自己的逻辑
- resources文件夹中目录结构
  - static：保存所有的静态资源； js css  images；
  - templates：保存所有的模板页面；（Spring Boot默认jar包使用嵌入式的Tomcat，默认不支持JSP页面）；可以使用模板引擎（freemarker、thymeleaf）；
  - application.properties：Spring Boot应用的配置文件；可以修改一些默认设置；

**Controller**

	//这个类的所有方法返回的数据直接写给浏览器，（如果是对象转为json数据）
	//相当于(@Controller和@ResponseBody的结合使用)
	@RestController
	public class HelloController {
	
	    @RequestMapping("/hello")
	    public String hello()
	    {
	        return "hello world quick!";
	    }
	}


# 配置文件 #

## 配置文件 ##

SpringBoot使用一个全局的配置文件，配置文件名是固定的；

•application.properties

•application.yml



配置文件的作用：修改SpringBoot自动配置的默认值；SpringBoot在底层都给我们自动配置好；



YAML（YAML Ain't Markup Language）

​	YAML  A Markup Language：是一个标记语言

​	YAML   isn't Markup Language：不是一个标记语言；

标记语言：

​	以前的配置文件；大多都使用的是  **xxxx.xml**文件；

​	YAML：**以数据为中心**，比json、xml等更适合做配置文件；

​	YAML：配置例子

	server:
	  port: 8081

​	XML：

	<server>
		<port>8081</port>
	</server>

## YAML语法 ##

### 基本语法 ###

k:(空格)v：表示一对键值对（空格必须有）；

以**空格**的缩进来控制层级关系；只要是左对齐的一列数据，都是同一个层级的

	server:
	    port: 8081
	    path: /hello

属性和值也是大小写敏感；

### 值的写法 ###

#### 字面量：普通的值（数字，字符串，布尔） ####

k: v：字面直接来写；

	​字符串默认不用加上单引号或者双引号；
	
	​		""：双引号；不会转义字符串里面的特殊字符；特殊字符会作为本身想表示的意思
	
	​				name:   "zhangsan \n lisi"：输出；zhangsan 换行  lisi
	
	​		''：单引号；会转义特殊字符，特殊字符最终只是一个普通的字符串数据
	
	​				name:   ‘zhangsan \n lisi’：输出；zhangsan \n  lisi

#### 对象、Map（属性和值）（键值对） ####

k: v：在下一行来写对象的属性和值的关系；注意缩进

​		对象还是k: v的方式

	friends:
		lastName: zhangsan
		age: 20

行内写法：

	friends: {lastName: zhangsan,age: 18}

#### 数组（List、Set） ####

用- 值表示数组中的一个元素

	pets:
	 - cat
	 - dog
	 - pig

行内写法

	pets: [cat,dog,pig]


## 配置文件值注入 ##

**配置文件application.yml**

	person:
	  name: 张三
	  age:  18
	  boss: false
	  birth: 2020/01/01
	  maps: {k1: v1,k2: v2}
	  lists:
	    - zhangsan
	    - lisi
	  dog:
	    name: 小狗
	    age: 6

**javaBean**

	/**
	 * 将配置文件中配置的每一个属性的值，映射到这个组件中
	 * @ConfigurationProperties：告诉SpringBoot将本类中的所有属性和配置文件中相关的配置进行绑定；
	 *      prefix = "person"：配置文件中哪个下面的所有属性进行一一映射
	 *
	 * 只有这个组件是容器中的组件，才能容器提供的@ConfigurationProperties功能；
	 *  @ConfigurationProperties(prefix = "person")默认从全局配置文件中获取值；
	 *
	 */
	@Component
	@ConfigurationProperties(prefix = "person")
	public class Person {
	    private String name;
	    private int age;
	    private Boolean boss;
	    private Date birth;
	
	    private Map<String,Object> maps;
	    private List<Object> lists;
	    private Dog dog;
	
	    public String getName() {
	        return name;
	    }

可以导入配置文件处理器，以后编写配置就有提示了

    <!--导入配置文件处理器，配置文件进行绑定就会有提示-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-configuration-processor</artifactId>
        <optional>true</optional>
    </dependency>


**注意**:

1. 当SpringBoot Test无法导包报错时,可以在pom文件加入junit依赖解决

	 	<dependency>
            <!-- this is needed or IntelliJ gives junit.jar or junit-platform-launcher:1.3.2 not found errors -->
            <groupId>org.junit.platform</groupId>
            <artifactId>junit-platform-launcher</artifactId>
            <scope>test</scope>
        </dependency>

2. 如Dog类为Null时,把Dog的构造方法去掉

成功打印输出
![](http://120.77.237.175:9080/photos/springboot/07.jpg)


### properties配置文件在idea中默认utf-8可能会乱码 ###

![](http://120.77.237.175:9080/photos/springboot/08.jpg)

**applicaion.properties**

	person.name=李四
	person.age=40
	person.birth=1986/01/01
	person.maps.k1=v1
	person.maps.k2=v2
	person.boss=true
	person.lists=a,b,c
	person.dog.name=小狗
	person.dog.age=1

### @Value获取值和@ConfigurationProperties获取值比较 ###

<table>
<tr>
	<td></td>
	<td>@ConfigurationProperties</td>
	<td>@Value</td>
</tr>
<tr>
	<td>功能</td>
	<td>批量注入配置文件中的属性</td>
	<td>一个个指定</td>
</tr>
<tr>
	<td>松散绑定（松散语法）</td>
	<td>支持</td>
	<td>不支持</td>
</tr>
<tr>
	<td>SpEL</td>
	<td>不支持</td>
	<td>支持</td>
</tr>
<tr>
	<td>JSR303数据校验</td>
	<td>支持</td>
	<td>不支持</td>
</tr>
<tr>
	<td>复杂类型封装</td>
	<td>支持</td>
	<td>不支持</td>
</tr>
</table>

- 松散绑定
	- 当person.name配置成person.NAME时,@ConfigurationProperties可支持

- SpEL
	- @ConfigurationProperties不法在配置文件里配置SPEL表达式,@Value可以,如下图

- 数据校验
	- @ConfigurationProperties支持数据校验
	![](http://120.77.237.175:9080/photos/springboot/09.jpg)
	- @Value不支持
	![](http://120.77.237.175:9080/photos/springboot/10.jpg)
- 复杂类型
	- @Value不支持复杂类型
	
		![](http://120.77.237.175:9080/photos/springboot/11.jpg)

配置文件yml还是properties他们都能获取到值；

如果说，我们只是在某个业务逻辑中需要获取一下配置文件中的某项值，使用@Value；

![](http://120.77.237.175:9080/photos/springboot/12.jpg)

如果说，我们专门编写了一个javaBean来和配置文件进行映射，我们就直接使用@ConfigurationProperties；

### 配置文件注入值数据校验 ###

	@Component
	@ConfigurationProperties(prefix = "person")
	@Validated
	public class Person {
	
	    /**
	     * <bean class="Person">
	     *      <property name="name" value="字面量/${key}从环境变量、配置文件中获取值/#{SpEL}"></property>
	     * <bean/>
	     */
	
	   //lastName必须是邮箱格式
	    @Email
	    //@Value("${person.name}")
	    private String name;
	    //@Value("#{11*2}")
	    private Integer age;
	    //@Value("true")
	    private Boolean boss;
	
	    private Date birth;
	    private Map<String,Object> maps;
	    private List<Object> lists;
	    private Dog dog;

### @PropertySource&@ImportResource&@Bean ###

在实际开发过程中,没有可能把所有类的属性全部都配置在全局配置文件中,因为必须指定加载文件

@**PropertySource**：加载指定的配置文件；

![](http://120.77.237.175:9080/photos/springboot/13.jpg)

@**ImportResource**：导入Spring的配置文件，让配置文件里面的内容生效；

Spring Boot里面没有Spring的配置文件，我们自己编写的配置文件，也不能自动识别；

![](http://120.77.237.175:9080/photos/springboot/14.jpg)

想让Spring的配置文件生效，加载进来；@**ImportResource**标注在一个配置类上

![](http://120.77.237.175:9080/photos/springboot/15.jpg)

**SpringBoot推荐给容器中添加组件的方式；推荐使用全注解的方式**

1. 配置类**@Configuration**------>Spring配置文件

2. 使用**@Bean**给容器中添加组件

		/**
		 * @Configuration：指明当前类是一个配置类；就是来替代之前的Spring配置文件
		 *
		 * 在配置文件中用<bean><bean/>标签添加组件
		 *
		 */
		@Configuration
		public class MyConfig {
		
		    //将方法的返回值添加到容器中；容器中这个组件默认的id就是方法名
		    @Bean
		    public Hello hello02()
		    {
		        return new Hello();
		    }
		}

![](http://120.77.237.175:9080/photos/springboot/16.jpg)


#### 随机数 ####
	
	${random.value}、${random.int}、${random.long}
	${random.int(10)}、${random.int[1024,65536]}

#### 占位符获取之前配置的值，如果没有可以是用:指定默认值 ####

	person.name=李四${random.uuid}
	person.age=${random.int(30)}	//随机数最大为30
	person.birth=1986/01/01
	person.maps.k1=v1
	person.maps.k2=v2
	person.boss=true
	person.lists=a,b,c
	person.dog.name=${person.name}小狗
	person.dog.age=${random.int[1,10]}	//随机数1-10

	//Person{name='李四5030c478-191f-4df8-8bee-348053a3984c', age=13, boss=true, birth=Wed Jan 01 00:00:00 CST 1986, maps={k1=v1, k2=v2}, lists=[a, b, c], dog=Dog{name='李四1326e75a-9bcf-4eca-ab01-199838871cf4小狗', age=9}}

**注意:这里要为persson.properties定义为Spring的配置文件才可以正常使用占位符**

![](http://120.77.237.175:9080/photos/springboot/17.jpg)

## Profile ##

### 多Profile文件 ###

我们在主配置文件编写的时候，文件名可以是   application-{profile}.properties/yml

默认使用application.properties的配置；

### yml支持多文档块方式 ###

	spring:
	  profiles:
	    active: pro	#启用名为pro的配置
	
	---
	
	server:
	  port: 8084
	spring:
	  profiles: pro
	
	---
	
	server:
	  port: 8085
	spring:
	  profiles: dev #指定属于哪个环境

### 激活指定profile ###

1. 在配置文件中指定  spring.profiles.active=pro

	![](http://120.77.237.175:9080/photos/springboot/18.jpg)

2. 命令行java -jar springboot-config-0.0.1-SNAPSHOT.jar --spring.profiles.active=pro；

3. 通过执行命令行

	![](http://120.77.237.175:9080/photos/springboot/19.jpg)

4. 虚拟机参数；

​	-Dspring.profiles.active=dev
	
![](http://120.77.237.175:9080/photos/springboot/20.jpg)

## 配置文件加载位置 ##

springboot 启动会扫描以下位置的application.properties或者application.yml文件作为Spring boot的默认配置文件

–file:./config/

–file:./

–classpath:/config/

–classpath:/

优先级由高到底，高优先级的配置会覆盖低优先级的配置；

SpringBoot会从这四个位置全部加载主配置文件；**互补配置**；

==我们还可以通过spring.config.location来改变默认的配置文件位置==

**注意:直接在配置文件里直接指定外部的配置文件是不会启作用的(以下是错误示范)**

![](http://120.77.237.175:9080/photos/springboot/23.jpg)

**项目打包好以后，我们可以使用命令行参数的形式，启动项目的时候来指定配置文件的新位置；指定配置文件和默认加载的这些配置文件共同起作用形成互补配置；**

	 java -jar springboot-config02-0.0.1-SNAPSHOT.jar --spring.config.location = F:/application.properties

![](http://120.77.237.175:9080/photos/springboot/24.jpg)

**注意:这里的-file是基于项目下的根目录,项目下的module的目录不在此作用范围,application配置不起作用**

![](http://120.77.237.175:9080/photos/springboot/21.jpg)

更改配置项目的访问路径

	server.servlet.context-path=/bootconfig02

![](http://120.77.237.175:9080/photos/springboot/22.jpg)

## 外部配置加载顺序 ##

**==SpringBoot也可以从以下位置加载配置； 优先级从高到低；高优先级的配置覆盖低优先级的配置，所有的配置会形成互补配置==**

1. **命令行参数**

	所有的配置都可以在命令行上进行指定
	
		java -jar springboot-config02-0.0.1-SNAPSHOT.jar --server.port=8087  --server.context-path=/abc
	
	多个配置用空格分开； --配置项=值


2. 来自java:comp/env的JNDI属性

3. Java系统属性（System.getProperties()）

4. 操作系统环境变量

5. RandomValuePropertySource配置的random.*属性值

	==**由jar包外向jar包内进行寻找；**==

	==**优先加载带profile**==

6. **jar包外部的application-{profile}.properties或application.yml(带spring.profile)配置文件**

7. **jar包内部的application-{profile}.properties或application.yml(带spring.profile)配置文件**

	==**再来加载不带profile**==

8. **jar包外部的application.properties或application.yml(不带spring.profile)配置文件**

9. **jar包内部的application.properties或application.yml(不带spring.profile)配置文件**



10. @Configuration注解类上的@PropertySource

11. 通过SpringApplication.setDefaultProperties指定的默认属性

所有支持的配置加载来源；

<a href="https://docs.spring.io/spring-boot/docs/2.2.6.RELEASE/reference/html/spring-boot-features.html#boot-features-external-config">[参考官方文档]</a>

## 自动配置原理 ##

<a href="https://docs.spring.io/spring-boot/docs/2.2.6.RELEASE/reference/html/appendix-application-properties.html#common-application-properties">配置文件能配置的属性参照</a>

### 自动配置原理 ###

1. SpringBoot启动的时候加载主配置类，开启了自动配置功能 ==@EnableAutoConfiguration==
2. **@EnableAutoConfiguration 作用**
	-  利用EnableAutoConfigurationImportSelector给容器中导入一些组件？

	-  可以查看selectImports()方法的内容；
	
	-  List<String> configurations = getCandidateConfigurations(annotationMetadata,      attributes);获取候选的配置

		SpringFactoriesLoader.loadFactoryNames()
		扫描所有jar包类路径下  META-INF/spring.factories
		把扫描到的这些文件的内容包装成properties对象
		从properties中获取到EnableAutoConfiguration.class类（类名）对应的值，然后把他们添加在容器中

	==将 类路径下  META-INF/spring.factories 里面配置的所有EnableAutoConfiguration的值加入到了容器中；==
	
		# Auto Configure
		org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
		org.springframework.boot.autoconfigure.admin.SpringApplicationAdminJmxAutoConfiguration,\
		org.springframework.boot.autoconfigure.aop.AopAutoConfiguration,\
		org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration,\
		org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration,\
		org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration,\
		org.springframework.boot.autoconfigure.cassandra.CassandraAutoConfiguration,\
		org.springframework.boot.autoconfigure.cloud.CloudServiceConnectorsAutoConfiguration,\
		org.springframework.boot.autoconfigure.context.ConfigurationPropertiesAutoConfiguration,\
		org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration,\
		org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration,\
		org.springframework.boot.autoconfigure.couchbase.CouchbaseAutoConfiguration,\
		org.springframework.boot.autoconfigure.dao.PersistenceExceptionTranslationAutoConfiguration,\
		org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration,\
		org.springframework.boot.autoconfigure.data.cassandra.CassandraReactiveDataAutoConfiguration,\
		org.springframework.boot.autoconfigure.data.cassandra.CassandraReactiveRepositoriesAutoConfiguration,\
		org.springframework.boot.autoconfigure.data.cassandra.CassandraRepositoriesAutoConfiguration,\
		org.springframework.boot.autoconfigure.data.couchbase.CouchbaseDataAutoConfiguration,\
		org.springframework.boot.autoconfigure.data.couchbase.CouchbaseReactiveDataAutoConfiguration,\
		org.springframework.boot.autoconfigure.data.couchbase.CouchbaseReactiveRepositoriesAutoConfiguration,\
		org.springframework.boot.autoconfigure.data.couchbase.CouchbaseRepositoriesAutoConfiguration,\
		org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchAutoConfiguration,\
		org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration,\
		org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchRepositoriesAutoConfiguration,\
		org.springframework.boot.autoconfigure.data.elasticsearch.ReactiveElasticsearchRepositoriesAutoConfiguration,\
		org.springframework.boot.autoconfigure.data.elasticsearch.ReactiveRestClientAutoConfiguration,\
		org.springframework.boot.autoconfigure.data.jdbc.JdbcRepositoriesAutoConfiguration,\
		org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration,\
		org.springframework.boot.autoconfigure.data.ldap.LdapRepositoriesAutoConfiguration,\
		org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration,\
		org.springframework.boot.autoconfigure.data.mongo.MongoReactiveDataAutoConfiguration,\
		org.springframework.boot.autoconfigure.data.mongo.MongoReactiveRepositoriesAutoConfiguration,\
		org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration,\
		org.springframework.boot.autoconfigure.data.neo4j.Neo4jDataAutoConfiguration,\
		org.springframework.boot.autoconfigure.data.neo4j.Neo4jRepositoriesAutoConfiguration,\
		org.springframework.boot.autoconfigure.data.solr.SolrRepositoriesAutoConfiguration,\
		org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration,\
		org.springframework.boot.autoconfigure.data.redis.RedisReactiveAutoConfiguration,\
		org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration,\
		org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration,\
		org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration,\
		org.springframework.boot.autoconfigure.elasticsearch.jest.JestAutoConfiguration,\
		org.springframework.boot.autoconfigure.elasticsearch.rest.RestClientAutoConfiguration,\
		org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration,\
		org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration,\
		org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration,\
		org.springframework.boot.autoconfigure.h2.H2ConsoleAutoConfiguration,\
		org.springframework.boot.autoconfigure.hateoas.HypermediaAutoConfiguration,\
		org.springframework.boot.autoconfigure.hazelcast.HazelcastAutoConfiguration,\
		org.springframework.boot.autoconfigure.hazelcast.HazelcastJpaDependencyAutoConfiguration,\
		org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration,\
		org.springframework.boot.autoconfigure.http.codec.CodecsAutoConfiguration,\
		org.springframework.boot.autoconfigure.influx.InfluxDbAutoConfiguration,\
		org.springframework.boot.autoconfigure.info.ProjectInfoAutoConfiguration,\
		org.springframework.boot.autoconfigure.integration.IntegrationAutoConfiguration,\
		org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration,\
		org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,\
		org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration,\
		org.springframework.boot.autoconfigure.jdbc.JndiDataSourceAutoConfiguration,\
		org.springframework.boot.autoconfigure.jdbc.XADataSourceAutoConfiguration,\
		org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration,\
		org.springframework.boot.autoconfigure.jms.JmsAutoConfiguration,\
		org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration,\
		org.springframework.boot.autoconfigure.jms.JndiConnectionFactoryAutoConfiguration,\
		org.springframework.boot.autoconfigure.jms.activemq.ActiveMQAutoConfiguration,\
		org.springframework.boot.autoconfigure.jms.artemis.ArtemisAutoConfiguration,\
		org.springframework.boot.autoconfigure.groovy.template.GroovyTemplateAutoConfiguration,\
		org.springframework.boot.autoconfigure.jersey.JerseyAutoConfiguration,\
		org.springframework.boot.autoconfigure.jooq.JooqAutoConfiguration,\
		org.springframework.boot.autoconfigure.jsonb.JsonbAutoConfiguration,\
		org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration,\
		org.springframework.boot.autoconfigure.ldap.embedded.EmbeddedLdapAutoConfiguration,\
		org.springframework.boot.autoconfigure.ldap.LdapAutoConfiguration,\
		org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration,\
		org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration,\
		org.springframework.boot.autoconfigure.mail.MailSenderValidatorAutoConfiguration,\
		org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration,\
		org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration,\
		org.springframework.boot.autoconfigure.mongo.MongoReactiveAutoConfiguration,\
		org.springframework.boot.autoconfigure.mustache.MustacheAutoConfiguration,\
		org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration,\
		org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration,\
		org.springframework.boot.autoconfigure.rsocket.RSocketMessagingAutoConfiguration,\
		org.springframework.boot.autoconfigure.rsocket.RSocketRequesterAutoConfiguration,\
		org.springframework.boot.autoconfigure.rsocket.RSocketServerAutoConfiguration,\
		org.springframework.boot.autoconfigure.rsocket.RSocketStrategiesAutoConfiguration,\
		org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration,\
		org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration,\
		org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration,\
		org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration,\
		org.springframework.boot.autoconfigure.security.reactive.ReactiveUserDetailsServiceAutoConfiguration,\
		org.springframework.boot.autoconfigure.security.rsocket.RSocketSecurityAutoConfiguration,\
		org.springframework.boot.autoconfigure.security.saml2.Saml2RelyingPartyAutoConfiguration,\
		org.springframework.boot.autoconfigure.sendgrid.SendGridAutoConfiguration,\
		org.springframework.boot.autoconfigure.session.SessionAutoConfiguration,\
		org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration,\
		org.springframework.boot.autoconfigure.security.oauth2.client.reactive.ReactiveOAuth2ClientAutoConfiguration,\
		org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.OAuth2ResourceServerAutoConfiguration,\
		org.springframework.boot.autoconfigure.security.oauth2.resource.reactive.ReactiveOAuth2ResourceServerAutoConfiguration,\
		org.springframework.boot.autoconfigure.solr.SolrAutoConfiguration,\
		org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration,\
		org.springframework.boot.autoconfigure.task.TaskSchedulingAutoConfiguration,\
		org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration,\
		org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration,\
		org.springframework.boot.autoconfigure.transaction.jta.JtaAutoConfiguration,\
		org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration,\
		org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration,\
		org.springframework.boot.autoconfigure.web.embedded.EmbeddedWebServerFactoryCustomizerAutoConfiguration,\
		org.springframework.boot.autoconfigure.web.reactive.HttpHandlerAutoConfiguration,\
		org.springframework.boot.autoconfigure.web.reactive.ReactiveWebServerFactoryAutoConfiguration,\
		org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration,\
		org.springframework.boot.autoconfigure.web.reactive.error.ErrorWebFluxAutoConfiguration,\
		org.springframework.boot.autoconfigure.web.reactive.function.client.ClientHttpConnectorAutoConfiguration,\
		org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration,\
		org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration,\
		org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration,\
		org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration,\
		org.springframework.boot.autoconfigure.web.servlet.HttpEncodingAutoConfiguration,\
		org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration,\
		org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration,\
		org.springframework.boot.autoconfigure.websocket.reactive.WebSocketReactiveAutoConfiguration,\
		org.springframework.boot.autoconfigure.websocket.servlet.WebSocketServletAutoConfiguration,\
		org.springframework.boot.autoconfigure.websocket.servlet.WebSocketMessagingAutoConfiguration,\
		org.springframework.boot.autoconfigure.webservices.WebServicesAutoConfiguration,\
		org.springframework.boot.autoconfigure.webservices.client.WebServiceTemplateAutoConfiguration
	
	每一个这样的  xxxAutoConfiguration类都是容器中的一个组件，都加入到容器中；用他们来做自动配置；

3. 每一个自动配置类进行自动配置功能
4. 以**HttpEncodingAutoConfiguration（Http编码自动配置）**为例解释自动配置原理；

		@Configuration   //表示这是一个配置类，以前编写的配置文件一样，也可以给容器中添加组件
		@EnableConfigurationProperties(HttpProperties.class)  //启动指定类的ConfigurationProperties功能；将配置文件中对应的值和HttpProperties绑定起来；并把HttpEncodingProperties加入到ioc容器中
	
		@ConditionalOnWebApplication //Spring底层@Conditional注解（Spring注解版），根据不同的条件，如果满足指定的条件，整个配置类里面的配置就会生效；    判断当前应用是否是web应用，如果是，当前配置类生效
	
		@ConditionalOnClass(CharacterEncodingFilter.class)  //判断当前项目有没有这个类CharacterEncodingFilter；SpringMVC中进行乱码解决的过滤器；

		@ConditionalOnProperty(prefix = "spring.http.encoding", value = "enabled", matchIfMissing = true)  //判断配置文件中是否存在某个配置  spring.http.encoding.enabled；如果不存在，判断也是成立的
		//即使我们配置文件中不配置spring.http.encoding.enabled=true，也是默认生效的；

			public class HttpEncodingAutoConfiguration {
	  
		  	//他已经和SpringBoot的配置文件映射了
		  	private final HttpProperties.Encoding properties;
		  
		   //只有一个有参构造器的情况下，参数的值就会从容器中拿
		  	public HttpEncodingAutoConfiguration(HttpEncodingProperties properties) {
				this.properties = properties;
			}
		  
		    @Bean   //给容器中添加一个组件，这个组件的某些值需要从properties中获取
			@ConditionalOnMissingBean //判断容器有没有这个组件？
			public CharacterEncodingFilter characterEncodingFilter() {
				CharacterEncodingFilter filter = new OrderedCharacterEncodingFilter();
				filter.setEncoding(this.properties.getCharset().name());
				filter.setForceRequestEncoding(this.properties.shouldForce(Type.REQUEST));
				filter.setForceResponseEncoding(this.properties.shouldForce(Type.RESPONSE));
				return filter;
			}

	根据当前不同的条件判断，决定这个配置类是否生效？

	一但这个配置类生效；这个配置类就会给容器中添加各种组件；这些组件的属性是从对应的properties类中获取的，这些类里面的每一个属性又是和配置文件绑定的；

5. 所有在配置文件中配置的属性都是在xxxxProperties类中封装；配置文件能配置什么就可以参照某个功能对应的这个属性类

		@ConfigurationProperties(prefix = "spring.http")//从配置文件中获取指定的值和bean的属性进行绑定
		public class HttpProperties {

		public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

**精髓：**

​	**1）、SpringBoot启动会加载大量的自动配置类**

​	**2）、我们看我们需要的功能有没有SpringBoot默认写好的自动配置类；**

​	**3）、我们再来看这个自动配置类中到底配置了哪些组件；（只要我们要用的组件有，我们就不需要再来配置了）**

​	**4）、给容器中自动配置类添加组件的时候，会从properties类中获取某些属性。我们就可以在配置文件中指定这些属性的值；**

xxxxAutoConfigurartion：自动配置类；

给容器中添加组件

xxxxProperties:封装配置文件中相关属性；

### 细节 ###

1. **@Conditional派生注解（Spring注解版原生的@Conditional作用）**

作用：必须是@Conditional指定的条件成立，才给容器中添加组件，配置配里面的所有内容才生效；


<table>
<tr>
	<td>@Conditional扩展注解</td>
	<td>作用（判断是否满足当前指定条件）</td>
</tr>

<tr>
	<td>@ConditionalOnJava</td>
	<td>系统的java版本是否符合要求</td>
</tr>
<tr>
	<td>@ConditionalOnBean</td>
	<td>容器中存在指定Bean；</td>
</tr>
<tr>
	<td>@ConditionalOnMissingBean</td>
	<td>容器中不存在指定Bean；</td>
</tr>
<tr>
	<td>@ConditionalOnExpression</td>
	<td>满足SpEL表达式指定</td>
</tr>
<tr>
	<td>@ConditionalOnClass</td>
	<td>系统中有指定的类</td>
</tr>
<tr>
	<td>@ConditionalOnMissingClass</td>
	<td>系统中没有指定的类</td>
</tr>
<tr>
	<td>@ConditionalOnSingleCandidate</td>
	<td>容器中只有一个指定的Bean，或者这个Bean是首选Bean</td>
</tr>
<tr>
	<td>@ConditionalOnProperty</td>
	<td>系统中指定的属性是否有指定的值</td>
</tr>

<tr>
	<td>@ConditionalOnResource</td>
	<td>类路径下是否存在指定资源文件</td>
</tr>
<tr>
	<td>@ConditionalOnWebApplication</td>
	<td>当前是web环境</td>
</tr>
<tr>
	<td>@ConditionalOnNotWebApplication</td>
	<td>当前不是web环境</td>
</tr>
<tr>
	<td>@ConditionalOnJndi</td>
	<td>JNDI存在指定项</td>
</tr>
</table>

**自动配置类必须在一定的条件下才能生效；**

我们怎么知道哪些自动配置类生效；

**==我们可以通过启用  debug=true属性；来让控制台打印自动配置报告==**，这样我们就可以很方便的知道哪些自动配置类生效；


	============================
	CONDITIONS EVALUATION REPORT
	============================
	
	
	Positive matches:（自动配置类启用的）
	-----------------

	DispatcherServletAutoConfiguration matched:
      - @ConditionalOnClass found required class 'org.springframework.web.servlet.DispatcherServlet' (OnClassCondition)
      - found 'session' scope (OnWebApplicationCondition)



	Negative matches:（没有启动，没有匹配成功的自动配置类）

  	ActiveMQAutoConfiguration:
      Did not match:
         - @ConditionalOnClass did not find required class 'javax.jms.ConnectionFactory' (OnClassCondition)

	AopAutoConfiguration.AspectJAutoProxyingConfiguration:
      Did not match:
         - @ConditionalOnClass did not find required class 'org.aspectj.weaver.Advice' (OnClassCondition)


# 日志 #

## 日志框架 ##

**市面上的日志框架**

JUL、JCL、Jboss-logging、logback、log4j、log4j2、slf4j....

<table>
	<tr>
		<td>日志门面  （日志的抽象层）</td>
		<td>日志实现</td>
	</tr>
	<tr>
		<td>JCL（Jakarta  Commons Logging）(不用)    SLF4j（Simple  Logging Facade for Java）    jboss-logging(不用)</td>
		<td>Log4j  JUL（java.util.logging）  Log4j2  Logback</td>
	</tr>
</table>

左边选一个门面（抽象层）、右边来选一个实现；

日志门面：  SLF4J；

日志实现：Logback；

SpringBoot：底层是Spring框架，Spring框架默认是用JCL；

​	**==SpringBoot选用 SLF4j和logback==**

## SLF4j使用 ##

### 如何在系统中使用SLF4j ###

https://www.slf4j.org

以后开发的时候，日志记录方法的调用，不应该来直接调用日志的实现类，而是调用日志抽象层里面的方法；

给系统里面导入slf4j的jar和  logback的实现jar

	import org.slf4j.Logger;
	import org.slf4j.LoggerFactory;
	
	public class HelloWorld {
	  public static void main(String[] args) {
	    Logger logger = LoggerFactory.getLogger(HelloWorld.class);
	    logger.info("Hello World");
	  }
	}

![](http://120.77.237.175:9080/photos/springboot/25.png)

每一个日志的实现框架都有自己的配置文件。使用slf4j以后，**配置文件还是做成日志实现框架自己本身的配置文件；**

### 遗留问题 ###

me（slf4j+logback）, Spring（commons-logging）,Hibernate（jboss-logging）,MyBatis、xxxx

统一日志记录，即使是别的框架和我一起统一使用slf4j进行输出？

![](http://120.77.237.175:9080/photos/springboot/26.png)


**如何让系统中所有的日志都统一到slf4j；**

1. 将系统中其他日志框架先排除出去；
2. 用中间包来替换原有的日志框架；
3. 我们导入slf4j其他的实现


## SpringBoot日志关系 ##

SpringBoot使用它来做日志功能

    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-logging</artifactId>
      <version>2.2.5.RELEASE</version>
      <scope>compile</scope>
    </dependency>
底层依赖关系

![](http://120.77.237.175:9080/photos/springboot/27.jpg)

总结：

1. SpringBoot底层也是使用slf4j+logback的方式进行日志记录
2. SpringBoot也把其他的日志都替换成了slf4j；
3. 中间替换包？


		public class SLF4JLoggerContextFactory implements LoggerContextFactory {
			    private static final StatusLogger LOGGER = StatusLogger.getLogger();
			    private static LoggerContext context = new SLF4JLoggerContext();
		}

	![](http://120.77.237.175:9080/photos/springboot/28.png)

4. 如果我们要引入其他框架？一定要把这个框架的默认日志依赖移除掉？

	SpringBoot框架2.0前用的是commons-logging,2.0后已经没有了以下的依赖；

		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-core</artifactId>
			<exclusions>
				<exclusion>
					<groupId>commons-logging</groupId>
					<artifactId>commons-logging</artifactId>
				</exclusion>
			</exclusions>
		</dependency>

**SpringBoot能自动适配所有的日志，而且底层使用slf4j+logback的方式记录日志，引入其他框架的时候，只需要把这个框架依赖的日志框架排除掉即可**

## 日志使用 ##

### 默认配置 ###

SpringBoot默认帮我们配置好了日志；


    /*记录器*/
    Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    void contextLoads() {

        /*调试不再需要直接打印出来*/
        //System.out.println();

        //日志的级别；
        //由低到高   trace<debug<info<warn<error
        //可以调整输出的日志级别；日志就只会在这个级别以以后的高级别生效
        logger.trace("这是trace日志...");
        logger.debug("这是debug日志...");
        //SpringBoot默认给我们使用的是info级别的，没有指定级别的就用SpringBoot默认规定的级别；root级别
        logger.info("这是info日志...");
        logger.warn("这是warn日志...");
        logger.error("这是error日志...");
    }

	<!--
	  日志输出格式：
		%d表示日期时间，
		%thread表示线程名，
		%-5level：级别从左显示5个字符宽度
		%logger{50} 表示logger名字最长50个字符，否则按照句点分割。 
		%msg：日志消息，
		%n是换行符
    -->
    %d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n

SpringBoot修改日志的默认配置

	#指定包路径的日志级别
	logging.level.com.springboot=trace
	
	#logging.path=
	# 不指定路径在当前项目下生成springboot.log日志
	# 可以指定完整的路径；
	#logging.file=F:/springboot.log
	
	# 在当前磁盘的根路径下创建spring文件夹和里面的log文件夹；使用 spring.log 作为默认文件
	logging.file.path=f:/spring/log
	
	#  在控制台输出的日志的格式
	logging.pattern.console=%d{yyyy-MM-dd} [%thread] %-5level %logger{50} - %msg%n
	# 指定文件中日志输出的格式
	logging.pattern.file=%d{yyyy-MM-dd} === [%thread] === %-5level === %logger{50} ==== %msg%n


<table>
<tr>
	<td>logging.file</td>
	<td>logging.path</td>
	<td>Example</td>
	<td>Description</td>
</tr>
<tr>
	<td>(none)</td>
	<td>(none)</td>
	<td></td>
	<td>只在控制台输出</td>
</tr>
<tr>
	<td>指定文件名</td>
	<td>(none)</td>
	<td>my.log</td>
	<td>输出日志到my.log文件</td>
</tr>
<tr>
	<td>(none)</td>
	<td>指定目录</td>
	<td>/var/log</td>
	<td>输出到指定目录的 spring.log 文件中</td>
</tr>
</table>

### 指定配置 ###

日志的默认配置已经在SpringBoot底层已经设置好了,


给类路径下放上每个日志框架自己的配置文件即可；SpringBoot就不使用他默认配置的了

![](http://120.77.237.175:9080/photos/springboot/29.jpg)

<table>
<tr>
	<td>Logging System</td>
	<td>Customization</td>
</tr>
<tr>
	<td>Logback</td>
	<td>`logback-spring.xml`, `logback-spring.groovy`, `logback.xml` or `logback.groovy`</td>
</tr>
<tr>
	<td>Log4j2</td>
	<td>`log4j2-spring.xml` or `log4j2.xml`</td>
</tr>
<tr>
	<td>JDK (Java Util Logging)</td>
	<td>logging.properties`</td>d
</tr>
</table>

logback.xml：不经由Spring框架,直接就被日志框架识别了

**logback-spring.xml**：日志框架就不直接加载日志的配置项，由SpringBoot解析日志配置，可以使用SpringBoot的高级Profile功能

	<springProfile name="staging">
	    <!-- configuration to be enabled when the "staging" profile is active -->
	</springProfile>

如:

  	<appender name="stdout" class="ch.qos.logback.core.ConsoleAppender">
        <!--
        日志输出格式：
			%d表示日期时间，
			%thread表示线程名，
			%-5level：级别从左显示5个字符宽度
			%logger{50} 表示logger名字最长50个字符，否则按照句点分割。 
			%msg：日志消息，
			%n是换行符
        -->
        <layout class="ch.qos.logback.classic.PatternLayout">
            <springProfile name="dev">
                <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} ----> [%thread] ---> %-5level %logger{50} - %msg%n</pattern>
            </springProfile>
            <springProfile name="!dev">
                <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} ==== [%thread] ==== %-5level %logger{50} - %msg%n</pattern>
            </springProfile>
        </layout>
    </appender>

如果使用logback.xml作为日志配置文件，还要使用profile功能，会有以下错误

 `no applicable action for [springProfile]`


## 切换日志框架 ##

可以按照slf4j的日志适配图，进行相关的切换(了解)；

slf4j+log4j的方式；要把依赖的logback,log4j转slf4j的转换包去除

	<dependency>
	  <groupId>org.springframework.boot</groupId>
	  <artifactId>spring-boot-starter-web</artifactId>
	  <exclusions>
	    <exclusion>
	      <artifactId>logback-classic</artifactId>
	      <groupId>ch.qos.logback</groupId>
	    </exclusion>
	    <exclusion>
	      <artifactId>log4j-over-slf4j</artifactId>
	      <groupId>org.slf4j</groupId>
	    </exclusion>
	  </exclusions>
	</dependency>
	
	<dependency>
	  <groupId>org.slf4j</groupId>
	  <artifactId>slf4j-log4j12</artifactId>
	</dependency>

切换starters为log4j2,SpringBoot默认是使用spring-boot-starter-logging,要把依赖去掉


	   <dependency>
	        <groupId>org.springframework.boot</groupId>
	        <artifactId>spring-boot-starter-web</artifactId>
	        <exclusions>
	            <exclusion>
	                <artifactId>spring-boot-starter-logging</artifactId>
	                <groupId>org.springframework.boot</groupId>
	            </exclusion>
	        </exclusions>
	    </dependency>
	
		<dependency>
		  <groupId>org.springframework.boot</groupId>
		  <artifactId>spring-boot-starter-log4j2</artifactId>
		</dependency>


# Web开发 #

## 简介 ##

使用SpringBoot；

**1）、创建SpringBoot应用，选中我们需要的模块；**

**2）、SpringBoot已经默认将这些场景配置好了，只需要在配置文件中指定少量配置就可以运行起来**

**3）、自己编写业务代码；**

**自动配置原理？**

这个场景SpringBoot帮我们配置了什么？能不能修改？能修改哪些配置？能不能扩展？xxx

	xxxxAutoConfiguration：帮我们给容器中自动配置组件；
	xxxxProperties:配置类来封装配置文件的内容；

## SpringBoot对静态资源的映射规则 ##

	@ConfigurationProperties(prefix = "spring.resources", ignoreUnknownFields = false)
	public class ResourceProperties {
		//可以设置和静态资源有关的参数，缓存时间等

---

	public class WebMvcAutoConfiguration {

		......
	
		@Override
		public void addResourceHandlers(ResourceHandlerRegistry registry) {
			if (!this.resourceProperties.isAddMappings()) {
				logger.debug("Default resource handling disabled");
				return;
			}
			Duration cachePeriod = this.resourceProperties.getCache().getPeriod();
			CacheControl cacheControl = this.resourceProperties.getCache().getCachecontrol().toHttpCacheControl();
			if (!registry.hasMappingForPattern("/webjars/**")) {
				customizeResourceHandlerRegistration(registry.addResourceHandler("/webjars/**")
						.addResourceLocations("classpath:/META-INF/resources/webjars/")
						.setCachePeriod(getSeconds(cachePeriod)).setCacheControl(cacheControl));
			}
			//静态资源文件夹映射
			String staticPathPattern = this.mvcProperties.getStaticPathPattern();
			if (!registry.hasMappingForPattern(staticPathPattern)) {
				customizeResourceHandlerRegistration(registry.addResourceHandler(staticPathPattern)
						.addResourceLocations(getResourceLocations(this.resourceProperties.getStaticLocations()))
						.setCachePeriod(getSeconds(cachePeriod)).setCacheControl(cacheControl));
			}
		}

		//配置欢迎页映射
		@Bean
		public WelcomePageHandlerMapping welcomePageHandlerMapping(ApplicationContext applicationContext,
				FormattingConversionService mvcConversionService, ResourceUrlProvider mvcResourceUrlProvider) {
			WelcomePageHandlerMapping welcomePageHandlerMapping = new WelcomePageHandlerMapping(
					new TemplateAvailabilityProviders(applicationContext), applicationContext, getWelcomePage(),
					this.mvcProperties.getStaticPathPattern());
			welcomePageHandlerMapping.setInterceptors(getInterceptors(mvcConversionService, mvcResourceUrlProvider));
			return welcomePageHandlerMapping;
		}

	}


1. 所有 /webjars/** ，都去 classpath:/META-INF/resources/webjars/ 找资源

	webjars：以jar包的方式引入静态资源(https://www.webjars.org/)；

	pom

		<!--引入jquery-webjar-->在访问的时候只需要写webjars下面资源的名称即可
		  <dependency>
	            <groupId>org.webjars</groupId>
	            <artifactId>jquery</artifactId>
	            <version>3.5.0</version>
	       </dependency>

	![](http://120.77.237.175:9080/photos/springboot/31.png)

	http://localhost:8080/webjars/jquery/3.5.0/jquery.js

2. "/**" 访问当前项目的任何资源，都去（静态资源的文件夹）找映射

		"classpath:/META-INF/resources/", 
		"classpath:/resources/",
		"classpath:/static/", 
		"classpath:/public/" 
		"/"：当前项目的根路径

	![](http://120.77.237.175:9080/photos/springboot/32.png)

	http://localhost:8080/asserts/css/bootstrap.min.css	去静态资源文件夹里面找指定的的文件

3. 欢迎页； 静态资源文件夹下的所有index.html页面；被"/**"映射

	localhost:8080/   找index页面

4. 所有的 **/favicon.ico  都是在静态资源文件下找

**注意:在配置文件中如果指定了静态源的路径,会使默认的SpringBoot映射配置失效**

	spring.resources.static-locations=classpath:/hello,classpath:/abc


## 模板引擎 ##

JSP、Velocity、Freemarker、Thymeleaf

SpringBoot推荐的Thymeleaf；

语法更简单，功能更强大；

### 引入thymeleaf ###

	**pom**
	<!--使用的springBoot2以上的版本使用默认自带的thymeleaf版本就可以了,2.0以下才需指定特有版本-->
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-thymeleaf</artifactId>
	</dependency>
	<!--
	切换thymeleaf版本
	<properties>
			<thymeleaf.version>3.0.9.RELEASE</thymeleaf.version>
			<!-- 布局功能的支持程序  thymeleaf3主程序  layout2以上版本 -->
			<!-- thymeleaf2   layout1-->
			<thymeleaf-layout-dialect.version>2.2.2</thymeleaf-layout-dialect.version>
	  </properties>
	-->

### Thymeleaf使用 ###

**源码配置**	

	@ConfigurationProperties(
	    prefix = "spring.thymeleaf"
	)
	public class ThymeleafProperties {
	    private static final Charset DEFAULT_ENCODING;
	    public static final String DEFAULT_PREFIX = "classpath:/templates/";
	    public static final String DEFAULT_SUFFIX = ".html";
	    private boolean checkTemplate = true;
	    private boolean checkTemplateLocation = true;
	    private String prefix = "classpath:/templates/";
	    private String suffix = ".html";
	    private String mode = "HTML";

只要我们把HTML页面放在classpath:/templates/，thymeleaf就能自动渲染；

使用：

1. 导入thymeleaf的名称空间

		<html xmlns:th="http://www.thymeleaf.org">
2. 使用thymeleaf语法；

		<!DOCTYPE html>
		<!--引入thymeleaf标签-->
		<html lang="en" xmlns:th="http://www.thymeleaf.org">
		<head>
		    <meta charset="UTF-8">
		    <title>success</title>
		</head>
		<body>
		<!--th:text 将p里面的文本内容设置为 -->
		<p th:text="${hello}">欢迎</p>
		</body>
		</html>

### 语法规则 ###

1. th:text；改变当前元素里面的文本内容；

	th：任意html属性；来替换原生属性的值


	![](http://120.77.237.175:9080/photos/springboot/33.png)

2. 表达式

		Simple expressions:（表达式语法）
		    Variable Expressions: ${...}：获取变量值；OGNL；
		    		1）、获取对象的属性、调用方法
		    		2）、使用内置的基本对象：
		    			#ctx : the context object.
		    			#vars: the context variables.
		                #locale : the context locale.
		                #request : (only in Web Contexts) the HttpServletRequest object.
		                #response : (only in Web Contexts) the HttpServletResponse object.
		                #session : (only in Web Contexts) the HttpSession object.
		                #servletContext : (only in Web Contexts) the ServletContext object.
		                
		                ${session.foo}
		            3）、内置的一些工具对象：
		#execInfo : information about the template being processed.
		#messages : methods for obtaining externalized messages inside variables expressions, in the same way as they would be obtained using #{…} syntax.
		#uris : methods for escaping parts of URLs/URIs
		#conversions : methods for executing the configured conversion service (if any).
		#dates : methods for java.util.Date objects: formatting, component extraction, etc.
		#calendars : analogous to #dates , but for java.util.Calendar objects.
		#numbers : methods for formatting numeric objects.
		#strings : methods for String objects: contains, startsWith, prepending/appending, etc.
		#objects : methods for objects in general.
		#bools : methods for boolean evaluation.
		#arrays : methods for arrays.
		#lists : methods for lists.
		#sets : methods for sets.
		#maps : methods for maps.
		#aggregates : methods for creating aggregates on arrays or collections.
		#ids : methods for dealing with id attributes that might be repeated (for example, as a result of an iteration).
		
		    Selection Variable Expressions: *{...}：选择表达式：和${}在功能上是一样；
		    	补充：配合 th:object="${session.user}：
		   <div th:object="${session.user}">
		    <p>Name: <span th:text="*{firstName}">Sebastian</span>.</p>
		    <p>Surname: <span th:text="*{lastName}">Pepper</span>.</p>
		    <p>Nationality: <span th:text="*{nationality}">Saturn</span>.</p>
		    </div>
		    
		    Message Expressions: #{...}：获取国际化内容
		    Link URL Expressions: @{...}：定义URL；
		    		@{/order/process(execId=${execId},execType='FAST')}
		    Fragment Expressions: ~{...}：片段引用表达式
		    		<div th:insert="~{commons :: main}">...</div>
		    		
		Literals（字面量）
		      Text literals: 'one text' , 'Another one!' ,…
		      Number literals: 0 , 34 , 3.0 , 12.3 ,…
		      Boolean literals: true , false
		      Null literal: null
		      Literal tokens: one , sometext , main ,…
		Text operations:（文本操作）
		    String concatenation: +
		    Literal substitutions: |The name is ${name}|
		Arithmetic operations:（数学运算）
		    Binary operators: + , - , * , / , %
		    Minus sign (unary operator): -
		Boolean operations:（布尔运算）
		    Binary operators: and , or
		    Boolean negation (unary operator): ! , not
		Comparisons and equality:（比较运算）
		    Comparators: > , < , >= , <= ( gt , lt , ge , le )
		    Equality operators: == , != ( eq , ne )
		Conditional operators:条件运算（三元运算符）
		    If-then: (if) ? (then)
		    If-then-else: (if) ? (then) : (else)
		    Default: (value) ?: (defaultvalue)
		Special tokens:
		    No-Operation: _ 

---

		<!DOCTYPE html>
		<!--引入thymeleaf标签-->
		<html lang="en" xmlns:th="http://www.thymeleaf.org">
		<head>
		    <meta charset="UTF-8">
		    <title>success</title>
		</head>
		<body>
		<!--th:text 将p里面的文本内容设置为 -->
		<!--th:text进行了转义-->
		<p th:text="${hello}">欢迎</p>
		<!--th:utext不进行转义-->
		<p th:utext="${hello}">欢迎</p>
		
		<hr>
		<!--th:each会迭代当前的标签,因此要把迭代的内容标注在所需迭代的标签里-->
		<h1 th:text="${user}" th:each="user:${users}"></h1>
		<hr/>
		<ul>
		    <li th:each="user:${users}">[[${user}]]</li>
		</ul>
		</body>
		</html>

**效果如下**:

![](http://120.77.237.175:9080/photos/springboot/34.jpg)

## SpringMVC自动配置 ##

https://docs.spring.io/spring-boot/docs/2.2.6.RELEASE/reference/html/spring-boot-features.html#boot-features

###  Spring MVC auto-configuration ###


Spring Boot 自动配置好了SpringMVC

以下是SpringBoot对SpringMVC的默认配置

	org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration

自动配置在Spring的默认值之上添加了以下功能

- 包含`ContentNegotiatingViewResolver`和`BeanNameViewResolver`。--> 视图解析器
- 支持服务静态资源，包括对WebJars的支持（<a href="https://docs.spring.io/spring-boot/docs/2.2.6.RELEASE/reference/html/spring-boot-features.html#boot-features-spring-mvc-static-content">官方文档中有介绍</a>）。--> 静态资源文件夹路径
- 自动注册`Converter`，`GenericConverter`和`Formatterbeans`。--> 转换器，格式化器
- 支持`HttpMessageConverters`（<a href="https://docs.spring.io/spring-boot/docs/2.2.6.RELEASE/reference/html/spring-boot-features.html#boot-features-spring-mvc-message-converters">官方文档中有介绍</a>）。--> SpringMVC用来转换Http请求和响应的；User---Json；
- 自动注册`MessageCodesResolver`（<a href="https://docs.spring.io/spring-boot/docs/2.2.6.RELEASE/reference/html/spring-boot-features.html#boot-features-spring-message-codes">官方文档中有介绍</a>）。--> 定义错误代码生成规则
- 静态`index.html`支持。--> 静态首页访问
- 定制`Favicon`支持（<a href="https://docs.spring.io/spring-boot/docs/2.2.6.RELEASE/reference/html/spring-boot-features.html#boot-features-spring-mvc-favicon">官方文档中有介绍</a>）。--> 网站图标
- 自动使用`ConfigurableWebBindingInitializerbean`（<a href="https://docs.spring.io/spring-boot/docs/2.2.1.RELEASE/reference/html/spring-boot-features.html#boot-features-spring-mvc-web-binding-initializer">官方文档中有介绍</a>）。


如果您想保留 Spring Boot MVC 的功能，并且需要添加其他 <a href="https://docs.spring.io/spring/docs/5.1.3.RELEASE/spring-framework-reference/web.html#mvc">MVC 配置</a>（拦截器，格式化程序和视图控制器等），可以添加自己的 `WebMvcConfigurer` 类型的 `@Configuration` 类，但不能带 `@EnableWebMvc` 注解。如果您想自定义 `RequestMappingHandlerMapping`、`RequestMappingHandlerAdapter` 或者 `ExceptionHandlerExceptionResolver` 实例，可以声明一个 `WebMvcRegistrationsAdapter` 实例来提供这些组件。

如果您想完全掌控 Spring MVC，可以添加自定义注解了 `@EnableWebMvc` 的 `@Configuration` 配置类。


#### 视图解析器 ####

视图解析器：根据方法的返回值得到视图对象（View），视图对象决定如何渲染（转发？重定向？）

- 自动配置了ViewResolver
- ContentNegotiatingViewResolver：组合所有的视图解析器的

![](http://120.77.237.175:9080/photos/springboot/35.png)

视图解析器从哪里来的？

![](http://120.77.237.175:9080/photos/springboot/36.png)

**可以自己给容器中添加一个视图解析器；自动的将其组合进来**


	@SpringBootApplication
	public class SpringbootWebApplication {
	
	    public static void main(String[] args) {
	        SpringApplication.run(SpringbootWebApplication.class, args);
	    }
	
	    @Bean
	    public ViewResolver myViewReolver()
	    {
	        return new MyViewResolver();
	    }
	
	    private static class MyViewResolver implements  ViewResolver{
	
	        @Override
	        public View resolveViewName(String s, Locale locale) throws Exception {
	            return null;
	        }
	    }
	}

![](http://120.77.237.175:9080/photos/springboot/37.jpg)

#### 转换器、格式化器 ####

- Converter：转换器； public String hello(User user)：类型转换使用Converter（表单数据转为user）
- Formatter 格式化器； 2017.12.17===Date；

		 @Bean
	    //在配置文件中配置日期格式化的规则
	    @ConditionalOnProperty(prefix = "spring.mvc", name = "date-format")
	    public Formatter<Date> dateFormatter() {
	        return new DateFormatter(this.mvcProperties.getDateFormat());//日期格式化组件
	    }

**自己添加的格式化器转换器，我们只需要放在容器中即可**

#### HttpMessageConverters ####

- `HttpMessageConverter`：SpringMVC用来转换Http请求和响应的；User---Json
- `HttpMessageConverters` 是从容器中确定；获取所有的HttpMessageConverter；

**自己给容器中添加HttpMessageConverter，只需要将自己的组件注册容器中（@Bean,@Component）**

#### MessageCodesResolver 定义错误代码生成规则####

#### ConfigurableWebBindingInitializer  ####

**我们可以配置一个ConfigurableWebBindingInitializer来替换默认的；（添加到容器）**

### 扩展SpringMVC ###

以前的配置文件中的配置

	<mvc:view-controller path="/hello" view-name="success"/>
    <mvc:interceptors>
        <mvc:interceptor>
            <mvc:mapping path="/hello"/>
            <bean></bean>
        </mvc:interceptor>
    </mvc:interceptors>

**现在编写一个配置类（@Configuration），是WebMvcConfigurer类型；不能标注@EnableWebMvc**

既保留了所有的自动配置，也能用我们扩展的配置

	//使用WebMvcConfigurer可以来扩展SpringMVC的功能
	@Configuration
	public class MyConfig implements WebMvcConfigurer {
	    @Override
	    public void addViewControllers(ViewControllerRegistry registry) {
			// super.addViewControllers(registry);
       		 //浏览器发送 /atguigu 请求来到 success
	        registry.addViewController("/hello").setViewName("success");
	    }
	}

**原理**

1. `WebMvcAutoConfiguration`是SpringMVC的自动配置类
2. 在做其他自动配置时会导入；`@Import(**EnableWebMvcConfiguration**.class)`

	下面这个类是WebMvcAutoConfiguration中的一个内部类

	![](http://120.77.237.175:9080/photos/springboot/38.jpg)
	![](http://120.77.237.175:9080/photos/springboot/39.jpg)

	这里重点看下`DelegatingWebMvcConfiguration`

		@Configuration(
	    proxyBeanMethods = false
		)
		public class DelegatingWebMvcConfiguration extends WebMvcConfigurationSupport {
		    private final WebMvcConfigurerComposite configurers = new WebMvcConfigurerComposite();
		
		    public DelegatingWebMvcConfiguration() {
		    }
		
			//从容器中获取所有的WebMvcConfigurer
		    @Autowired(
		        required = false
		    )
		    public void setConfigurers(List<WebMvcConfigurer> configurers) {
		        if (!CollectionUtils.isEmpty(configurers)) {
		            this.configurers.addWebMvcConfigurers(configurers);
		        }
		
		    }
			......

			/**
		     * 查看其中一个方法
		      * this.configurers：也是WebMvcConfigurer接口的一个实现类
		      * 看一下调用的configureViewResolvers方法 ↓
		      */
		    protected void configureViewResolvers(ViewResolverRegistry registry) {
		        this.configurers.configureViewResolvers(registry);
		    }

			......
		}

	---
		
		 public void configureViewResolvers(ViewResolverRegistry registry) {
	        Iterator var2 = this.delegates.iterator();
	
	        while(var2.hasNext()) {
	            WebMvcConfigurer delegate = (WebMvcConfigurer)var2.next();
				//将所有的WebMvcConfigurer相关配置都来一起调用；
	            delegate.configureViewResolvers(registry);
	        }
	
	    }

3. 容器中所有的WebMvcConfigurer都会一起起作用
4. 我们的配置类也会被调用

效果：SpringMVC的自动配置和我们的扩展配置都会起作用

![](http://120.77.237.175:9080/photos/springboot/40.jpg)

### 全面接管SpringMVC ###

SpringBoot对SpringMVC的自动配置不需要了，所有都是我们自己配置；所有的SpringMVC的自动配置都失效了

**我们需要在配置类中添加@EnableWebMvc即可；**

![](http://120.77.237.175:9080/photos/springboot/41.jpg)

原理：

为什么`@EnableWebMvc`自动配置就失效了；

1. `@EnableWebMvc`的核心

		@Import({DelegatingWebMvcConfiguration.class})
		public @interface EnableWebMvc {
		}

2. `DelegatingWebMvcConfiguration`
	
		@Configuration(
	    proxyBeanMethods = false
		)
		public class DelegatingWebMvcConfiguration extends WebMvcConfigurationSupport { 
			....
		}
3. `WebMvcAutoConfiguration`

		@Configuration(proxyBeanMethods = false)
		@ConditionalOnWebApplication(type = Type.SERVLET)
		@ConditionalOnClass({ Servlet.class, DispatcherServlet.class, WebMvcConfigurer.class })
		//重点是这个注解，只有当容器中没有这个类型组件的时候该配置类才会生效
		@ConditionalOnMissingBean(WebMvcConfigurationSupport.class)
		@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE + 10)
		@AutoConfigureAfter({ DispatcherServletAutoConfiguration.class, TaskExecutionAutoConfiguration.class,
				ValidationAutoConfiguration.class })
		public class WebMvcAutoConfiguration {
			...
		}

4. `@EnableWebMvc`将`WebMvcConfigurationSupport`组件导入进来
5. 导入的`WebMvcConfigurationSupport`只是SpringMVC最基本的功能

**注意:因此一般在开发中是不用`@EnableWebMvc`开启的,除非是不用加载这么多特定的组件,但一般所定义到的组件都是所需的**

## 如何修改SpringBoot的默认配置 ##

1. SpringBoot在自动配置很多组件的时候，先看容器中有没有用户自己配置的（@Bean、@Component）如果有就用用户配置的，如果没有，才自动配置；如果有些组件可以有多个（ViewResolver）将用户配置的和自己默认的组合起来；
2. 在SpringBoot中会有非常多的xxxConfigurer帮助我们进行扩展配置
3. 在SpringBoot中会有很多的xxxCustomizer帮助我们进行定制配置


## RestfulCRUD ##

### 默认访问首页 ###

template文件加不是静态资源文件夹，默认是无法直接访问的，所以要添加视图映射

	@Configuration
	public class MyConfig implements WebMvcConfigurer {
	    @Override
	    public void addViewControllers(ViewControllerRegistry registry) {
	        registry.addViewController("/").setViewName("login");
	        registry.addViewController("index").setViewName("login");
	        registry.addViewController("index.html").setViewName("login");
	    }
	
	}

### 国际化 ###

1. 编写国际化配置文件
2. 使用ResourceBundleMessageSource管理国际化资源文件
3. 在页面使用fmt:message取出国际化内容

步骤:

1. 编写国际化配置文件，抽取页面需要显示的国际化消息

	![](http://120.77.237.175:9080/photos/springboot/42.jpg)

2. SpringBoot自动配置好了管理国际化资源文件的组件

		@Configuration(proxyBeanMethods = false)
		@ConditionalOnMissingBean(name = AbstractApplicationContext.MESSAGE_SOURCE_BEAN_NAME, search = SearchStrategy.CURRENT)
		@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
		@Conditional(ResourceBundleCondition.class)
		@EnableConfigurationProperties
		public class MessageSourceAutoConfiguration {
		
				private static final Resource[] NO_RESOURCES = {};
			
				；
				@Bean
				@ConfigurationProperties(prefix = "spring.messages")
				public MessageSourceProperties messageSourceProperties() {
					//默认如果没有配置的话是加载类路径下的messages.properties
					return new MessageSourceProperties();
				}
			
				@Bean
				public MessageSource messageSource(MessageSourceProperties properties) {
					
					ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
					 //设置国际化资源文件的基础名（去掉语言国家代码的）
					if (StringUtils.hasText(properties.getBasename())) {
						messageSource.setBasenames(StringUtils
								.commaDelimitedListToStringArray(StringUtils.trimAllWhitespace(properties.getBasename())));
					}
					if (properties.getEncoding() != null) {
						messageSource.setDefaultEncoding(properties.getEncoding().name());
					}
					messageSource.setFallbackToSystemLocale(properties.isFallbackToSystemLocale());
					Duration cacheDuration = properties.getCacheDuration();
					if (cacheDuration != null) {
						messageSource.setCacheMillis(cacheDuration.toMillis());
					}
					messageSource.setAlwaysUseMessageFormat(properties.isAlwaysUseMessageFormat());
					messageSource.setUseCodeAsDefaultMessage(properties.isUseCodeAsDefaultMessage());
					return messageSource;
				}
			.....
		}


	---

		public class MessageSourceProperties {
		
			//我们的配置文件可以直接放在类路径下叫messages.properties
			/**
			 * Comma-separated list of basenames (essentially a fully-qualified classpath
			 * location), each following the ResourceBundle convention with relaxed support for
			 * slash based locations. If it doesn't contain a package qualifier (such as
			 * "org.mypackage"), it will be resolved from the classpath root.
			 */
			private String basename = "messages";
	
		}

3. 在配置文件中添加国际化文件的位置和基础名

		spring.messages.basename=i18n.login

	**如果配置文件中没有配置基础名，就在类路径下找基础名为message的配置文件**

4. 将页面文字改为获取国际化配置

		<!DOCTYPE html>
		<html lang="en" xmlns:th="http://www.thymeleaf.org">
			<head>
				<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
				<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
				<meta name="description" content="">
				<meta name="author" content="">
				<title>Signin Template for Bootstrap</title>
				<!-- Bootstrap core CSS -->
				<link href="asserts/css/bootstrap.min.css" th:href="@{/webjars/bootstrap/4.4.1-1/css/bootstrap.min.css}" rel="stylesheet">
				<!-- Custom styles for this template -->
				<link href="asserts/css/signin.css" th:href="@{/asserts/css/signin.css}"  rel="stylesheet">
			</head>
		
			<body class="text-center">
				<form class="form-signin" action="dashboard.html">
					<img class="mb-4" src="asserts/img/bootstrap-solid.svg" th:src="@{/asserts/img/bootstrap-solid.svg}"  alt="" width="72" height="72">
					<h1 class="h3 mb-3 font-weight-normal" th:text="#{login.tip}">Please sign in</h1>
					<label class="sr-only" th:text="#{login.username}">Username</label>
					<input type="text" class="form-control" placeholder="Username" th:placeholder="#{login.username}" required="" autofocus="">
					<label class="sr-only" th:text="#{login.password}">Password</label>
					<input type="password" class="form-control" placeholder="Password" th:placeholder="#{login.password}" required="">
					<div class="checkbox mb-3">
						<label>
		          <input type="checkbox" value="remember-me">[[#{login.remember}]]
		        </label>
					</div>
					<button class="btn btn-lg btn-primary btn-block" type="submit" th:text="#{login.btn}">Sign in</button>
					<p class="mt-5 mb-3 text-muted">© 2017-2018</p>
					<a class="btn btn-sm">中文</a>
					<a class="btn btn-sm">English</a>
				</form>
		
			</body>
		
		</html>


	**原理**
	
	- 国际化Locale（区域信息对象）；
	- LocaleResolver（获取区域信息对象）；
	
			@Bean
			@ConditionalOnMissingBean//前提是容器中不存在这个组件,所以使用自己的对象就要配置@Bean让这个条件不成立（实现LocaleResolver 即可）
			@ConditionalOnProperty(prefix = "spring.mvc", name = "locale")//如果在application.properties中有配置国际化就用配置文件的,没有配置就用AcceptHeaderLocaleResolver 默认request中获取
			public LocaleResolver localeResolver() {
				if (this.mvcProperties.getLocaleResolver() == WebMvcProperties.LocaleResolver.FIXED) {
					return new FixedLocaleResolver(this.mvcProperties.getLocale());
				}
				AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
				localeResolver.setDefaultLocale(this.mvcProperties.getLocale());
				return localeResolver;
			}
	
			
	默认的就是根据请求头带来的区域信息获取Locale进行国际化
	
	    public Locale resolveLocale(HttpServletRequest request) {
	        Locale defaultLocale = this.getDefaultLocale();
	        if (defaultLocale != null && request.getHeader("Accept-Language") == null) {
	            return defaultLocale;
	        } else {
	            Locale requestLocale = request.getLocale();
	            List<Locale> supportedLocales = this.getSupportedLocales();
	            if (!supportedLocales.isEmpty() && !supportedLocales.contains(requestLocale)) {
	                Locale supportedLocale = this.findSupportedLocale(request, supportedLocales);
	                if (supportedLocale != null) {
	                    return supportedLocale;
	                } else {
	                    return defaultLocale != null ? defaultLocale : requestLocale;
	                }
	            } else {
	                return requestLocale;
	            }
	        }
	    }

5. 点击链接切换国际化

	- **HTML**
	
	  		<a class="btn btn-sm" href="?l=zh_CN">中文</a>
	        <a class="btn btn-sm" href="?l=en_US">English</a>

	- **实现区域信息解析器**

			public class MyLocaleResolve implements LocaleResolver {
			    @Override
			    public Locale resolveLocale(HttpServletRequest httpServletRequest) {
			        String lan = httpServletRequest.getParameter("l");
			        Locale locale = Locale.getDefault();
			        if(!StringUtils.isEmpty(lan))
			        {
			            String[] strings = lan.split("_");
			             locale = new Locale(strings[0],strings[1]);
			
			        }
			        return locale;
			
			    }
			
			    @Override
			    public void setLocale(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Locale locale) {
			
			    }
			}

		- **在配置类中将其注册到容器中**


				@Configuration
				public class MyConfig implements WebMvcConfigurer {
				    @Override
				    public void addViewControllers(ViewControllerRegistry registry) {
				        // super.addViewControllers(registry);
				        //浏览器发送 /atguigu 请求来到 success
				        registry.addViewController("/").setViewName("login");
				        registry.addViewController("index").setViewName("login");
				        registry.addViewController("index.html").setViewName("login");
				    }
				
				    @Bean
				    public LocaleResolver localeResolver()
				    {
				        return new MyLocaleResolve();
				    }
				
				}


		**注意:如果没有生效，请检查@Bean的那个方法的名称是否为localeResolver**


### 登陆 ###

开发期间模板引擎页面修改以后，要实时生效

1. 禁用模板引擎的缓存

		# 禁用缓存
		spring.thymeleaf.cache=false 


2. 页面修改完成以后ctrl+f9：重新编译；