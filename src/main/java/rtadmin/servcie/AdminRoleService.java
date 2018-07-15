package rtadmin.servcie;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import rtadmin.entity.AdminRole;

public interface AdminRoleService {
  Page<AdminRole> findAll(Pageable pageable);
}