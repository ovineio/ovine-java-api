package rtadmin.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class AdminRole {
  @Id
  @GeneratedValue
  private long roleId;

  /**
   * 角色名称
   */
  private String name;

  /**
   * 角色描述
   */
  private String remark;

  /**
   * 后端api限制
   */
  private String apis;

  /**
   * 前端页面限制
   */
  private String views;
}