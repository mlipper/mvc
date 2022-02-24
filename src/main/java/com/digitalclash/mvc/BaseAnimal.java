package com.digitalclash.mvc;

public abstract class BaseAnimal implements Animal {
    protected String name;
    protected Integer age;

    public BaseAnimal() {
        super();
    }

    public BaseAnimal(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
