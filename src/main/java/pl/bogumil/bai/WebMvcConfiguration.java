package pl.bogumil.bai;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import pl.bogumil.bai.interceptor.LoggedUserInterceptor;

/**
 * Created by bbierc on 2016-03-31.
 */
@Configuration
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {

    @Bean
    public LoggedUserInterceptor loggedUserInterceptor() {
        return new LoggedUserInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggedUserInterceptor()).addPathPatterns("/**");
    }

}
