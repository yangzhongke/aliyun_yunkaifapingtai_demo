/*
 * 
 * Copyright (C) 2010-2013 Alibaba Group Holding Limited
 * 
 */

package com.alibaba.sca.temp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

/**
 * @author <a href="mailto:chenxilzx1@gmail.com">theonefx</a>
 */
@SpringBootApplication
@PropertySource( value = "classpath:application.properties", encoding ="UTF-8" )
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
