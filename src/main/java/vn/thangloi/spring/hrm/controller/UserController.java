package vn.thangloi.spring.hrm.controller;

import com.sun.nio.sctp.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.boot.autoconfigure.pulsar.PulsarProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.thangloi.spring.hrm.entity.Account;
import vn.thangloi.spring.hrm.entity.Employee;
import vn.thangloi.spring.hrm.entity.LeaveRequest;
import vn.thangloi.spring.hrm.service.AccountService;
import vn.thangloi.spring.hrm.service.LeaveRequestService;

@Controller
@RequestMapping("users")
public class UserController {

    private AccountService accountService;

    private LeaveRequestService leaveRequestService;


    @Autowired
    public UserController(AccountService accountService, LeaveRequestService leaveRequestService) {
        this.accountService = accountService;
        this.leaveRequestService = leaveRequestService;
    }

    @GetMapping("/detail")
    public String showUserHome(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Account account = accountService.findAccountByUserName(username);

        Employee employee = account.getEmployee();

        model.addAttribute("employee", employee);
        return "user/user-detail";
    }

    @GetMapping("/view-form")
    public String viriewForm(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Account account = accountService.findAccountByUserName(username);
        Employee employee = account.getEmployee();
        int employeeId = employee.getId();
        model.addAttribute("employeeId", employeeId);
        return "user/leave-request-form";
    }

    @PostMapping("/create-leave-request")
    public String createLeaveRequest(@ModelAttribute("leaveRequest") LeaveRequest leaveRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Account account = accountService.findAccountByUserName(username);
        Employee employee = account.getEmployee();
        leaveRequest.setEmployee(employee);
        leaveRequest.setStatus("Pending");
        leaveRequestService.createLeaveRequest(leaveRequest);
        return "redirect:/";
    }


}
