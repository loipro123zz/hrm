package vn.thangloi.spring.hrm.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.thangloi.spring.hrm.entity.Attendance;
import vn.thangloi.spring.hrm.entity.Employee;

import java.util.Date;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {
    List<Attendance> findByEmployeeIdAndDate(int employeeId, Date date);

    List<Attendance> findByDate(Date date);

    List<Attendance> findByEmployeeAndDateBetween(Employee employee, Date formDate, Date toDate);

}
