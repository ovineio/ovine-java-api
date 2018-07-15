package rtadmin.servcie;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import rtadmin.dto.AdminUserForm;
import rtadmin.entity.AdminUser;

public interface AdminUserService {
  Page<AdminUser> findAll(Pageable pageable);

  /**
   * 创建新admin用户
   */
  public void create(AdminUserForm adminUserForm);
}