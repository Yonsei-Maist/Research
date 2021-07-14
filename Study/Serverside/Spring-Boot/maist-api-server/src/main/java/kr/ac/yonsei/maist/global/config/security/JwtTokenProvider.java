/**
 * Class that provides everything related to the token.
 * @author Mina Kim, Yonsei Univ. Researcher, since 2020.08~
 * @Date 2021.01.07
 */
package kr.ac.yonsei.maist.global.config.security;

import com.google.gson.Gson;
import io.jsonwebtoken.*;
import kr.ac.yonsei.maist.global.error.exception.InvalidTokenException;
import kr.ac.yonsei.maist.global.error.exception.UnauthorizedException;
import kr.ac.yonsei.maist.domain.user.dto.UserInformationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${spring.jwt.secret}")
    private String secretKey;
    private long tokenValidMilisecond = 1000L*60*60*24; // 1day

    @PostConstruct
    protected  void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    /**
     * Create a token.
     * Issue a token with data, issue date, and expiration time.
     * @param userInformationDto User Information
     * @param language system code
     * @return String Token
     */
/*    public String createToken(UserInformationDto userInformationDto, int language, boolean unlimit) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(userInformationDto);
        if (!unlimit)
            return createToken(jsonString, language);
        else
            return createGuestToken(jsonString, language);
    }*/

    /**
     * Create a token.
     * Issue a token with data, issue date, and expiration time.
     * @param userName user name
     * @return String Token
     */
    public String createToken(String userName) {
        Date issueDate = new Date();
        return Jwts.builder()
                .setAudience(userName) // 토큰 대상자
                .setIssuedAt(issueDate) // 발행 날짜
                .setExpiration(new Date(issueDate.getTime() + tokenValidMilisecond)) // 만료 시간
                .signWith(SignatureAlgorithm.HS256, secretKey) // 암호화 알고리즘, secret 값 세팅
                .compact();
    }

    /**
     * Create a guest token.
     * Issue a token with data, issue date.
     * @param jsonInfo guest id
     * @param language system code
     * @return String Token
     */
    public String createGuestToken(String jsonInfo, int language) {
        Date issueDate = new Date();
        return Jwts.builder()
                .claim("language", language) // 데이터-언어
                .setAudience(jsonInfo) // 토큰 대상자
                .setIssuedAt(issueDate) // 발행 날짜
                .signWith(SignatureAlgorithm.HS256, secretKey) // 암호화 알고리즘, secret 값 세팅
                .compact();
    }

    /**
     * Reissue token.
     * @param token Token
     * @return String Reissue Token
     */
    public String reissueToken(String token) {
        try {
            String userId = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getAudience();
            return createToken(userId);
        } catch (ExpiredJwtException e) {
            String userId = e.getClaims().getAudience(); // 기간 만료 토큰 예외
            return createToken(userId);
        } catch (UnsupportedJwtException | MalformedJwtException |SignatureException e) {
            throw new UnauthorizedException(); // 토큰 형식, 서명 예외
        }
    }

    /**
     * Extract information from the token.
     * @param token Token
     * @return String Token information
     */
    public UserInformationDto getUser(String token) {
        try {
        String json = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getAudience();
            Gson gson = new Gson();
            return gson.fromJson(json, UserInformationDto.class);
        } catch (IllegalArgumentException e) {
            throw new InvalidTokenException(token); // 비어있는 토큰값 예외
        } catch (UnsupportedJwtException | MalformedJwtException |SignatureException e) {
            throw new UnauthorizedException(); // 토큰 형식, 서명 예외
        }
    }

    /**
     * Gets the header value named 'token' from http servlet request.
     * @param request HttpServletRequest
     * @return String Token
     */
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("token");
    }

    /**
     * Check the validity of the token.
     * @param jwtToken Token
     * @return boolean Whether the token is valid or not
     * @throws UnauthorizedException
     */
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
        } catch (IllegalArgumentException e) {
            throw new InvalidTokenException(jwtToken); // 비어있는 토큰값 예외
        } catch (UnsupportedJwtException | MalformedJwtException |SignatureException e) {
            throw new UnauthorizedException(); // 토큰 형식, 서명 예외
        }
        return true;
    }

    /**
     * Returns the token expiration period.
     * @param jwtToken Token
     * @return Date Expiration Date
     */
    public Date expirationDate(String jwtToken) {
        Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
        return claims.getBody().getExpiration();
    }
}
