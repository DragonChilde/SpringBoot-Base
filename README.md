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