package cn.wqz.study.springboot.swagger2.entity;

import javax.validation.constraints.Max;

public class User {
    @Max(value = 4, message = "输入id超出最大值")
    private int id;

    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
