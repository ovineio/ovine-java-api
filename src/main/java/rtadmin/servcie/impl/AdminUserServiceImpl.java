package rtadmin.servcie.impl;

import java.awt.print.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import rtadmin.dao.AdminUserRepository;
import rtadmin.entity.AdminUser;
import rtadmin.servcie.AdminUserService;

public class AdminUserServiceImpl implements AdminUserService {
  @Autowired
  private AdminUserRepository repository;
  
  public Page<AdminUser> findAll(Pageable pageable) {
      return repository.findAll(pageable);
  }
}