package com.zz.classes;

/**
 * @ClassName Student
 * @Description TODO
 * @Author carl
 * @Date 2020/11/15 09:19
 * @Version 1.0
 **/
public class Student implements Comparable<Student> {
    private int scroe;
    private int age;
    private int id;

    public Student(int scroe, int age, int id) {
        this.scroe = scroe;
        this.age = age;
        this.id = id;
    }

    public Student(int scroe, int age) {
        this.scroe = scroe;
        this.age = age;
        this.id = 1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setScroe(int scroe) {
        this.scroe = scroe;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getScroe() {
        return scroe;
    }

    public int getAge() {
        return age;
    }

    @Override
    public int compareTo(Student o) {
        return age - o.getAge();
    }
}
