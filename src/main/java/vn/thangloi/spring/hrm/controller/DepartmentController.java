package vn.thangloi.spring.hrm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.thangloi.spring.hrm.entity.Department;
import vn.thangloi.spring.hrm.entity.Employee;
import vn.thangloi.spring.hrm.service.DepartmentService;
import vn.thangloi.spring.hrm.service.EmployeeService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/departments")
public class DepartmentController {
    private DepartmentService departmentService;

    private EmployeeService employeeService;

    @Autowired
    public DepartmentController(DepartmentService departmentService, EmployeeService employeeService) {
        this.departmentService = departmentService;
        this.employeeService = employeeService;
    }

    @GetMapping("/list")
    public String listAll(Model model) {
        List<Department> departments = departmentService.getAllDepartments();
        model.addAttribute("departments", departments);

        Map<Integer, Integer> departmentEmployeesCounts = new HashMap<>();
        for (Department department : departments) {
            int employeeCount = employeeService.getEmployeeCountByDepartmentId(department.getId());
            departmentEmployeesCounts.put(department.getId(), employeeCount);
        }
        model.addAttribute("departmentEmployeeCounts", departmentEmployeesCounts);

        Map<Integer, List<Employee>> departmentEmployees = new HashMap<>();
        for (Department department : departments) {
            List<Employee> employees = employeeService.getEmployeeByDepartmentId(department.getId());
            departmentEmployees.put(department.getId(), employees);
        }
        model.addAttribute("departmentEmployees", departmentEmployees);

        return "department/departments";
    }

    @PostMapping("/save")
    public String saveDepartment(@ModelAttribute("department") Department department, RedirectAttributes redirectAttributes) {
        departmentService.saveDepartment(department);
        redirectAttributes.addFlashAttribute("message", "Thêm phòng ban thành công!");
        return "redirect:/departments/list";
    }

    @PostMapping("/update")
    public String updateDepartment(@ModelAttribute("department") Department department, @RequestParam("managerId") int managerId, RedirectAttributes redirectAttributes) {
        // Cập nhật trưởng phòng cho department
        Employee manager = employeeService.getEmployeeById(managerId);
        department.setManager(manager);
        departmentService.updateDepartment(department);

        // Cập nhật thông tin trưởng phòng cho các phòng ban
        List<Employee> employees = employeeService.getEmployeeByDepartmentId(department.getId());
        for (Employee employee : employees) {
            employee.setManager(manager);
            employeeService.updateEmployee(employee);
        }

        redirectAttributes.addFlashAttribute("message", "Cập nhật phòng ban thành công!");
        return "redirect:/departments/list";
    }

    @PostMapping("delete/{id}")
    public String deleteDepartment(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        try {
            departmentService.deleteDepartmentById(id);
            redirectAttributes.addFlashAttribute("message", "Xóa phòng ban thành công!");
            return "redirect:/departments/list";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/departments/list";
        }
    }
}
