package com.gydx.bookManager;

import com.gydx.bookManager.filter.HttpServletRequestReplacedFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import tk.mybatis.spring.annotation.MapperScan;

import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

@MapperScan("com.gydx.bookManager.mapper")
@SpringBootApplication
@EnableAsync
public class BookManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookManagerApplication.class, args);
    }

    @Bean
    public CookieSerializer httpSessionIdResolver(){
        DefaultCookieSerializer cookieSerializer = new DefaultCookieSerializer();                            cookieSerializer.setCookieName("token");
        cookieSerializer.setUseHttpOnlyCookie(false);
        cookieSerializer.setUseSecureCookie(true);
        return cookieSerializer;

    }
}
