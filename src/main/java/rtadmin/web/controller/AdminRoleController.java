package rtadmin.web.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import rtadmin.entity.AdminRole;
import rtadmin.servcie.AdminRoleService;
import rtadmin.vo.ResultVO;

@RestController
@Slf4j
public class AdminRoleController {

  @Autowired
  private AdminRoleService AdminRoleService;

  @RequestMapping(value = "/admin/role", method = RequestMethod.GET )
  public ResultVO<List<AdminRole>> list(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                        @RequestParam(value = "size", defaultValue = "50") Integer size) {
    PageRequest request = PageRequest.of(page, size);
    Page<AdminRole> adminUserPage = AdminRoleService.findAll(request);
    log.info("获取数据用户列表成功");
    return ResultVO.success(adminUserPage.getContent());
  }
}