package com.edo.util.ApplicationContext;

import lombok.experimental.UtilityClass;
import org.springframework.context.ApplicationContext;

@UtilityClass
public class BeanUtils {
    public static <T> T getBean(Class<T> classType){
        ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();
        return applicationContext.getBean(classType);
    }
}
