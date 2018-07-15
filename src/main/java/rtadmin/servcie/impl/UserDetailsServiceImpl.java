package rtadmin.servcie.impl;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import rtadmin.dao.LoginUserRepository;
import rtadmin.entity.LoginUser;

import static java.util.Collections.emptyList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  private LoginUserRepository loginUserRepository;


  public UserDetailsServiceImpl(LoginUserRepository loginUserRepository) {
    this.loginUserRepository = loginUserRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      LoginUser user = loginUserRepository.findByUsername(username);
      if(user == null){
          throw new UsernameNotFoundException(username);
      }
      return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), emptyList());
  }
}