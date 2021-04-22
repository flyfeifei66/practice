package com.zhaojufei.practice.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectTest {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException,
            InvocationTargetException, InstantiationException, NoSuchFieldException {

        /******************* 构造方法 ***********************/
        Student stu1 = new Student();

        Class class1 = stu1.getClass();

        // System.out.println(class1.getName());

        Class class2 = Student.class;

        // System.out.println(class2.getName());

        // System.out.println(class1 == class2);

        Class class3 = Class.forName("com.zhaojufei.practice.reflect.Student");

        // System.out.println(class2 == class3);

        Constructor[] conArray = class3.getConstructors();

        // Lists.newArrayList(conArray).forEach(e -> System.out.println(e));

        conArray = class3.getDeclaredConstructors();

        // Lists.newArrayList(conArray).forEach(e -> System.out.println(e));

        Constructor con = class3.getConstructor();

        // System.out.println("con = " + con);

        Object obj = con.newInstance();

        // System.out.println("obj=" + obj);

        Student stu = (Student) obj;

        // System.out.println("obj=" + obj);

        con = class3.getDeclaredConstructor(int.class);

        // System.out.println(con);

        // 调用构造方法
        con.setAccessible(true);// 暴力访问(忽略掉访问修饰符)

        obj = con.newInstance(2);

        /******************* 成员变量 ***********************/

        Class stuClass = Class.forName("com.zhaojufei.practice.reflect.Student");

        Field[] fieldArray = stuClass.getFields();

        // Lists.newArrayList(fieldArray).forEach(e -> System.out.println(e));

        fieldArray = stuClass.getDeclaredFields();

        // Lists.newArrayList(fieldArray).forEach(e -> System.out.println(e));

        Field f = stuClass.getField("name");

        // System.out.println(f);

        // 获取一个对象
        Object obj2 = stuClass.getConstructor().newInstance();// 产生Student对象--》Student stu = new Student();

        // 为字段设置值
        f.set(obj2, "刘德华");// 为Student对象中的name属性赋值--》stu.name = "刘德华"

        // 验证
        Student stu2 = (Student) obj2;

        // System.out.println("验证姓名：" + stu2.name);

        // System.out.println("**************获取私有字段****并调用********************************");

        f = stuClass.getDeclaredField("phoneNum");

        // System.out.println(f);

        f.setAccessible(true);// 暴力反射，解除私有限定
        f.set(obj2, "18888889999");

        // System.out.println("验证电话：" + stu2);

        /************* 成员方法 *********************/

        Method[] methodArray = stuClass.getMethods();

        // Lists.newArrayList(methodArray).forEach(e -> System.out.println(e));

        methodArray = stuClass.getDeclaredMethods();

        // Lists.newArrayList(methodArray).forEach(e -> System.out.println(e));

        Method m = stuClass.getMethod("show1", String.class);

        // System.out.println(m);

        Object obj3 = stuClass.getConstructor().newInstance();

        m.invoke(obj3, "刘德华");

        m = stuClass.getDeclaredMethod("show4", int.class);

        // System.out.println(m);

        m.setAccessible(true);// 解除私有限定

        Object result = m.invoke(obj3, 20);// 需要两个参数，一个是要调用的对象（获取有反射），一个是实参

        // System.out.println("返回值：" + result);

    }
}
