package vn.thangloi.spring.hrm.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.thangloi.spring.hrm.entity.Employee;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    int countByDepartmentId(int departmentId);

    int countByPositionId(int positionId);

    List<Employee> findByDepartmentId(int departmentId);
}
