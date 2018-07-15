package rtadmin.servcie;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import rtadmin.entity.AdminUser;

public interface AdminUserService {
  Page<AdminUser> findAll(Pageable pageable);
}