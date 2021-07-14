/**
 * Class that implements HandlerInterceptor interface.
 * This contains the logic to be processed before the controller.
 * @author Mina Kim, Yonsei Univ. Researcher, since 2020.08~
 * @Date 2021.01.28
 */
package kr.ac.yonsei.maist.global.config.interceptor;

import kr.ac.yonsei.maist.global.config.security.JwtTokenProvider;
import kr.ac.yonsei.maist.global.error.exception.UnauthorizedException;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class GuestHandlerInterceptor implements HandlerInterceptor {

    private JwtTokenProvider jwtTokenProvider;
    private static final String GUEST = "Guest"; // 게스트 아이디

    public GuestHandlerInterceptor(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     * If the user is a guest, access is restricted.
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param handler Object
     * @return boolean
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Headers, token");

        String token = jwtTokenProvider.resolveToken(request);
        if(jwtTokenProvider.getUser(token).equals(GUEST) && !HttpMethod.GET.matches(request.getMethod())){ // Guest 특정 URI 접근 제한
            throw new UnauthorizedException();
        }
        return true;
    }
}
