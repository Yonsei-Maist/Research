/**
 * Class that implements HandlerInterceptor interface.
 * This contains the logic to be processed before the controller.
 * @author Mina Kim, Yonsei Univ. Researcher, since 2020.08~
 * @Date 2021.01.07
 */
package kr.ac.yonsei.maist.global.config.interceptor;

import kr.ac.yonsei.maist.global.config.security.JwtTokenProvider;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TokenHandlerInterceptor implements HandlerInterceptor {

    private JwtTokenProvider jwtTokenProvider;

    public TokenHandlerInterceptor(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     * If the user's token is invalid, access is restricted.
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param handler Object
     * @return boolean
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = jwtTokenProvider.resolveToken(request);
        return jwtTokenProvider.validateToken(token);
    }
}
