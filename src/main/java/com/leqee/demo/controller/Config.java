package com.leqee.demo.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
public class Config {

    @Bean(name = "multipartResolver1")
    public Object multiPart(){
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        return resolver;
    }


}
