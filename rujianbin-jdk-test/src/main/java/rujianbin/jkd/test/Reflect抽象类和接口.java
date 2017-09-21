package rujianbin.jkd.test;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

/**
 * Created by rujianbin@xinyunlian.com on 2017/9/20.
 */
public class Reflect抽象类和接口 {
    public static void main(String[] args) {

        /**
         * Class和ParameterizedType都是集成自Type。   ParameterizedType指参数化类型，即包含泛型类型
         *
         */


        System.out.println("===============================Class getGenericInterfaces() ====================");
        Type[] types = new Test1().getClass().getGenericInterfaces();
        for(Type type : types){
            System.out.println(type+"               具体实现类="+type.getClass());
            if(type instanceof ParameterizedType ){
                ParameterizedType p = (ParameterizedType)type;
                System.out.println("ParameterizedType中又可以继续获取Class="+p.getRawType()+"        泛型="+ Arrays.toString(p.getActualTypeArguments()));
            }
        }


        System.out.println("");System.out.println("");System.out.println("");
        System.out.println("===============================Class getInterfaces() =========================");
        Class[] classes = new Test1().getClass().getInterfaces();
        for(Class clazz : classes){
            System.out.println(clazz);
        }


        System.out.println("");System.out.println("");System.out.println("");
        System.out.println("===============================Class getGenericSuperclass()=========================");
        Type t = new Test1().getClass().getGenericSuperclass();
        System.out.println("继承的抽象类 "+t+"       类型="+t.getClass());
        if(t instanceof ParameterizedType){
            ParameterizedType p = (ParameterizedType)t;
            System.out.println("ParameterizedType中又可以继续获取Class="+p.getRawType()+"   泛型="+Arrays.toString(p.getActualTypeArguments()));
        }


        System.out.println("");System.out.println("");System.out.println("");
        System.out.println("===============================Class getSuperclass()=========================");
        Class cla = new Test1().getClass().getSuperclass();
        System.out.println("继承的抽象类="+cla+"     类型="+cla.getClass());

    }

    public interface MyInterface1 extends Serializable{
        String print();
    }

    public interface MyInterface2{
        String print2();
    }

    public static abstract class MyAbstract1<T> implements MyInterface1{

        @Override
        public String print() {
            return "你好";
        }
    }


    public static class Test1<T,V> extends MyAbstract1<V> implements MyInterface2,Comparable<T>{
        @Override
        public int compareTo(T o) {
            return 0;
        }

        @Override
        public String print2() {
            return null;
        }
    }


}
