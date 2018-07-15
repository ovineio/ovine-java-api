package rtadmin.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import rtadmin.entity.AdminUser;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminUserRepositoryTest {
  @Autowired
  private AdminUserRepository repository;
  
  @Test
  public void saveUserTest() {
    AdminUser adminUser = new AdminUser();

    adminUser.setRoleId(3);
    adminUser.setRealName("嘻嘻哈哈");
    repository.save(adminUser);

    List<AdminUser> adminXiao = repository.findByRealName("嘻嘻哈哈");
    Assert.assertEquals(1, adminXiao.size());
  }
}