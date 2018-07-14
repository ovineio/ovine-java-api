package rtadmin.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class AdminUser {
  @Id
  @GeneratedValue
  private long userId;

  /**
   * 角色ID
   */
  private Integer roleId;

  /**
   * 用户真实名字
   */
  private String realName;

  /**
   * 用户昵称
   */
  private String nickName;

  /**
   * 用户登陆时的ip地址
   */
  private String ip;

  /**
   * 用户头像
   */
  private String avatar;
}