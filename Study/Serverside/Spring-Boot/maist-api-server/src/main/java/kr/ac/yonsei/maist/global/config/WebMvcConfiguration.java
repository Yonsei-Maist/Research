/**
 * Class to configure Spring related configuration.
 * @author Mina Kim, Yonsei Univ. Researcher, since 2020.08~
 * @Date 2021.01.06
 */
package kr.ac.yonsei.maist.global.config;

import kr.ac.yonsei.maist.global.config.interceptor.MenuHandlerInterceptor;
import kr.ac.yonsei.maist.global.config.interceptor.TokenHandlerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.TimeUnit;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    private static final String[] TOKEN_EXCLUDE_PATHS = {
            "/maist/login" // Log in
            , "/maist/users" // create user
    };

    @Autowired
    private MenuHandlerInterceptor menuHandlerInterceptor;
    @Autowired
    private TokenHandlerInterceptor tokenHandlerInterceptor;

    /**
     * Add an encryptor that intercepts the request before it reaches the controller.
     * Add the URL that the interceptor should apply to and the URL that should be excluded.
     * @param registry InterceptorRegistry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(menuHandlerInterceptor) // 메뉴 유효성 확인
                .addPathPatterns("/**")
                .excludePathPatterns(TOKEN_EXCLUDE_PATHS);
    }

    /**
     * Add resource.
     * @param registry ResourceHandlerRegistry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/templates/")
                .setCacheControl(CacheControl.maxAge(60, TimeUnit.MINUTES));
    }
}
