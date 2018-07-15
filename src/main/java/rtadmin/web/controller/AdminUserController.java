package rtadmin.web.controller;


import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import rtadmin.dto.AdminUserForm;
import rtadmin.entity.AdminUser;
import rtadmin.servcie.AdminUserService;
import rtadmin.vo.ResultVO;

@RestController
@Slf4j
public class AdminUserController {

  @Autowired
  private AdminUserService adminUserService;

  @GetMapping("/admin/user")
  public List<AdminUser> list(@RequestParam(value = "page", defaultValue = "0") Integer page,
                              @RequestParam(value = "size", defaultValue = "50") Integer size) {
    PageRequest request = PageRequest.of(page, size);
    Page<AdminUser> adminUserPage = adminUserService.findAll(request);
    return adminUserPage.getContent();
  }

  @PostMapping("/admin/user")
  public ResultVO create(@RequestBody @Valid AdminUserForm adminUserForm, BindingResult bindingResult) {

    if (bindingResult.hasErrors()) {
      log.error("【参数不正确】添加admin user 参数不正确{}", adminUserForm);
      return ResultVO.error(bindingResult.getFieldError().getDefaultMessage());
    }

    String pwd = adminUserForm.getPassword();
    if (null == pwd || "" == pwd.trim() ) {
      return ResultVO.error("密码不能为空");
    }

    adminUserService.create(adminUserForm);
    return ResultVO.success();
  }
}