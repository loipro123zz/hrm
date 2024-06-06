package vn.thangloi.spring.hrm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.thangloi.spring.hrm.entity.Department;
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
        return "department/departments";
    }

    @PostMapping("/save")
    public String saveDepartment(@ModelAttribute("department") Department department, RedirectAttributes redirectAttributes) {
        departmentService.saveDepartment(department);
        redirectAttributes.addFlashAttribute("message", "Thêm phòng ban thành công!");
        return "redirect:/departments/list";
    }

    @PostMapping("/update")
    public String updateDepartment(@ModelAttribute("department") Department department, RedirectAttributes redirectAttributes) {
        departmentService.updateDepartment(department);
        redirectAttributes.addFlashAttribute("message", "Cập nhật phòng ban thành công!");
        return "redirect:/departments/list";
    }

    @PostMapping("delete/{id}")
    public String deleteDepartment(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        departmentService.deleteDepartmentById(id);
        redirectAttributes.addFlashAttribute("message", "Xóa phòng ban thành công!");
        return "redirect:/departments/list";
    }
}