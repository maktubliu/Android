package com.example;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Created by LHH on 2016/4/8.
 */
public class LoadTest {
    public static void main(String[] args) throws Exception{
        Class<?> cls = Class.forName("com.example.MyClass");
        Object o = cls.newInstance();
        String nameString = cls.getClassLoader().getClass().getName();
        System.out.println(nameString);
        Method method = cls.getMethod("run",String.class);
        method.invoke(o, "ok");
        Constructor<?> constructor = cls.getConstructor();
        MyClass mc = (MyClass)constructor.newInstance();
        System.out.println(mc);
    }
}
