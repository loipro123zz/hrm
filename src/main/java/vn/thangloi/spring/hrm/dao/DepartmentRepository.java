package vn.thangloi.spring.hrm.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.thangloi.spring.hrm.entity.Department;
import vn.thangloi.spring.hrm.entity.Employee;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    Department findByManagerId(int managerId);
}
