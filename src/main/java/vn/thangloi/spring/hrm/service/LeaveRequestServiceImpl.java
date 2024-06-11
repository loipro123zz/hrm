package vn.thangloi.spring.hrm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.thangloi.spring.hrm.dao.LeaveRequestRepository;
import vn.thangloi.spring.hrm.entity.LeaveRequest;

import java.util.List;

@Service
public class LeaveRequestServiceImpl implements LeaveRequestService {

    private LeaveRequestRepository leaveRequestRepository;

    @Autowired
    public LeaveRequestServiceImpl(LeaveRequestRepository leaveRequestRepository) {
        this.leaveRequestRepository = leaveRequestRepository;
    }

    @Override
    public List<LeaveRequest> getAllLeaveRequest() {
        return leaveRequestRepository.findAll();
    }

    @Override
    public LeaveRequest createLeaveRequest(LeaveRequest leaveRequest) {
        return leaveRequestRepository.save(leaveRequest);
    }

    @Override
    public LeaveRequest approveLeaveRequest(int leaveRequestId) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(leaveRequestId).orElse(null);
        if (leaveRequest != null) {
            leaveRequest.setStatus("Approved");
            return leaveRequestRepository.save(leaveRequest);
        }

        return null;
    }

    @Override
    public LeaveRequest rejectLeaveRequest(int leaveRequestId) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(leaveRequestId).orElse(null);
        if (leaveRequest != null) {
            leaveRequest.setStatus("Rejected");
            return leaveRequestRepository.save(leaveRequest);
        }
        return null;
    }
}
