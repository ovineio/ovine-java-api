package rtadmin.servcie.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import rtadmin.dao.AdminRoleRepository;
import rtadmin.entity.AdminRole;
import rtadmin.servcie.AdminRoleService;

@Service
public class AdminRoleServiceImpl implements AdminRoleService {
  @Autowired
  private AdminRoleRepository repository;
  
  public Page<AdminRole> findAll(Pageable pageable) {
      return repository.findAll(pageable);
  }
}