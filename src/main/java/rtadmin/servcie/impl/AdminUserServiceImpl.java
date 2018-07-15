package rtadmin.servcie.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import lombok.extern.slf4j.Slf4j;
import rtadmin.dao.AdminUserRepository;
import rtadmin.dao.LoginUserRepository;
import rtadmin.dto.AdminUserForm;
import rtadmin.entity.AdminUser;
import rtadmin.entity.LoginUser;
import rtadmin.servcie.AdminUserService;

@Service
@Slf4j
public class AdminUserServiceImpl implements AdminUserService {
  @Autowired
  private AdminUserRepository adminUserRepository;
  @Autowired
  private LoginUserRepository loginUserRepository;

  public Page<AdminUser> findAll(Pageable pageable) {
      return adminUserRepository.findAll(pageable);
  }

  @Override
  @Transactional
  public void create(AdminUserForm adminUserForm) {
    AdminUser adminUser = new AdminUser();
    adminUser.setRealName(adminUserForm.getRealName());
    adminUser.setRoleId(adminUserForm.getRoleId());

    AdminUser newAdminUser = adminUserRepository.save(adminUser);

    log.info("【admin user创建成功】");

    LoginUser loginUser = new LoginUser();
    loginUser.setUserId(newAdminUser.getUserId());
    loginUser.setUsername(adminUserForm.getUsername());
    loginUser.setPassword(DigestUtils.md5DigestAsHex(adminUserForm.getPassword().getBytes()));

    loginUserRepository.save(loginUser);

    log.info("login user创建成功】");
  }
}