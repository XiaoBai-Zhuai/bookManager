package com.gydx.bookManager.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Servlet;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DruidConfig {

    private static Logger log = LoggerFactory.getLogger(DruidConfig.class);

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druid() {
        return new DruidDataSource();
    }

    @Bean
    public ServletRegistrationBean<Servlet> druidServlet() {
        log.info("init Druid Servlet Configuration");
        ServletRegistrationBean<Servlet> servletRegistrationBean = new ServletRegistrationBean<>();
        //配置一个拦截器
        servletRegistrationBean.setServlet(new StatViewServlet());
        //指定拦截器只拦截druid管理页面的请求
        servletRegistrationBean.addUrlMappings("/druid/*");
        Map<String, String> initParam = new HashMap<>();
        //是否允许重置druid的统计信息
        initParam.put("resetEnable", "false");
        initParam.put("loginUsername", "admin");
        initParam.put("loginPassword", "123456");
        servletRegistrationBean.setInitParameters(initParam);
        return servletRegistrationBean;
    }

}
