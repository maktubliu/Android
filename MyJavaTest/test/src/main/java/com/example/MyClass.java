package com.example;

import
        com.sun.java.util.jar.pack.*;

import org.omg.CORBA.*;

import java.lang.Object;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class MyClass {
    public int age;
    public static int count;
    private String name;
    static {
        count = 0;
    }
    MyClass(){
        age = count++;
        System.out.println("This is MyClass");
    }
    public void run(String name){
        System.out.println("What the fuck!" + name);
    }
    private void frun(){
    }
    public static void main(String[] args) throws Exception{
        Class c = MyClass.class;
        Field[] fields = c.getDeclaredFields();
        for (Field field : fields){
            System.out.println(field);
        }
        Method[] methods = c.getDeclaredMethods();
        for (Method method : methods){
            System.out.println(method);
        }
        try{
            MyClass myClass = (MyClass)c.newInstance();
        }catch (InstantiationException e){
            e.printStackTrace();
        }catch (IllegalAccessException e){
            e.printStackTrace();
        }

        /*try{
            Class myClass1 = Class.forName("MyClass");
            Field name = myClass1.getDeclaredField("name");
            name.setAccessible(true);
            MyClass myClass2 = (MyClass) myClass1.newInstance();
            name.set(myClass2, "liu");
            System.out.println(myClass2.getClass());
        }catch (InstantiationException e){
            e.printStackTrace();
        }catch (NoSuchFieldException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }catch (IllegalAccessException e){
            e.printStackTrace();
        }*/
        /*Constructor cr = c.getConstructor();
        MyClass myClass = (MyClass)cr.newInstance();*/


    }
}
