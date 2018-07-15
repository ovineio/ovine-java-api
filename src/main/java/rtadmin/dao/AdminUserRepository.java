package rtadmin.dao;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import rtadmin.entity.AdminUser;


public interface AdminUserRepository extends JpaRepository<AdminUser, Long> {
    Page<AdminUser> findAll(Pageable pageable);
    List<AdminUser> findByRealName(String realname);
}