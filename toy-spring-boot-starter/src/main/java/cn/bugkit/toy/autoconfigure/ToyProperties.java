package cn.bugkit.toy.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

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
