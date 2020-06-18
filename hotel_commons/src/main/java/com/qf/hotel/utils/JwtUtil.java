package com.qf.hotel.utils;

import com.qf.hotel.pojo.User;
import io.jsonwebtoken.*;

import java.util.Date;

/**
 * @author ChenJie
 * @date 2020-05-28 23:26:16
 * 功能说明
 */
public class JwtUtil {
    // jwt的加密密钥
    private static final String SECRET_KEY = "hufsvkesnviafrieus-dfauba-fbajhbfds";

    // 令牌过期时间
    private static final Long TTL = 7*24*60*60*1000L;

    public static String createJwtToken(User user){
        JwtBuilder jwtBuilder = Jwts.builder()
                .setId(user.getId().toString())// id
                .setSubject(user.getUsername())// 签发者
                .setIssuedAt(new Date())// 签发时间
                .setExpiration(new Date(new Date().getTime() + TTL))//过期时间
                // 存放不敏感信息  形成签名
                .claim("id", user.getId())
                .claim("username", user.getUsername())
                .claim("nickname", user.getNickname())
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY);

        return jwtBuilder.compact();
    }

    public static User paseJwtToken(String token){
        User user = null;
        try {
            Claims body = (Claims) Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
            Integer id = (Integer) body.get("id");
            String username = (String) body.get("username");
            String nickname = (String) body.get("nickname");
            System.out.println(id + "===" + username + "===" + nickname);
            user = new User()
                .setId(id)
                .setUsername(username)
                .setNickname(nickname);
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
        } catch (UnsupportedJwtException e) {
            e.printStackTrace();
        } catch (MalformedJwtException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return user;
    }

    public static void main(String[] args) {
        /*User user = new User()
                .setId(1)
                .setUsername("chenjie")
                .setPassword("123456")
                .setNickname("wudi");
        String jwtToken = createJwtToken(user);
        System.out.println(jwtToken);*/

        User user1 = paseJwtToken("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxIiwic3ViIjoiY2hlbmppZSIsImlhdCI6MTU5MDY4MDgwNiwiZXhwIjoxNTkxMjg1NjA2LCJpZCI6MSwidXNlcm5hbWUiOiJjaGVuamllIiwibmlja25hbWUiOiJ3dWRpIn0.EkVZd3Z6-zy1bl4QHKTJ6p2gFYwF3f4NBdQTLCuzq8o");
    }

}
