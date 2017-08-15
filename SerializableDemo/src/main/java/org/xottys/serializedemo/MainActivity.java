/**
 * 本例演示了Android序列化常用的六种方式：
 * 1）Serializable
 * 2）Parcelable
 * 3）Json
 * 4）XML
 * 5）Protocol Buffer
 * 6）Flat Buffer
 * 方式2只能用来在android内部通过Intent或Message传递(Serializable也可以)
 * 其它方式则主要用来在网络上传递或持久化存储
 * 方式3）4）序列化后是可视文本，其它方式序列化后均为不可视的二进制
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

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;

import flatbuffers.FlatBufferBuilder;

public class MainActivity extends Activity {
    private static final String TAG = "SerializeDemo";
    private static final String str = "对象序列化值如下(可能会有乱码)：";

    private Button bt1, bt2, bt3, bt4, bt5, bt6;
    private LinkedList<Button> buttonList;
    private TextView tv1, tv2, tv3, tv2t;
    private Handler handler;


    private Student s1, s2;      //实现Serializable接口
    private Teacher t1, t2;      //实现Parcelable接口

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt1 = (Button) findViewById(R.id.bt1);
        bt2 = (Button) findViewById(R.id.bt2);
        bt3 = (Button) findViewById(R.id.bt3);
        bt4 = (Button) findViewById(R.id.bt4);
        bt5 = (Button) findViewById(R.id.bt5);
        bt6 = (Button) findViewById(R.id.bt6);
        buttonList=new LinkedList<Button>();
        buttonList.add(bt1);
        buttonList.add(bt2);
        buttonList.add(bt3);
        buttonList.add(bt4);
        buttonList.add(bt5);
        buttonList.add(bt6);

        tv1 = (TextView) findViewById(R.id.tv_1);
        tv2t = (TextView) findViewById(R.id.tv_2t);
        tv2 = (TextView) findViewById(R.id.tv_2);
        tv3 = (TextView) findViewById(R.id.tv_3);

        for (Button bt:buttonList) {
            bt.setBackgroundColor(0xbd292f34);
            bt.setTextColor(0xFFFFFFFF);
        }
//        bt2.setBackgroundColor(0xbd292f34);
//        bt2.setTextColor(0xFFFFFFFF);
//        bt3.setBackgroundColor(0xbd292f34);
//        bt3.setTextColor(0xFFFFFFFF);
//        bt4.setBackgroundColor(0xbd292f34);
//        bt4.setTextColor(0xFFFFFFFF);
//        bt5.setBackgroundColor(0xbd292f34);
//        bt5.setTextColor(0xFFFFFFFF);
//        bt6.setBackgroundColor(0xbd292f34);
//        bt6.setTextColor(0xFFFFFFFF);

        s1 = new Student("张三", 22, true, 51.5f, new String[]{"孙子兵法", "从0到1"}, new HashSet<String>(Arrays.asList("爬山", "游泳", "篮球")));
        t1 = new Teacher("李四", 42, true, 75.5f, new String[]{"史记", "三国演义"}, new HashSet<String>(Arrays.asList("语文", "数学", "物理")));

        //Serializabe的对象序列化为字节数组
        bt1.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                tv1.setText(s1.toString());
                //object->byte[]
                byte[] b = OutputWriteDemo.object2Byte(s1);

                tv2t.setText(str + b.length);
                tv2.setText(new String(b));

                //byte[]->object
                s2 = OutputWriteDemo.byte2Object(b);

                tv3.setText(s2.toString());
                Log.i(TAG, "s2= " + s2.toString());

                bt1.setBackgroundColor(0xFFD7D7D7);
                buttonList.remove(bt1);
                for (Button bt:buttonList) {
                    bt.setBackgroundColor(0xbd292f34);
                }
                buttonList.add(bt1);

            }
        });

        //Serializabe和Parcelable对象在android内部组件间或线程间传递
        bt2.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                tv1.setText("Serializable-->"+s1.toString());
                tv1.append("\nParcelable-->" + t1.toString());
                tv2t.setText(str);
                tv2.setText("");
                handler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        //解析Handler传回的对象
                        s2 = (Student) msg.getData().getSerializable("s1");
                        t2 = (Teacher) msg.getData().getParcelable("t1");
                        //显示对象解析结果
                        tv3.setText("Serializable-->"+s2.toString());
                        tv3.append("\nParcelable-->" + t2.toString());
                        Log.i(TAG, "s2= " + s2.toString());
                    }
                };
                //启动对象传递线程
                new mThread().start();
                bt2.setBackgroundColor(0xFFD7D7D7);
                buttonList.remove(bt2);
                for (Button bt:buttonList) {
                    bt.setBackgroundColor(0xbd292f34);
                }
                buttonList.add(bt2);
            }
        });

        //对象序列化为Json
        bt3.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                tv1.setText(s1.toString());

                //object->json字符串
                String jsonString = JsonDemo.object2Json(s1);

                tv2t.setText(str + jsonString.getBytes().length);
                tv2.setText(jsonString);

                //json字符串->object
                s2 = JsonDemo.json2Object(jsonString, Student.class);

                tv3.setText(s2.toString());
                Log.i(TAG, "s2= " + s2.toString());
                bt3.setBackgroundColor(0xFFD7D7D7);
                buttonList.remove(bt3);
                for (Button bt:buttonList) {
                    bt.setBackgroundColor(0xbd292f34);
                }
                buttonList.add(bt3);
            }
        });

        //对象序列化为XML
        bt4.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                tv1.setText(s1.toString());
                //object->XML字符串
                String xmlString = XMLDemo.object2Xml(s1);

                tv2t.setText(str + xmlString.getBytes().length);
                tv2.setText(xmlString);

                //XML字符串->object
                s2 = (Student) XMLDemo.xml2Pbject(xmlString);

                tv3.setText(s2.toString());
                Log.i(TAG, "s2= " + s2.toString());

                bt4.setBackgroundColor(0xFFD7D7D7);
                buttonList.remove(bt4);
                for (Button bt:buttonList) {
                    bt.setBackgroundColor(0xbd292f34);
                }
                buttonList.add(bt4);
            }
        });

        //对象序列化为Protocol Buffer
        bt5.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                tv1.setText(s1.toString());
                //object->protocol buffer对象->byte[]
                StudentPB.Student ps1 = ProtocolBufferDemo.objetc2Proto(s1);
                byte[] b = ProtocolBufferDemo.proto2Byte(ps1);

                tv2t.setText(str + b.length);
                tv2.setText(new String(b));

                //byte[]->protocol buffer对象->object
                StudentPB.Student ps2 = ProtocolBufferDemo.byte2Proto(b);
                Student s2 = ProtocolBufferDemo.proto2objetc(ps2);

                tv3.setText(s2.toString());
                Log.i(TAG, "s2= " + s2.toString());

                bt5.setBackgroundColor(0xFFD7D7D7);
                buttonList.remove(bt5);
                for (Button bt:buttonList) {
                    bt.setBackgroundColor(0xbd292f34);
                }
                buttonList.add(bt5);
                /*另一种protocol buffer序列化方法

                //object->protocol buffer对象->OutputStream->byte[]
                ByteArrayOutputStream ba = ProtocolBufferDemo.proto2Stream(ps1);
                b = ba.toByteArray();

                //byte[]->InputStream->protocol buffer对象->object
                ByteArrayInputStream bi = new ByteArrayInputStream(b);
                ps2 = ProtocolBufferDemo.stream2Proto(bi);
                */
            }
        });

        //对象序列化为Flat Buffer
        bt6.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                tv1.setText(s1.toString());
                //object->FlatBufferBuilder ->byte[]
                FlatBufferBuilder builder = FlatBufferDemo.objetc2Flatbuffer(s1);
                byte[] b = FlatBufferDemo.flatBuffer2Byte(builder);

                tv2t.setText(str + b.length);
                tv2.setText(new String(b));

                //byte[]->flat buffer对象->object
                StudentFB fs = FlatBufferDemo.byte2FlatBuffer(b);
                Student s2 = FlatBufferDemo.flatbuffer2objetc(fs);

                tv3.setText(s2.toString());
                Log.i(TAG, "s2= " + s2.toString());

                bt6.setBackgroundColor(0xFFD7D7D7);
                buttonList.remove(bt6);
                for (Button bt:buttonList) {
                    bt.setBackgroundColor(0xbd292f34);
                }
                buttonList.add(bt6);
            }
        });
        Log.i(TAG, "s1= " + s1.toString());
    }

    //将Serializabe和Parcelable对象放在Message中，然后用Handler发送
    //将Serializabe和Parcelable对象直接在android内部传递，主要有Intent和Handler两种方式
    //Intent方式可以直接intent.putExtra()，也可以用bundle.putSerializable()／bundle.putParcelable()，然后通过 intent.putExtras(bundle);
    //Handler方式只能用bundle.putSerializable／bundle.putParcelable()，然后通过message.setData(bundle)，将其装入Message中
    class mThread extends Thread {
        @Override
        public void run() {
            Message msg = Message.obtain();
            Bundle bundle = new Bundle();
            bundle.putSerializable("s1", s1);
            bundle.putParcelable("t1", t1);
            msg.setData(bundle);
            handler.sendMessage(msg);
        }
    }
}
