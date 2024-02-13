package org.green.web.spring;

import javax.print.DocFlavor;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Green写代码
 * @date 2024-02-13 14:22
 */
public class GreenApplicationContext {
    private Class configClass;
    private Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();
    private Map<String, Object> singletonMap     = new HashMap<>();
    public GreenApplicationContext(Class configClass) {
        this.configClass = configClass;
        //扫描--->得到class---->获取beanDefinitionMap
        scan(configClass);

        for(Map.Entry<String ,BeanDefinition>entry : beanDefinitionMap.entrySet()){
            String name = entry.getKey();
            BeanDefinition beanDefinition = entry.getValue();
            if(beanDefinition.getScope().equals("singleton")){
                Object bean = createBean(name, beanDefinition);
                singletonMap.put(name, bean);
            }
        }
    }

    private void scan(Class configClass){
        if(configClass.isAnnotationPresent(ComponentScan.class)){
            ComponentScan componentScan = (ComponentScan) configClass.getAnnotation(ComponentScan.class);
            String path = componentScan.value();
            path = path.replace(".","/");
//            System.out.println(path);
            ClassLoader classLoader = GreenApplicationContext.class.getClassLoader();
            URL resource = classLoader.getResource(path);
            File file = new File(resource.getFile());
            if(file.isDirectory()){
                for(File f : file.listFiles()){
                    String absolutePath = f.getAbsolutePath();
                    System.out.println(absolutePath);
                    //Spring使用的是ASM技术
                    //但是不管怎么说我们还是将其类信息加载出来
                    absolutePath = absolutePath.substring(absolutePath.indexOf("org"),absolutePath.indexOf(".class"));
                    absolutePath = absolutePath.replace("\\",".");
                    try {
                        Class<?> clazz = classLoader.loadClass(absolutePath);
                        if(clazz.isAnnotationPresent(Component.class)){
                            System.out.println(clazz);
                            Component componentAnnotation = clazz.getAnnotation(Component.class);
                            String beanName = componentAnnotation.value();
                            BeanDefinition beanDefinition = new BeanDefinition();
                            beanDefinition.setType(clazz);
                            //要知道其是单例还是原型-->通过scope
                            if(clazz.isAnnotationPresent(Scope.class)){
                                Scope scopeAnnotation = clazz.getAnnotation(Scope.class);
                                String value = scopeAnnotation.value();
                                beanDefinition.setScope(value);
                            }else{
                                beanDefinition.setScope("singleton");
                            }
                            beanDefinitionMap.put(beanName, beanDefinition);
                        }
                    }catch (ClassNotFoundException e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public Object getBean(String name){
        if(!beanDefinitionMap.containsKey(name)){
            throw new NullPointerException();
        }
        BeanDefinition beanDefinition = beanDefinitionMap.get(name);
        if(beanDefinition.getScope().equals("singleton")){
            Object singletonBean = singletonMap.get(name);
            return singletonBean;
        }else{
            //原型
            Object bean = createBean(name, beanDefinition);
            return bean;
        }
    }
    private Object createBean(String beanName, BeanDefinition beanDefinition){
        Class clazz = beanDefinition.getType();
        Object instance = null;
        try {
            instance = clazz.getConstructor().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return instance;
    }

}
