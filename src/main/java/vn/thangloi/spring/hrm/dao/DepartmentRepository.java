package vn.thangloi.spring.hrm.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.thangloi.spring.hrm.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
}
