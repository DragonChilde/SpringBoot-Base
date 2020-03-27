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