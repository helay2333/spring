package org.green.web.spring;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;

import javax.print.DocFlavor;
import java.beans.Introspector;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Green写代码
 * @date 2024-02-13 14:22
 */
public class GreenApplicationContext {
    private Class configClass;
    private Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();
    private Map<String, Object> singletonMap     = new HashMap<>();
    private List<BeanPostProcessor> postProcessorList = new ArrayList<>();
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
                            if(BeanPostProcessor.class.isAssignableFrom(clazz)){
                                //实现了初始化后接口
                                BeanPostProcessor instance = (BeanPostProcessor) clazz.getConstructor().newInstance();
                                postProcessorList.add(instance);
                            }
                            Component componentAnnotation = clazz.getAnnotation(Component.class);
                            String beanName = componentAnnotation.value();
                            if("".equals(beanName)){
                                //生成默认名字
                                beanName = Introspector.decapitalize(clazz.getName());
                                String[] arr = beanName.split("\\.");
                                beanName = arr[arr.length - 1];
                            }
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
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
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
            if(singletonBean == null){
                singletonBean = createBean(name, beanDefinition);
                singletonMap.put(name, beanDefinition);

            }
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
            for(Field field : clazz.getDeclaredFields()){
                if(field.isAnnotationPresent(Autowired.class)){
                    field.setAccessible(true);
                    field.set(instance, getBean(field.getName()));
                }
            }

            //初始化后调用beanPostProcessor
            if(instance instanceof InitializingBean){
                ((InitializingBean) instance).afterPropertiesSet();
            }
            for(BeanPostProcessor beanPostProcessor : postProcessorList){
                beanPostProcessor.postProcessAfterInitialization(instance, beanName);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return instance;
    }

}
