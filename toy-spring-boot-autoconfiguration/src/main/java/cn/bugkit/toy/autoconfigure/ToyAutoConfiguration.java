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
