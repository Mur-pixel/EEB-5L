package com.example.monoproj.config;
import com.example.monoproj.servlet_lab.controller.HelloServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//spring 저장 관리
@Configuration
public class ServletRegisterConfig {

    // spring에서 관리하는 싱글톤 객체/ servlet에 helloServlet(싱글톤)을 등록
    @Bean
    public ServletRegistrationBean<HelloServlet> helloServlet() {
        ServletRegistrationBean<HelloServlet> bean =
                new ServletRegistrationBean<>(new HelloServlet(), "/hello-servlet");
        bean.setLoadOnStartup(1); //spring이 구동될때 HelloServlet 구성
        return bean;
    }
}