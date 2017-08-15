//模拟对象，实现Serializable接口
package org.xottys.serializedemo;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;


public class Student implements Serializable {
    private static final long serialVersionUID = -4346233399222979476L;

    private String name;
    private int age;
    private boolean isMale;
    private float weight;
    private String[] favoriteBooks;
    private HashSet<String> interests;

    public Student() {
        super();
    }

    public Student(String name, int age, boolean isMale, float weight, String[] favoriteBooks, HashSet<String> interests) {
        super();
        this.name = name;
        this.age = age;
        this.isMale = isMale;
        this.weight = weight;
        this.favoriteBooks = favoriteBooks;
        this.interests = interests;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isMale() {
        return isMale;
    }

    public void setMale(boolean male) {
        isMale = male;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String[] getFavoriteBooks() {
        return favoriteBooks;
    }

    public void setFavoriteBooks(String[] favoriteBooks) {
        this.favoriteBooks = favoriteBooks;
    }

    public HashSet<String> getInterests() {

        return interests;
    }

    public void setInterests(HashSet<String> interests) {
        this.interests = interests;
    }

    //此重写方法不是必须的，此处是为本Demo输出方便而添加
    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", isMale=" + isMale +
                ", weight=" + weight +
                ", favoriteBooks=" + Arrays.toString(favoriteBooks) +
                ", interests=" + interests +
                '}';
    }
}
