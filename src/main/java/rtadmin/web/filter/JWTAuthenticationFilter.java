package rtadmin.web.filter;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * token的校验
 * 该类继承自BasicAuthenticationFilter，在doFilterInternal方法中，
 * 从http头的Authorization 项读取token数据，然后用Jwts包提供的方法校验token的合法性。
 * 如果校验通过，就认为这是一个取得授权的合法请求
 */
@Slf4j
public class JWTAuthenticationFilter extends BasicAuthenticationFilter {

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }
        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null) {
          return null;
        }
            
        String user = null; // parse the token.
        try {
            user = Jwts.parser()
                    .setSigningKey("rtadmin")
                    .parseClaimsJws(token.replace("Bearer ", ""))
                    .getBody()
                    .getSubject();
            if (user == null) {
                return null;
            }
            return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
        } catch (ExpiredJwtException e) {
            log.error("Token已过期: {} " + e);
            throw new RuntimeException("Token已过期");
        } catch (UnsupportedJwtException e) {
            log.error("Token格式错误: {} " + e);
            throw new RuntimeException("Token格式错误");
        } catch (MalformedJwtException e) {
            log.error("Token没有被正确构造: {} " + e);
            throw new RuntimeException("Token没有被正确构造");
        } catch (SignatureException e) {
            log.error("签名失败: {} " + e);
            throw new RuntimeException("签名失败");
        } catch (IllegalArgumentException e) {
            log.error("非法参数异常: {} " + e);
            throw new RuntimeException("非法参数异常");
        }
    }

}
