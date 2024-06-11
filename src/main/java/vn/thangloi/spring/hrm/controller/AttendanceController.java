package vn.thangloi.spring.hrm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.thangloi.spring.hrm.entity.Account;
import vn.thangloi.spring.hrm.entity.Attendance;
import vn.thangloi.spring.hrm.entity.Employee;
import vn.thangloi.spring.hrm.service.AccountService;
import vn.thangloi.spring.hrm.service.AttendanceService;
import vn.thangloi.spring.hrm.service.EmployeeService;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("attendances")
public class AttendanceController {
    private AttendanceService attendanceService;
    private AccountService accountService;

    private EmployeeService employeeService;

    @Autowired
    public AttendanceController(AttendanceService attendanceService, AccountService accountService, EmployeeService employeeService) {
        this.attendanceService = attendanceService;
        this.accountService = accountService;
        this.employeeService = employeeService;
    }

    @GetMapping("/list")
    public String listAll(Model model) {
        List<Attendance> attendances = attendanceService.getAllAttendances();
        model.addAttribute("attendances", attendances);
        return "attendance/attendances";
    }

    @GetMapping("/summary")
    public String getAttendanceSummary(Model model) {
        Map<String, List<Employee>> summary = attendanceService.getAttendanceSummary();
        model.addAttribute("summary", summary);
        return "redirect:/attendances/list";
    }

    // User
    @GetMapping("user-list")
    public String getUserAttendances(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Account account = accountService.findAccountByUserName(username);
        Employee employee = account.getEmployee();
        int employeeId = employee.getId();

        List<Attendance> attendances = attendanceService.getAttendancesByEmployeeAndDate(employeeId, new Date(System.currentTimeMillis()));
        model.addAttribute("attendances", attendances);

        return "user/user-attendances";
    }

    @PostMapping("/user/checkin")
    public String checkIn(Model model, RedirectAttributes redirectAttributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Account account = accountService.findAccountByUserName(username);
        Employee employee = account.getEmployee();
        String message = attendanceService.checkIn(employee.getId());
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/attendances/user-list";
    }

    @PostMapping("user/checkout")
    public String checkOut(Model model, RedirectAttributes redirectAttributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Account account = accountService.findAccountByUserName(username);
        Employee employee = account.getEmployee();
        String message = attendanceService.checkOut(employee.getId());
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/attendances/user-list";
    }


}
