package testInterface.context;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.*;
import utils.TestEngineDataHolder;

import java.io.File;

/**
 * Class stores spring context
 */
public class ContextHolder {

    private static final ContextHolder instance = new ContextHolder();

    //String bean factory
    private BeanFactory beanFactory;

    public ContextHolder() {
        try{
            String packageDir = TestEngineDataHolder.getData().getWorkspacePath();
            String path = String.format("%s%s%s", packageDir, File.separator, "connector-context.xml");

            /*beanFactory = new XmlBeanFactory(new ClassPathResource("/connector-context.xml", ContextHolder.class));*/
            System.out.println(String.format("Context file: [%s]", path));
            beanFactory = new XmlBeanFactory(new FileSystemResource(path));
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * Get singleton
     *
     * @return ContextHolder
     */
    public static ContextHolder getInstance() {
        return instance;
    }

    /**
     * Get bean for name from bean factory
     *
     * @param name bean name
     * @return bean instance
     * @throws org.springframework.beans.BeansException if build bin failed
     */
    public Object getBean(String name) throws BeansException {
        return beanFactory.getBean(name);
    }
}
