package rtadmin.dao;

import java.awt.print.Pageable;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import rtadmin.entity.AdminRole;


public interface AdminRoleRepository extends JpaRepository<AdminRole, Long> {
    Page<AdminRole> findAll(Pageable pageable);
}