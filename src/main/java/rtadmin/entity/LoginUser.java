package rtadmin.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class LoginUser {

  @Id
  private long userId;

  /**
   * 用户登陆账号
   */
  private String username;

  /**
   * 用户登陆密码
   */
  private String password;
}