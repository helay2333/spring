package org.green.web.mvc;

import org.green.web.context.AnnotationConfigWebApplicationContext;
import org.green.web.test.service.UserService;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Green写代码
 * @date 2024-02-14 02:23
 */
public class Main {

//    public static void main(String[] args) {
//        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
//        context.register(AppConfig.class);
//        context.refresh();
//        System.out.println(context.getBean(Service.class));
////        HashMap
//    }
//    public synchronized static void main(String[] args) {
//        ExecutorService executor1 = Executors.newCachedThreadPool();
//        ExecutorService executor3 = Executors.newFixedThreadPool(10);
//        ExecutorService executor2 = Executors.newSingleThreadExecutor();
//
////        Thread t = new MyThread();//向上转型
//        Thread t = new Thread(()->{
//            while(true){
//                System.out.println("hello thread");
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        t.start();
//        t.wait();
//        t.join();
////        ReentrantLock
//
//    }
//    public static void main(String[] args) {
//        ExecutorService executorService1 = Executors.newCachedThreadPool();//快
//        ExecutorService executorService2 = Executors.newFixedThreadPool(10);//慢
//        ExecutorService executorService3 = Executors.newSingleThreadExecutor();//最慢
//
//
//        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 20,
//                0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(10));//自定义线程
//
//        for (int i = 1; i <= 100; i++) {
//            threadPoolExecutor.execute(new MyTask(i));
//
//        }
//    }
//    public static void main(String[] args) {
//
//        Object object=new Object();
//        Thread thread1=new Thread(()->{
//            synchronized (object){
//                try {
//                    object.wait();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//            System.out.println("线程1执行完成");
//        });
//
//        Thread thread2=new Thread(()->{
//            synchronized (object){
//                try {
//                    object.wait();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//            System.out.println("线程2执行完成");
//        });
//
//        Thread thread3=new Thread(()->{
//            synchronized (object){
//                try {
//                    object.wait();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//            System.out.println("线程3执行完成");
//        });
//        thread2.start();
//
//        thread1.start();
//        thread3.start();
//
//        Thread thread4=new Thread(()->{
//            synchronized (object){
//                //使用一次notify只能唤醒一个线程；
//                //使用一次notifyAll可唤醒所有的线程
//                object.notify();
////                object.notifyAll();
//                System.out.println("线程4去唤醒wait的线程");
//            }
//
//        });
//        thread4.start();
        public static void main(String[] args) {
            Entity entity = new Entity();

            Unsafe unsafe = UnsafeFactory.getUnsafe();

            long offset = UnsafeFactory.getFieldOffset(unsafe, Entity.class, "x");
            System.out.println(offset);
            boolean successful;

            // 4个参数分别是：对象实例、字段的内存偏移量、字段期望值、字段更新值
            successful = unsafe.compareAndSwapInt(entity, offset, 0, 3);
            System.out.println(successful + "\t" + entity.x);

            successful = unsafe.compareAndSwapInt(entity, offset, 3, 5);
            System.out.println(successful + "\t" + entity.x);

            successful = unsafe.compareAndSwapInt(entity, offset, 3, 8);
            System.out.println(successful + "\t" + entity.x);

        }


}



class Entity{
    int x;
}
class UnsafeFactory {

    /**
     * 获取 Unsafe 对象
     * @return
     */
    public static Unsafe getUnsafe() {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            return (Unsafe) field.get(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取字段的内存偏移量
     * @param unsafe
     * @param clazz
     * @param fieldName
     * @return
     */
    public static long getFieldOffset(Unsafe unsafe, Class clazz, String fieldName) {
        try {
            return unsafe.objectFieldOffset(clazz.getDeclaredField(fieldName));
        } catch (NoSuchFieldException e) {
            throw new Error(e);
        }
    }


}
class MyTask implements Runnable {
    int i = 0;

    public MyTask(int i) {
        this.i = i;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "程序员做第" + i + "个项目");
        try {
            Thread.sleep(3000L);//业务逻辑
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


