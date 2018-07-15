package rtadmin.servcie.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import rtadmin.dao.AdminUserRepository;
import rtadmin.entity.AdminUser;
import rtadmin.servcie.AdminUserService;

@Service
public class AdminUserServiceImpl implements AdminUserService {
  @Autowired
  private AdminUserRepository repository;
  
  public Page<AdminUser> findAll(Pageable pageable) {
      return repository.findAll(pageable);
  }
}