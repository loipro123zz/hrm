package vn.thangloi.spring.hrm.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.thangloi.spring.hrm.dao.DepartmentRepository;
import vn.thangloi.spring.hrm.entity.Department;
import vn.thangloi.spring.hrm.entity.Employee;

import java.util.List;
import java.util.Set;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    @Override
    public Department getDepartmentById(int id) {
        return departmentRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Department saveDepartment(Department department) {
        return departmentRepository.save(department);
    }

    @Override
    @Transactional
    public Department updateDepartment(Department department) {
        return departmentRepository.saveAndFlush(department);
    }

    @Override
    @Transactional
    public void deleteDepartmentById(int departmentId) {
        Department department = departmentRepository.findById(departmentId).orElse(null);
        if (department != null) {
            // Kiểm tra xem có nhân viên thuộc phòng ban này không
            Set<Employee> employees = department.getEmployees();
            if (!employees.isEmpty()) {
                // Nếu có nhân viên, thông báo cho người dùng và dừng việc xóa
                throw new RuntimeException("Không thể xóa phòng ban này vì có nhân viên thuộc phòng ban này.");
            } else {
                // Nếu không có nhân viên, xóa phòng ban
                departmentRepository.deleteById(departmentId);
            }
        } else {
            throw new RuntimeException("Không tìm thấy phòng ban với ID: " + departmentId);
        }
    }

    @Override
    public Department getDepartmentByManagerId(int managerId) {
        return departmentRepository.findByManagerId(managerId);
    }
}
