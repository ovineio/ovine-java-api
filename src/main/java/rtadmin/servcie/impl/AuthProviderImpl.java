package rtadmin.servcie.impl;

import java.util.ArrayList;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.DigestUtils;

public class AuthProviderImpl implements AuthenticationProvider {

  private UserDetailsService userDetailsService;

  private BCryptPasswordEncoder bCryptPasswordEncoder;

  public AuthProviderImpl( UserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.userDetailsService = userDetailsService;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    // 获取用户账号密码
    String username = authentication.getName();
    String password = authentication.getCredentials().toString();

    // 认证逻辑
    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

    if (null == userDetails) {
      throw new UsernameNotFoundException("用户不存在");
    }

    String encodePassword = DigestUtils.md5DigestAsHex((password).getBytes());

    if (!userDetails.getPassword().equals(encodePassword)) {
      throw new BadCredentialsException("密码错误");
    }

    // 这里设置权限和角色

    Authentication auth = new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
    return auth;
	}

  /**
   * 是否可以提供输入类型的认证服务
   * @param authentication
   * @return
   */
	@Override
	public boolean supports(Class<?> authentication) {
    return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}
