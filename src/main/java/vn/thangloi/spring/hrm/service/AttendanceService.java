package vn.thangloi.spring.hrm.service;


import vn.thangloi.spring.hrm.entity.Attendance;
import vn.thangloi.spring.hrm.entity.Employee;


import java.util.Date;
import java.util.List;
import java.util.Map;


public interface AttendanceService {
    public List<Attendance> getAllAttendances();

    public List<Attendance> getAttendancesByEmployeeAndDate(int employeeId, Date date);

    public Attendance saveAttendace(Attendance attendance);

    public void deleteAttendance(int attendanceId);

    public boolean hasCheckedInToday(Employee employee);

    public boolean hasCheckedOutToday(Employee employee);

    public String checkIn(int employeeId);

    public String checkOut(int employeeId);

    public List<Attendance> getTodayAttendances();

    public Map<String, List<Employee>> getAttendanceSummary();

}
