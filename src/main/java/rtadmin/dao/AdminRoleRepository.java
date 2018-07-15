package rtadmin.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import rtadmin.entity.AdminRole;


public interface AdminRoleRepository extends JpaRepository<AdminRole, Long> {
    Page<AdminRole> findAll(Pageable pageable);
}