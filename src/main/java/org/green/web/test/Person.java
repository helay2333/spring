package org.green.web.test;

/**
 * @author Green写代码
 * @date 2024-02-15 15:05
 */

public class Person {
    /**
     * @Field: listener
     *          在Person类中定义一个PersonListener变量来记住传递进来的监听器
     */
    private PersonListener listener;


    public void eat() {
        if (listener != null) {
            /**
             * 调用监听器的doeat方法监听Person类对象eat(吃)这个动作，将事件对象Event传递给doeat方法，
             * 事件对象封装了事件源，new Event(this)中的this代表的就是事件源
             */
            listener.doeat(new Event(this));
        }
    }


    public void run() {
        if (listener != null) {
            /**
             * 调用监听器的dorun方法监听Person类对象run(跑)这个动作，将事件对象Event传递给doeat方法，
             * 事件对象封装了事件源，new Event(this)中的this代表的就是事件源
             */
            listener.dorun(new Event(this));
        }
    }


    public void registerListener(PersonListener listener) {
        this.listener = listener;
    }
}


interface PersonListener {


    void doeat(Event e);


    void dorun(Event e);

}


class Event {

    /**
     * @Field: source
     *          事件源(Person就是事件源)
     */
    private Person source;

    public Event() {

    }

    public Event(Person source) {
        this.source = source;
    }

    public Person getSource() {
        return source;
    }

    public void setSource(Person source) {
        this.source = source;
    }
}
