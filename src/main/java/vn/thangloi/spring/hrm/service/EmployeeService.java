package vn.thangloi.spring.hrm.service;

import vn.thangloi.spring.hrm.entity.Employee;

import java.util.List;

public interface EmployeeService {
    public List<Employee> getAllEmployees();

    public Employee getEmployeeById(int id);

    public Employee saveEmployee(Employee employee);

    public Employee updateEmployee(Employee employee);

    public void deleteEmployeeById(int id);

    public int getEmployeeCountByDepartmentId(int departmentId);

    public int getEmployeeCountByPositionId(int positionId);

    public List<Employee> getEmployeeByDepartmentId(int departmentId);

}
