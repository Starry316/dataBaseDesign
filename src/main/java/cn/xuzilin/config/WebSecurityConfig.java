package cn.xuzilin.config;

import cn.xuzilin.interceptor.Interceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Configuration
public class WebSecurityConfig extends WebMvcConfigurerAdapter {


    @Bean
    public Interceptor getInterceptor() {
        return new Interceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration addInterceptor = registry.addInterceptor(getInterceptor());
        // 拦截配置
        addInterceptor.addPathPatterns("/manage/main");
        addInterceptor.addPathPatterns("/manage/reserve");
    }
}