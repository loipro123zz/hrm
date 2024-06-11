package vn.thangloi.spring.hrm.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.thangloi.spring.hrm.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
