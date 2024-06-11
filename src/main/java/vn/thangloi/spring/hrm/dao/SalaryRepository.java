package vn.thangloi.spring.hrm.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.thangloi.spring.hrm.entity.Salary;

public interface SalaryRepository extends JpaRepository<Salary, Long> {
}
