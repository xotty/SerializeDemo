/**
 * 利用第三方框架flat buffer实现对象序列化和逆序列化
 * 该框架目前没有Gradle本版本，而将其框架直接放到flatbuffers目录下
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

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashSet;

import flatbuffers.FlatBufferBuilder;

public class FlatBufferDemo {

    //FlatBufferBuilder ->byte[]
    static public byte[] flatBuffer2Byte(FlatBufferBuilder builder) {
        byte[] originalData = builder.dataBuffer().array();
        byte[] dataByte = Arrays.copyOfRange(originalData, builder.dataBuffer().position(), (builder.dataBuffer().position() + builder.offset()));
        return dataByte;
    }
    //byte[]->flat buffer对象
    static public StudentFB byte2FlatBuffer(byte[] data) {
        ByteBuffer bb = ByteBuffer.wrap(data);
        return StudentFB.getRootAsStudentFB(bb);
    }

    //object->FlatBufferBuilder
    static public FlatBufferBuilder objetc2Flatbuffer(Student student) {
        FlatBufferBuilder builder = new FlatBufferBuilder();

        //初始化准备，非标量数据要提前转换成位移量
        int name = builder.createString(student.getName());
        int[] favoriteBooksOffsets = new int[student.getFavoriteBooks().length];
        int i = 0;
        for (String fb : student.getFavoriteBooks()) {
            favoriteBooksOffsets[i] = builder.createString(fb);
            i++;
        }
        int favoriteBooksVector = StudentFB.createFavoriteBooksVector(builder, favoriteBooksOffsets);
        int[] interestsOffsets = new int[student.getInterests().size()];
        i = 0;
        for (String fb : student.getInterests()) {
            interestsOffsets[i] = builder.createString(fb);
            i++;
        }
        int interestsVector = StudentFB.createInterestsVector(builder, interestsOffsets);
        //初始化生成StudentFB
        int offset = StudentFB.createStudentFB(builder, name, student.getAge(), student.isMale(), student.getWeight(), favoriteBooksVector, interestsVector);

        /*另一种初始化赋值方式
        int name = builder.createString("张三");
        int[] favoriteBooksOffsets = new int[2];
        favoriteBooksOffsets[0] = builder.createString("孙子兵法");
        favoriteBooksOffsets[1] = builder.createString("从0到1");
        int favoriteBooksVector =StudentFB.createFavoriteBooksVector(builder,favoriteBooksOffsets);
        int[] interestsOffsets = new int[3];
        interestsOffsets[0] = builder.createString("爬山");
        interestsOffsets[1] = builder.createString("游泳");
        interestsOffsets[2] = builder.createString("篮球");
        int interestsVector =StudentFB.createInterestsVector(builder,interestsOffsets);
        //初始化生成StudentFB
        StudentFB.startStudentFB(builder);
        StudentFB.addName(builder,name);
        StudentFB.addAge(builder,(short)21);
        StudentFB.addIsMale(builder,true);
        StudentFB.addWeight(builder,(float)51.5);
        StudentFB.addFavoriteBooks(builder,favoriteBooksVector);
        StudentFB.addInterests(builder,interestsVector);
        int offset=StudentFB.endStudentFB(builder);*/

        builder.finish(offset);
        return builder;
    }
    //flat buffer对象->object
    static public Student flatbuffer2objetc(StudentFB student) {
        Student s = new Student();
        s.setName(student.name());
        s.setAge((int) student.age());
        s.setMale(student.isMale());
        s.setWeight(student.weight());
        ;
        String[] favoriteBooks = new String[student.favoriteBooksLength()];
        for (int i = 0; i < student.favoriteBooksLength(); i++) {
            favoriteBooks[i] = student.favoriteBooks(i);
        }
        s.setFavoriteBooks(favoriteBooks);
        HashSet<String> hs = new HashSet<String>(student.interestsLength());
        for (int i = 0; i < student.interestsLength(); i++) {
            hs.add(student.interests(i));
        }
        s.setInterests(hs);
        return s;
    }
}
