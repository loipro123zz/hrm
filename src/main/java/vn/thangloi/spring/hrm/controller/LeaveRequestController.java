package vn.thangloi.spring.hrm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.thangloi.spring.hrm.entity.LeaveRequest;
import vn.thangloi.spring.hrm.service.LeaveRequestService;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/leave-requests")
public class LeaveRequestController {
    private LeaveRequestService leaveRequestService;


    @Autowired
    public LeaveRequestController(LeaveRequestService leaveRequestService) {
        this.leaveRequestService = leaveRequestService;
    }

    @GetMapping("/list")
    public String listAll(Model model) {
        List<LeaveRequest> leaveRequests = leaveRequestService.getAllLeaveRequest();
        model.addAttribute("leaveRequests", leaveRequests);
        return "leave-request/leave-requests";
    }
    
    @PostMapping("/approve/{leaveRequestId}")
    public String approveLeaveRequest(Model model, @PathVariable("leaveRequestId") int leaveRequestId) {
        leaveRequestService.approveLeaveRequest(leaveRequestId);
        return "redirect:/leave-requests/list";
    }

    @PostMapping("/reject/{leaveRequestId}")
    public String rejectLeaveRequest(Model model, @PathVariable("leaveRequestId") int leaveRequestId) {
        leaveRequestService.rejectLeaveRequest(leaveRequestId);
        return "redirect:/leave-requests/list";
    }
}
