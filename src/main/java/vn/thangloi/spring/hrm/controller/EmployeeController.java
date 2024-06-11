package vn.thangloi.spring.hrm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.thangloi.spring.hrm.entity.Department;
import vn.thangloi.spring.hrm.entity.Employee;
import vn.thangloi.spring.hrm.entity.Position;
import vn.thangloi.spring.hrm.service.DepartmentService;
import vn.thangloi.spring.hrm.service.EmployeeService;
import vn.thangloi.spring.hrm.service.PositionService;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
    private EmployeeService employeeService;
    private DepartmentService departmentService;

    private PositionService positionService;

    @Autowired
    public EmployeeController(EmployeeService employeeService, DepartmentService departmentService, PositionService positionService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
        this.positionService = positionService;
    }


    @GetMapping("/list")
    public String listAll(Model model) {
        List<Employee> employees = employeeService.getAllEmployees();
        model.addAttribute("employees", employees);
        model.addAttribute("departments", departmentService.getAllDepartments());
        model.addAttribute("positions", positionService.getAllPositions());
        return "employee/employees";
    }

    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute("employee") Employee employee,
                               @RequestParam("departmentId") int departmentId,
                               @RequestParam("positionId") int positionId,
                               RedirectAttributes redirectAttributes) {

        // Lưu phòng ban xuống csdl
        Department department = departmentService.getDepartmentById(departmentId);
        employee.setDepartment(department);

        // Lưu vị trí xuống csdl
        Position position = positionService.getPositionById(positionId);
        employee.setPosition(position);

        employeeService.saveEmployee(employee);
        redirectAttributes.addFlashAttribute("message", "Thêm nhân viên thành công");
        return "redirect:/employees/list";

    }

    @PostMapping("/update")
    public String updateEmployee(@ModelAttribute("employee") Employee employee,
                                 @RequestParam("departmentId") int departmentId,
                                 @RequestParam("positionId") int positionId,
                                 RedirectAttributes redirectAttributes) {

        // Cập nhật phòng ban xuống csdl
        Department department = departmentService.getDepartmentById(departmentId);
        employee.setDepartment(department);

        // Cập nhật vị trí xuống csdl
        Position position = positionService.getPositionById(positionId);
        employee.setPosition(position);

        employeeService.updateEmployee(employee);
        redirectAttributes.addFlashAttribute("message", "Cập nhật nhân viên thành công");
        return "redirect:/employees/list";
    }

    @PostMapping("delete/{id}")
    public String deleteEmployee(@PathVariable("id") int id,
                                 RedirectAttributes redirectAttributes) {


        // mình phải kt xem là thằng nhân viên có phải là trưởng phòng không
        Department department = departmentService.getDepartmentByManagerId(id);
        if (department != null) {
            department.setManager(null);
            departmentService.updateDepartment(department);
        }
        // Rôì mới xóa
        employeeService.deleteEmployeeById(id);
        redirectAttributes.addFlashAttribute("message", "Xóa nhân viên thành công");
        return "redirect:/employees/list";
    }

    @GetMapping("/search/position")
    public String searchEmployeesByPosition(@RequestParam("keyword") String keyword, Model model) {
        List<Employee> foundEmployees = employeeService.searchEmployeesByPosition(keyword);
        model.addAttribute("employees", foundEmployees);
        return "employee/employees";
    }
}
