package vn.thangloi.spring.hrm.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.thangloi.spring.hrm.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    int countByDepartmentId(int departmentId);

    int countByPositionId(int positionId);
}
