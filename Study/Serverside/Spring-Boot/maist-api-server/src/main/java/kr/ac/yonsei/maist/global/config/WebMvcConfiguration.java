/**
 * Class to configure Spring related configuration.
 * @author Mina Kim, Yonsei Univ. Researcher, since 2020.08~
 * @Date 2021.01.06
 */
package kr.ac.yonsei.maist.global.config;

import kr.ac.yonsei.maist.global.config.interceptor.GuestHandlerInterceptor;
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

    private static final String[] TOKEN_CHECK_PATHS = {
            "/maist/system",     // System Code 조회, 생성
            "/maist/system/*",   // System Code 수정, 삭제
            "/maist/language",   // Language Code 조회, 생성
            "/maist/language/*", // Language Code 수정, 삭제
            "/maist/command",   // Command Data 조회, 생성
            "/maist/command/*", // Command Data 수정, 삭제
    };

    private static final String[] GUEST_ADD_PATHS = {
            "/maist/users/password"// 프로필 수정시 비밀번호 확인
            , "/maist/users/password/*" // 비밀번호 수정
            , "/maist/connection" // 머신-사용자 연결관계 생성
            , "/maist/connection/*" // 머신-사용자 연결관계 삭제
            , "/maist/reservations" // 예약 생성
            , "/maist/reservations/*" // 예약 수정, 예약 삭제

    };

    @Autowired
    private GuestHandlerInterceptor guestHandlerInterceptor;
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

//        registry.addInterceptor(guestHandlerInterceptor) // 게스트인지 확인
//                .addPathPatterns(GUEST_ADD_PATHS);

//        registry.addInterceptor(menuHandlerInterceptor) // 메뉴 유효성 확인
//                .addPathPatterns("/**")
//                .excludePathPatterns(TOKEN_EXCLUDE_PATHS);

        registry.addInterceptor(tokenHandlerInterceptor) // 토큰 유효성 확인
                .addPathPatterns(TOKEN_CHECK_PATHS);
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
