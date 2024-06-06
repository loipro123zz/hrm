package vn.thangloi.spring.hrm.service;

import vn.thangloi.spring.hrm.entity.Department;

import java.util.List;

public interface DepartmentService {
    public List<Department> getAllDepartments();

    public Department getDepartmentById(int id);

    public Department saveDepartment(Department department);

    public Department updateDepartment(Department department);

    public void deleteDepartmentById(int id);
}
