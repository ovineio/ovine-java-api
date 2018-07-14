package rtadmin.servcie;

import org.springframework.data.domain.Page;
import java.awt.print.Pageable;
import rtadmin.entity.AdminRole;

public interface AdminRoleService {
  Page<AdminRole> findAll(Pageable pageable);
}