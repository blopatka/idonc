package org.lopatka.idonc.service.utils;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.springframework.context.ApplicationContextException;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;

public class AnnotationSessionFactoryBean extends LocalSessionFactoryBean {

//    private static final Logger LOG = Logger.getLogger(AnnotationSessionFactoryBean.class);

    private List<String> annotatedClasses_;

    /**
     * @return the classes.
     */
    public List<String> getAnnotatedClasses() {
        return annotatedClasses_;
    }

    /**
     * @param classes The classes to set.
     */
    public void setAnnotatedClasses(List<String> classes) {
        annotatedClasses_ = classes;
    }

    @Override
    protected void postProcessConfiguration(Configuration config) throws HibernateException {
        super.postProcessConfiguration(config);

        if (!(config instanceof AnnotationConfiguration)) {
            throw new ApplicationContextException("The configuration must be AnnotationConfiguration.");
        }

        if (annotatedClasses_ == null) {
//            LOG.info("No annotated classes to register with Hibernate.");
            return;
        }

        for (String className : annotatedClasses_) {
            try {
                Class<?> clazz = config.getClass().getClassLoader().loadClass(className);
                ((AnnotationConfiguration)config).addAnnotatedClass(clazz);

//                if (LOG.isDebugEnabled()) {
//                    LOG.debug("Class " + className + " added to Hibernate config.");
//                }
            }
            catch (MappingException e) {
                throw new ApplicationContextException("Unable to register class " + className, e);
            }
            catch (ClassNotFoundException e) {
                throw new ApplicationContextException("Unable to register class " + className, e);
            }
        }
    }
}
