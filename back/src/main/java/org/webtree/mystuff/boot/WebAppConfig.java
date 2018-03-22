package org.webtree.mystuff.boot;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;


/**
 * Created by Udjin on 21.03.2018.
 */

@Configuration
public  class WebAppConfig {
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource resource = new
            ResourceBundleMessageSource();
        resource.setBasename("messages");
        resource.setDefaultEncoding("UTF-8");
        return resource;
    }

}
