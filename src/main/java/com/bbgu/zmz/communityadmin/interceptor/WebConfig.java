package com.bbgu.zmz.communityadmin.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Properties;


@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private SessionInterceptor sessionInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(sessionInterceptor).addPathPatterns("/**")
                .excludePathPatterns(
                        "/static/**",
                        "/",
                        "/ad/test",
                        "/dologin",
                        "/logout",
                        "/api/check"
                        );
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
                Properties props=System.getProperties(); //获得系统属性集
                String osName = props.getProperty("os.name"); //操作系统名称
                if(osName.indexOf("Win") != -1){
                    registry.addResourceHandler("/upload/**").addResourceLocations("file:D://upload/");
                    registry.addResourceHandler("/ad/**").addResourceLocations("file:D://ad/");
                }else{
                    registry.addResourceHandler("/upload/**").addResourceLocations("file:/data/wwwroot/upload/");
                    registry.addResourceHandler("/ad/**").addResourceLocations("file:/data/wwwroot/ad/");
                }

          }

/*    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
    }*/


}
