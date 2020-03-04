package com.bbgu.zmz.communityadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@ServletComponentScan
@MapperScan("com.bbgu.zmz.communityadmin.mapper")
public class CommunityadminApplication  extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(CommunityadminApplication.class, args);
    }
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(CommunityadminApplication.class);
    }

}
