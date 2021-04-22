package com.zhaojufei.practice;


import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@EnableApolloConfig
@SpringBootApplication
public class PracticeApplication {

    public static ApplicationContext context;

    private static Logger logger = LoggerFactory.getLogger(PracticeApplication.class);

    public static void main(String[] args) {
        context = SpringApplication.run(PracticeApplication.class, args);
        logger.info("--------启动完成--------");
    }
}

