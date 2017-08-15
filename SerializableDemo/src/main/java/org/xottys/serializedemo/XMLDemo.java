/**
 * 利用第三方框架xStream实现XML封装和解析
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

import com.thoughtworks.xstream.XStream;

public class XMLDemo {

    //object->XML字符串
    public static <T> String object2Xml(T t) {
        XStream xstream = new XStream();
        String xmlString = xstream.toXML(t);
        return xmlString;
    }

    //XML字符串->object
    public static <T> T xml2Pbject(String xmlString) {
        XStream xstream = new XStream();
        T t = (T) xstream.fromXML(xmlString);
        return t;
    }
}

