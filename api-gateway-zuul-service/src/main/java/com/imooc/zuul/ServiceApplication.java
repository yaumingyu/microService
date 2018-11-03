package com.imooc.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * Created by Administrator on 2018/10/1 0001.
 */
@SpringBootApplication
@EnableZuulProxy
public class ServiceApplication {

    public static void main(String[] args){
        SpringApplication.run(ServiceApplication.class, args);
    }
}
