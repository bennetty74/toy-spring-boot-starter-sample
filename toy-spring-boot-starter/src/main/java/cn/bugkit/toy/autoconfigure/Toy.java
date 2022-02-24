package cn.bugkit.toy.autoconfigure;

/**
 * @author bugkit
 * @since 2022.2.24
 */
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
