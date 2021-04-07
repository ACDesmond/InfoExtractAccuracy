package com.nju.infoextract.config;

import com.nju.infoextract.controller.tmp.Token;
import org.omg.PortableInterceptor.Interceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author: songqiang
 * @since: 2020/11/20
 */
@Configuration
public class MyConfiguration implements WebMvcConfigurer {

    @Bean
    public TokenInterceptor getTokenInterceptor() {
        return new TokenInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getTokenInterceptor())
                .excludePathPatterns("/infoextract/user/login")
                .addPathPatterns("/**");
    }
}
