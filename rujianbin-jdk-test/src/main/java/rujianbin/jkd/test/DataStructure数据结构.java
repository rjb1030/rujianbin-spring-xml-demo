package rujianbin.jkd.test;

import org.apache.commons.collections.list.TreeList;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by rujianbin@xinyunlian.com on 2017/9/21.
 */
public class DataStructure数据结构 {

    public static void main(String[] args) {
        Map map1 = new HashMap(10,0.75f);
        Map map2 = new LinkedHashMap();
        Map map3 = new TreeMap();
        Map map4 = new Hashtable();
        /**
         * jdk1.8前分段锁实现。1.8后通过Cas无锁算法取数组元素（取数组元素时防并发），synchronized锁定链头则添加元素防并发
         */
        Map map5 = new ConcurrentHashMap();


        List list1 = new ArrayList();
        List list2 = new LinkedList();
        List list3 = new TreeList();


    }

}
