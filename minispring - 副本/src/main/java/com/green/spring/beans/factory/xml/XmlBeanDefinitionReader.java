package com.green.spring.beans.factory.xml;

/**
 * @author Green写代码
 * @date 2024-03-03 17:40
 */

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import com.green.spring.beans.factory.config.BeanDefinition;
import com.green.spring.beans.factory.config.BeanReference;
import com.green.spring.beans.factory.config.PropertyValue;
import com.green.spring.beans.factory.exception.BeanException;

import com.green.spring.beans.factory.support.AbstractBeanDefinitionReader;
import com.green.spring.beans.factory.support.BeanDefinitionRegistry;
import com.green.spring.core.io.Resource;
import com.green.spring.core.io.ResourceLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;


public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {


    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        super(registry);
    }

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
        super(registry, resourceLoader);
    }

    @Override
    public void loadBeanDefinitions(Resource resource) throws BeanException {
        try (InputStream inputStream = resource.getInputStream()) {
            doLoadBeanDefinitions(inputStream);
        } catch (IOException | ClassNotFoundException e) {
            throw new BeanException("读取bean定义时出错：", e);
        }
    }

    @Override
    public void loadBeanDefinitions(Resource... resources) throws BeanException {
        for (Resource r : resources) {
            loadBeanDefinitions(r);
        }
    }

    @Override
    public void loadBeanDefinitions(String location) throws BeanException {
        ResourceLoader resourceLoader = getResourceLoader();
        Resource resource = resourceLoader.getResource(location);
        loadBeanDefinitions(resource);
    }
    @Override
    public void loadBeanDefinitions(String... location) throws BeanException {
        for (String s : location) {
            loadBeanDefinitions(s);
        }
    }

    /**
     * 真正通过xml读取bean定义的方法实现
     *
     * @param inputStream xml配置文件输入流
     * @throws BeanException          e
     * @throws ClassNotFoundException e
     */
    private void doLoadBeanDefinitions(InputStream inputStream) throws BeanException, ClassNotFoundException {
        Document doc = XmlUtil.readXML(inputStream);
        Element root = doc.getDocumentElement();
        NodeList childNodes = root.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            //如果不是bean，则跳过
            if (!isBean(childNodes.item(i))) {
                continue;
            }
            // 解析标签
            Element bean = (Element) childNodes.item(i);
            String id = bean.getAttribute("id");
            String name = bean.getAttribute("name");
            String className = bean.getAttribute("class");
            String initMethodName = bean.getAttribute("init-method");
            String destroyMethodName = bean.getAttribute("destroy-method");
            String scope = bean.getAttribute("scope");
            // 获取 Class，方便获取类中的名称
            Class<?> clazz = Class.forName(className);
            // 优先级 id > name
            String beanName = StrUtil.isNotEmpty(id) ? id : name;
            if (StrUtil.isEmpty(beanName)) {
                beanName = StrUtil.lowerFirst(clazz.getSimpleName());
            }
            // 定义Bean
            BeanDefinition beanDefinition = new BeanDefinition(clazz);
            beanDefinition.setInitMethodName(initMethodName);
            beanDefinition.setDestroyMethodName(destroyMethodName);
            // 读取属性并填充
            buildProperty(bean, beanDefinition);
            if (getRegistry().containsBeanDefinition(beanName)) {
                throw new BeanException("Duplicate beanName[" + beanName + "] is not allowed");
            }
            if (StrUtil.isNotBlank(scope)) {
                beanDefinition.setScope(scope);
            }
            // 注册 BeanDefinition
            getRegistry().registerBeanDefinition(beanName, beanDefinition);
        }
    }


    /**
     * 填充beanDefinition的属性
     *
     * @param bean           配置文件中的bean信息
     * @param beanDefinition 等待填充属性的bean定义
     */
    private void buildProperty(Element bean, BeanDefinition beanDefinition) {
        // 读取属性并填充
        for (int j = 0; j < bean.getChildNodes().getLength(); j++) {
            //如果不是属性，则跳过
            if (!isProperty(bean.getChildNodes().item(j))) {
                continue;
            }
            // 解析标签：property
            Element property = (Element) bean.getChildNodes().item(j);
            String attrName = property.getAttribute("name");
            String attrValue = property.getAttribute("value");
            String attrRef = property.getAttribute("ref");
            // 获取属性值：引入对象、值对象
            Object value = StrUtil.isNotEmpty(attrRef) ? new BeanReference(attrRef) : attrValue;
            // 创建属性信息
            PropertyValue propertyValue = new PropertyValue(attrName, value);
            beanDefinition.getPropertyValues().addPropertyValue(propertyValue);
        }
    }

    /**
     * 判断一个节点是不是bean
     *
     * @param node 待判断节点
     * @return result
     */
    private boolean isBean(Node node) {
        if (!(node instanceof Element)) {
            return false;
        }
        return "bean".equals(node.getNodeName());
    }

    /**
     * 判断一个节点是不是bean的属性
     *
     * @param node 待判断节点
     * @return result
     */
    private boolean isProperty(Node node) {
        if (!(node instanceof Element)) {
            return false;
        }
        return "property".equals(node.getNodeName());
    }
}


