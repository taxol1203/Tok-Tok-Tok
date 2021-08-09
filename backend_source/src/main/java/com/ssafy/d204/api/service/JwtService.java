package com.ssafy.d204.api.service;

import com.ssafy.d204.db.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class JwtService {

  private final String signature = "SUPERSECRET";
  // TODO : 시그니쳐 백엔드에서 시큐어한 공간에 격리하는 방법
  private final Long expireMin = 5L;
  // 토큰 만료

  public String create(User user) {
    JwtBuilder jwtBuilder = Jwts.builder();
    //set header
    jwtBuilder.setHeaderParam("typ", "JWT"); // 토큰의 타입으로 고정 값.
    //set payload
    jwtBuilder.setSubject("로그인토큰") // 토큰의 제목 설정
        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * expireMin)) // 유효기간 설정
        .claim("user", user)
        .claim("greeting", "환영합니다. " + user.getEmail()); // 담고 싶은 정보 설정.

    //set signature
    jwtBuilder.signWith(SignatureAlgorithm.HS256, signature.getBytes());
    //serialize
    String jwt = jwtBuilder.compact();
    return jwt;
  }

  public void jwtValidation(String jwt) {
    Jwts.parser().setSigningKey(signature.getBytes()).parseClaimsJws(jwt);
    // 예외 발생 시 -> 전달받은 토큰에 문제가 있음
  }

  public Map<String, Object> getInfos(String jwt) {
    Jws<Claims> claims = null;
    try {
      claims = Jwts.parser().setSigningKey(signature.getBytes()).parseClaimsJws(jwt);
    } catch (final Exception e) {
      throw new RuntimeException();
    }
    // Claim implements Map.
    return claims.getBody();
  }

}
