/**
 * Class that implements HandlerInterceptor interface.
 * This contains the logic to be processed before the controller.
 *
 * @author Chanwoo Gwon, Yonsei Univ. Researcher, since 2020.05~
 * @Date 2021.02.02
 */
package kr.ac.yonsei.maist.global.config.interceptor;

import kr.ac.yonsei.maist.global.config.security.JwtTokenProvider;
import kr.ac.yonsei.maist.global.error.exception.UnauthorizedException;
import kr.ac.yonsei.maist.domain.menu.dto.MenuDto;
import kr.ac.yonsei.maist.domain.menu.dto.Method;
import kr.ac.yonsei.maist.domain.user.dto.UserInformationDto;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class MenuHandlerInterceptor implements HandlerInterceptor {

    private JwtTokenProvider jwtTokenProvider;

    public MenuHandlerInterceptor(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    private boolean checkAuthorization(String menuPathItem, String urlPathItem, UserInformationDto userInfo) {

        // just now for test (temp code, 2021.02.04, Chanwoo Gwon)
        // TODO: Change to more flexible.
        if (menuPathItem.equals("{userId}") && !urlPathItem.equals(userInfo.getUserId())) {
            return false;
        }

        return true;
    }

    private boolean checkUrl(String method, String pathUrl, UserInformationDto userInfo) {
        class PairString{
            private String menu;
            private String url;

            public PairString(String menu, String url) {
                this.menu = menu;
                this.url = url;
            }
        }

        String regex = "/+";
        Pattern pattern = Pattern.compile("[0-9]");
        String[] paths = pathUrl.split(regex);
        for (MenuDto menu : userInfo.getMenuList()) {
            Matcher mat = pattern.matcher(menu.getId());
            boolean res = mat.find();

            if (!res)
                return false;

            int menuMethodId = Integer.parseInt(mat.group());
            String menuPath = menu.getPath();
            String[] menuPaths = menuPath.split(regex);

            Method methodKind = Method.valueOf(method);

            if (paths.length != menuPaths.length || methodKind.val != menuMethodId)
                continue;

            int matchCount = 0;
            List<PairString> pathVariableList = new ArrayList<PairString>();

            for (int i = paths.length - 1; i > -1; i--) {
                String pathOfMenu = menuPaths[i];
                String pathOfUrl = paths[i];

                if (pathOfMenu.contains("{") && pathOfMenu.contains("}")) {
                    pathVariableList.add(new PairString(pathOfMenu, pathOfUrl));

                    matchCount++;
                } else if (pathOfMenu.equals(pathOfUrl)) {
                    matchCount++;
                }
            }

            if (matchCount == paths.length) {
                for (PairString pair : pathVariableList) {
                    if (!this.checkAuthorization(pair.menu, pair.url, userInfo))
                        return false;
                }

                return true;
            }
        }

        return false;
    }

    /**
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param handler Object
     * @return boolean
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = jwtTokenProvider.resolveToken(request);
        UserInformationDto userDto = jwtTokenProvider.getUser(token);

        boolean res = this.checkUrl(request.getMethod(), request.getRequestURI(), userDto);

        if (!res) {
            throw new UnauthorizedException();
        }

        return true;
    }
}
