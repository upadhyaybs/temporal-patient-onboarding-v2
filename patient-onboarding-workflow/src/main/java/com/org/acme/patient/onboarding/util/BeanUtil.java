package com.org.acme.patient.onboarding.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

@Service
public class BeanUtil implements ApplicationContextAware {

        private static ApplicationContext context;

        /**
         *Use this method to manually autowire Spring Beans into classes that are not managed by Spring.
         *
         *@param beanClass - Class type of the required bean.
         **/
        public static <T> T getBean(Class<T> beanClass) {
            return context.getBean(beanClass);
        }

        @Override
        public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            context = applicationContext;
        }

}



