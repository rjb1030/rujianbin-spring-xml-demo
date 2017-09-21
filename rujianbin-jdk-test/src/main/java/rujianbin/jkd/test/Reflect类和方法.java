package rujianbin.jkd.test;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;

/**
 * Created by rujianbin@xinyunlian.com on 2017/9/20.
 */
public class Reflect类和方法 {

    public static void main(String[] args) throws Exception{


        System.out.println("");System.out.println("");System.out.println("");
        System.out.println("=========================Reflect类和方法====================================");
        Constructor[] cons = Test1.class.getConstructors();
        System.out.println("Test1所有构造函数  "+Arrays.toString(cons));

        Constructor<Test1> con = Test1.class.getConstructor(Long.class);
        System.out.println("Test1获取指定构造函数"+con);

        Method[] methods = Test1.class.getMethods();
        System.out.println("Test1所有公共方法(包括自身声明的和继承的)  "+Arrays.toString(methods));

        Method[] methods2 = Test1.class.getDeclaredMethods();
        System.out.println("Test1所有自己声明的方法（包含私有方法）     "+Arrays.toString(methods2));

        Test1 test1 = con.newInstance(Long.valueOf(1));
        Method m = Test1.class.getDeclaredMethod("getPrivateValue",String.class);
        m.setAccessible(true);
        System.out.println("构造实例，执行私有方法，方法名="+m.getName()+"    方法参数类型="+Arrays.toString(m.getParameterTypes())+"  执行结果="+m.invoke(test1,"参数你好"));


        Test2 test2 = new Test2();
        System.out.println(test2.msg);
        System.out.println(Arrays.toString(Test2.class.getDeclaredMethods())); //通过以上2步后，Test2编译合成了一个方法access，而该方法的isSynthetic是true


        System.out.println("");System.out.println("");System.out.println("");
    }


    public interface MyInterface1 extends Serializable {
        String print();
    }

    public interface MyInterface2{
        String print2();
    }

    public static abstract class MyAbstract1<T> implements MyInterface1 {

        @Override
        public String print() {
            return "你好";
        }
    }


    public static class Test1<T,V> extends MyAbstract1<V> implements MyInterface2,Comparable<T>{

        private Long id;
        private String name;

        public Test1(){

        }

        public Test1(Long id){
            this.id=id;
        }

        public Test1(String name){
            this.name=name;
        }

        @Override
        public int compareTo(T o) {
            return 0;
        }

        @Override
        public String print2() {
            return "我是print2方法";
        }

        private String getPrivateValue(String ss){
            return ss+"-私有方法";
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }


        public void setName(String name) {
            this.name = name;
        }
    }

    public static class Test2{
        private String msg="测试isSynthetic 即合成方法或合成类";
    }
}
