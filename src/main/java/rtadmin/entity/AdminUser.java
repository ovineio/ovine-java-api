package rtadmin.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Entity
@Data
@JsonInclude(JsonInclude.Include.NON_NULL) // 设置不返回 为 null的字段
public class AdminUser {
  @Id
  @TableGenerator(
    name = "adminUser",
    initialValue = 10000
  )
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "adminUser")
  private long userId; // 设置自增ID 从 10000 开始

  /**
   * 角色ID
   */
  private Integer roleId;

  /**
   * 用户真实名字 【必填】
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