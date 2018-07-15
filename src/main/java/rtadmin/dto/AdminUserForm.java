package rtadmin.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class AdminUserForm {
  /**
   * 角色ID号
   */
  private Integer roleId;

  /**
   * 用户真实名字
   */
  @NotEmpty(message = "用户名字不能为空")
  private String realName;

  /**
   * 用户登陆账号
   */
  @NotEmpty(message = "用户登陆名不能为空")
  private String username;

  /**
   * 设置用户密码
   */
  private String password;

}