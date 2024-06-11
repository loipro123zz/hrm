package vn.thangloi.spring.hrm.service;

import vn.thangloi.spring.hrm.entity.LeaveRequest;

import java.util.List;

public interface LeaveRequestService {
    public List<LeaveRequest> getAllLeaveRequest();

    public LeaveRequest createLeaveRequest(LeaveRequest leaveRequest);

    public LeaveRequest approveLeaveRequest(int leaveRequestId);

    public LeaveRequest rejectLeaveRequest(int leaveRequestId);

}
