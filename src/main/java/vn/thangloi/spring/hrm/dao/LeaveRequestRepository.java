package vn.thangloi.spring.hrm.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.thangloi.spring.hrm.entity.LeaveRequest;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Integer> {

}
