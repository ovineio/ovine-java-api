package rtadmin.servcie;


import org.springframework.data.domain.Page;
import java.awt.print.Pageable;
import rtadmin.entity.AdminUser;

public interface AdminUserService {
  Page<AdminUser> findAll(Pageable pageable);
}