package rtadmin.dao;


import org.springframework.data.jpa.repository.JpaRepository;

import rtadmin.entity.LoginUser;

public interface LoginUserRepository extends JpaRepository<LoginUser, Long> {
  LoginUser findByUsername(String username);
}