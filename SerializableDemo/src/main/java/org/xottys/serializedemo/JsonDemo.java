/**
 * 利用第三方框架fastjson实现Json封装和解析
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

import com.alibaba.fastjson.JSON;

public class JsonDemo {

    //object->json字符串
    static public String object2Json(Object obj) {
        String jsonString = JSON.toJSONString(obj);
        return jsonString;
    }

    //json字符串->object
    static public <T> T json2Object(String jsonString, Class<T> clazz) {
        T t = JSON.parseObject(jsonString, clazz);
        return t;
    }

}


