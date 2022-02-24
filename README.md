
# 本文介绍

我们都知道Spring Boot项目由一个个的starter构成，比如web的starter，redis的starter等等。
我们通过maven引入对应的starter，几乎无须配置就可以完美使用集成的spring mvc或redis.

**那么我们如何自定义一个starter呢？**


本文用将示如何使用SpringBoot的Auto-Configuration特性创建自己的**Spring Boot Starter**。



假设`Toy`为我们需要集成为starter框架或者类，并且希望在应用启动的时候就加载到Spring上下文中供我们调用。

**有的小伙伴可能会问为什么要这样做？直接将Toy声明为Spring的bean不就好了。**

答案肯定是可以这样做，不过秉承Spring Boot快速上手的原则，通过引入starter的方式可以更加快速使用集成的我们想要的功能或者特性，就好比上面的`Toy`。

又比如mybatis，因为我们无法修改mybatis源码，途径之一是通过`@Bean`的方式在方法上创建`SqlSessionFactory` bean，并在Spring Boot应用中使用。

途径二则是通过创建starter，需要用到mybatis的地方引入mybatis的starter就可完成mybatis的集成。

**在很多项目需要集成mybatis的情况下，starter的方式会更为快速高效。**


# 项目结构

本项目分为两个模块：

`toy-sample-app` 和`toy-spring-boot-starter`

* **toy-sample-app**: 演示如何引入自己创建的starter

- **toy-spring-boot-starter**: starter模块，用于构建`Toy` Spring Bean 供演示项目使用


# 代码介绍


## toy-spring-boot-starter

- **META-INF/spring.factories文件**

该文件指明了自动注入的的类名，Spring Boot应用启动的时候会将该类初始化并加载到上下文中。

```java
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
        cn.bugkit.toy.autoconfigure.ToyAutoConfiguration
```


- **ToyAutoConfiguration文件**

**@Configuration注解说明是Spring的配置类**

**@EnableConfigurationProperties(ToyProperties.class)注解指定了需要使用的属性配置类**

```java
package cn.bugkit.toy.autoconfigure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author bugkit
 * @since 2022.2.24
 */
@Configuration
@EnableConfigurationProperties(ToyProperties.class)
public class ToyAutoConfiguration {

    @Autowired
    private ToyProperties toyProperties;

    @Bean
    public Toy toy(){
        return new Toy(toyProperties.getName(),toyProperties.getPassword(), toyProperties.getWeight());
    }

}

```

- ToyProperties文件

该类声明了需要从`application.properties`文件读取的属性，供上述文件的`toy()`方法构建所需要的`Toy`对象。

```java
/**
 * @author bugkit
 * @since 2022.2.24
 */
@Configuration
@ConfigurationProperties(prefix = "toy")
public class ToyProperties {
    private String name;
    private String password;
    private int weight;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}

```


- **Toy文件**

提供了我们需要的`showInfo()`方法，具体在`toy-sample-app`项目中用到

```java
public class Toy {
    private String name;
    private String password;
    private int weight;

    public Toy(String name, String password, int weight) {
        this.name = name;
        this.password = password;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void showInfo(){
        System.out.println("===================start==========================");
        System.out.println("Toy [ Name: " + name +", password: " + password +", weight: " + weight + " ]");
        System.out.println("===================end============================");
    }
}
```

## toy-sample-app项目

该项目用于演示如何使用我们自己构建的toy-spring-boot-starter

- **pom文件**

pom文件引入starter项目的jar

```xml
 <dependency>
    <groupId>cn.bugkit</groupId>
    <artifactId>toy-spring-boot-starter</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

- **ToyService文件**

该类注入starter项目的`Toy` bean，并在ToyService初始化完成后调用`Toy`的`showInfo()`方法

```java
package cn.bugkit.toy.app.service;

import cn.bugkit.toy.autoconfigure.Toy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author bugkit
 * @since 2022.2.24
 */
@Service
public class ToyService {

    @Autowired
    private Toy toy;

    @PostConstruct
    public void init() {
        toy.showInfo();
    }

}

```

- **application.properties文件**

该文件的key(如`toy.name`)对应starter项目的`ToyProperties`类。

```properties
toy.name=Hello Kitty
toy.password=kitty@1234
toy.weight=99
```


- **控制台启动日志**
```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v2.6.3)

2022-02-24 14:53:24.092  INFO 14652 --- [           main] c.bugkit.toy.app.StarterDemoApplication  : Starting StarterDemoApplication using Java 1.8.0_211 on Bennetty74 with PID 14652 (E:\ideaProjects\spring-learning\toy-sample-app\target\classes started by Bennetty74 in E:\ideaProjects\spring-learning)
2022-02-24 14:53:24.092  INFO 14652 --- [           main] c.bugkit.toy.app.StarterDemoApplication  : No active profile set, falling back to default profiles: default
===================start==========================
Toy [ Name: Hello Kitty, password: kitty@1234, weight: 99 ]
===================end============================
2022-02-24 14:53:24.608  INFO 14652 --- [           main] c.bugkit.toy.app.StarterDemoApplication  : Started StarterDemoApplication in 0.888 seconds (JVM running for 1.836)

Process finished with exit code 0
```

# 总结

- `toy-spring-boot-starter`： 我们通过Spring Boot 的Auto-Configuration特性集成`Toy`

- `toy-sample-app`：在需要用到的starter的地方引入starter的maven依赖，从而可以使用`Toy`对应的Spring Bean

# 可选

> 剩余代码可以查看Github仓库源码，这里不再赘述。

代码地址：https://github.com/bennetty74/toy-spring-boot-starter-sample
