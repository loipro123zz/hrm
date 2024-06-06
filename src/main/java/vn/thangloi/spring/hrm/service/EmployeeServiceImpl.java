package vn.thangloi.spring.hrm.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.thangloi.spring.hrm.dao.EmployeeRepository;
import vn.thangloi.spring.hrm.entity.Employee;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }


    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployeeById(int id) {
        return employeeRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    @Transactional
    public Employee updateEmployee(Employee employee) {
        return employeeRepository.saveAndFlush(employee);
    }

    @Override
    @Transactional
    public void deleteEmployeeById(int id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public int getEmployeeCountByDepartmentId(int departmentId) {
        return employeeRepository.countByDepartmentId(departmentId);
    }

    @Override
    public int getEmployeeCountByPositionId(int positionId) {
        return employeeRepository.countByPositionId(positionId);
    }

    @Override
    public List<Employee> getEmployeeByDepartmentId(int departmentId) {
        return employeeRepository.findByDepartmentId(departmentId);
    }


}
