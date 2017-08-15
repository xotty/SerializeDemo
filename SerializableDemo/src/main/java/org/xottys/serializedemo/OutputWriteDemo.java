/**
 * 利用java OutputStream／InputStream将Serializabe对象与字节数组进行互换
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
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class OutputWriteDemo {
    //object->ObjectOutputStream->byte[]
    static public byte[] object2Byte(Object obj) {
        byte data[] = null;
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(arrayOutputStream);
            objectOutputStream.writeObject(obj);
            objectOutputStream.flush();
            data = arrayOutputStream.toByteArray();
            objectOutputStream.close();
            arrayOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    //byte[]->ObjectIntputStream->object
    static public <T> T byte2Object(byte[] data) {
        T t = null;
        ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(data);
        try {
            ObjectInputStream inputStream = new ObjectInputStream(arrayInputStream);
            t = (T) inputStream.readObject();
            inputStream.close();
            arrayInputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }
}
