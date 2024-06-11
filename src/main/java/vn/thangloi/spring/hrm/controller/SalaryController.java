package vn.thangloi.spring.hrm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.thangloi.spring.hrm.entity.Employee;
import vn.thangloi.spring.hrm.entity.Salary;
import vn.thangloi.spring.hrm.service.EmployeeService;
import vn.thangloi.spring.hrm.service.SalaryService;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@Controller
public class SalaryController {

    private final EmployeeService employeeService;
    private final SalaryService salaryService;

    @Autowired
    public SalaryController(EmployeeService employeeService, SalaryService salaryService) {
        this.employeeService = employeeService;
        this.salaryService = salaryService;
    }

    @GetMapping("/calculate-salaries")
    public String calculateSalaries(@RequestParam Date fromDate, @RequestParam Date toDate, Model model) {
        List<Employee> employees = employeeService.getAllEmployees();
        for (Employee employee : employees) {
            BigDecimal salaryAmount = salaryService.calculateSalaryForEmployee(employee, fromDate, toDate);
            salaryService.saveSalary(employee, salaryAmount, fromDate, toDate);
        }
        return "redirect:/salaries";
    }

    @GetMapping("/salaries")
    public String showSalaries(Model model) {
        List<Salary> salaries = salaryService.getAllSalaries();
        model.addAttribute("salaries", salaries);
        return "salaries";
    }
}
