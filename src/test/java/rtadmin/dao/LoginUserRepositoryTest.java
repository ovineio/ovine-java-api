package rtadmin.dao;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import org.junit.Assert;
import rtadmin.entity.LoginUser;;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginUserRepositoryTest {
  @Autowired
  private LoginUserRepository repository;
  
  @Test
  public void findByUsername() {
    LoginUser loginUser = repository.findByUsername("123");

    Assert.assertNotNull(loginUser);
  }
}