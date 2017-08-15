/**
 * 利用第三方框架protocol buffer实现对象序列化和逆序列化
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:HttpDemo
 * <br/>Date:Aug，2017
 *
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.serializedemo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class ProtocolBufferDemo {
    //方式1：通过字节数组序列化和反序列化

    //protocol buffer对象->byte[]
    static public byte[] proto2Byte(StudentPB.Student student) {
        return student.toByteArray();
    }
    //byte[]->protocol buffer对象
    static public StudentPB.Student byte2Proto(byte[] data) {
        StudentPB.Student t = null;
        try {
            t = StudentPB.Student.parseFrom(data);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
        return t;
    }

    //方式2：通过输入/输出流序列化和反序列化

    //protocol buffer对象->OutputStream
    static public ByteArrayOutputStream proto2Stream(StudentPB.Student student) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            student.writeTo(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }
    //InputStream->protocol buffer对象
    static public StudentPB.Student stream2Proto(ByteArrayInputStream data) {
        StudentPB.Student t = null;
        try {
            t = StudentPB.Student.parseFrom(data);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return t;
    }

    //object->protocol buffer对象
    static public StudentPB.Student objetc2Proto(Student student) {
        StudentPB.Student s = StudentPB.Student.newBuilder()
                .setName(student.getName())
                .setAge(student.getAge())
                .setIsMale(student.isMale())
                .setWeight(student.getWeight())
                .addAllFavoriteBooks(Arrays.asList(student.getFavoriteBooks()))
                .addAllInterests(student.getInterests())
                .build();

        /*给protocol buffer对象赋初值
        StudentPB.Student s1 = StudentPB.Student.newBuilder()
                .setName("张三")
                .setAge(22)
                .setIsMale(true)
                .setWeight(51.5f)
                .addFavoriteBooks("孙子兵法")
                .addFavoriteBooks("从0到1")
                .addInterests("爬山")
                .addInterests("游泳")
                .addInterests("篮球")
                .build();*/
        return s;
        }

    //protocol buffer对象->object
    static public Student proto2objetc(StudentPB.Student student) {
        Student s = new Student();
        s.setName(student.getName());
        s.setAge(student.getAge());
        s.setMale(student.getIsMale());
        s.setWeight(student.getWeight());
        //将list值转到String数组中
        List<String> ls = student.getFavoriteBooksList();
        String[] sa = new String[student.getFavoriteBooksCount()];
        System.arraycopy(ls.toArray(), 0, sa, 0, sa.length);
        s.setFavoriteBooks(sa);
        //将list值转到HashSet中
        ls = student.getInterestsList();
        HashSet<String> hs = new HashSet<String>();
        hs.addAll(ls);
        s.setInterests(hs);

        return s;
    }
    void setValueForPB() {

    }
}

