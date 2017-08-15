//模拟对象，实现Parcelable接口
package org.xottys.serializedemo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;
import java.util.HashSet;


public class Teacher implements Parcelable {

    public static final Creator<Teacher> CREATOR = new Creator<Teacher>() {
        @Override
        public Teacher createFromParcel(Parcel source) {
            return new Teacher(source);
        }

        @Override
        public Teacher[] newArray(int size) {
            return new Teacher[size];
        }
    };
    String name;
    int age;
    boolean isMale;
    float weight;
    String[] favoriteBooks;
    HashSet<String> teachingCourses;

    public Teacher(String name, int age, boolean isMale, float weight, String[] favoriteBooks, HashSet<String> teachingCourses) {
        this.name = name;
        this.age = age;
        this.isMale = isMale;
        this.weight = weight;
        this.favoriteBooks = favoriteBooks;
        this.teachingCourses = teachingCourses;
    }

    public Teacher() {
    }

    protected Teacher(Parcel in) {
        this.name = in.readString();
        this.age = in.readInt();
        this.isMale = in.readByte() != 0;
        this.weight = in.readFloat();
        this.favoriteBooks = in.createStringArray();
        this.teachingCourses = (HashSet<String>) in.readSerializable();
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

    public HashSet<String> getTeachingCourses() {
        return teachingCourses;
    }

    public void setTeachingCourses(HashSet<String> teachingCourses) {
        this.teachingCourses = teachingCourses;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.age);
        dest.writeByte(this.isMale ? (byte) 1 : (byte) 0);
        dest.writeFloat(this.weight);
        dest.writeStringArray(this.favoriteBooks);
        dest.writeSerializable(this.teachingCourses);
    }

    //此重写方法不是必须的，此处是为本Demo输出方便而添加
    @Override
    public String toString() {
        return "Teacher{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", isMale=" + isMale +
                ", weight=" + weight +
                ", favoriteBooks=" + Arrays.toString(favoriteBooks) +
                ", teachingCourses=" + teachingCourses +
                '}';
    }
}
