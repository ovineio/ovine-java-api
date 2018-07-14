package rtadmin.servcie.impl;

import java.awt.print.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import rtadmin.dao.AdminRoleRepository;
import rtadmin.entity.AdminRole;
import rtadmin.servcie.AdminRoleService;

public class AdminRoleServiceImpl implements AdminRoleService {
  @Autowired
  private AdminRoleRepository repository;
  
  public Page<AdminRole> findAll(Pageable pageable) {
      return repository.findAll(pageable);
  }

}