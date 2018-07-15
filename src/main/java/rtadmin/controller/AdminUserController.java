package rtadmin.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import rtadmin.entity.AdminUser;
import rtadmin.servcie.AdminUserService;

@RestController
@RequestMapping("/admin/user")
@Slf4j
public class AdminUserController {

  @Autowired
  private AdminUserService adminUserService;

  @GetMapping("/list")
  public List<AdminUser> list(@RequestParam(value = "page", defaultValue = "0") Integer page,
                              @RequestParam(value = "size", defaultValue = "50") Integer size) {
    PageRequest request = PageRequest.of(page, size);
    Page<AdminUser> adminUserPage = adminUserService.findAll(request);
    return adminUserPage.getContent();
  }
}