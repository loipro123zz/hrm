package vn.thangloi.spring.hrm.service;

import org.apache.commons.math3.stat.inference.OneWayAnova;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.thangloi.spring.hrm.dao.AttendanceRepository;
import vn.thangloi.spring.hrm.dao.EmployeeRepository;
import vn.thangloi.spring.hrm.entity.Attendance;
import vn.thangloi.spring.hrm.entity.Employee;

import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.*;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    private AttendanceRepository attendanceRepository;

    private EmployeeRepository employeeRepository;

    @Autowired
    public AttendanceServiceImpl(AttendanceRepository attendanceRepository, EmployeeRepository employeeRepository) {
        this.attendanceRepository = attendanceRepository;
        this.employeeRepository = employeeRepository;
    }


    @Override
    public List<Attendance> getAllAttendances() {
        return attendanceRepository.findAll();
    }

    @Override
    public List<Attendance> getAttendancesByEmployeeAndDate(int employeeId, Date date) {
        return attendanceRepository.findByEmployeeIdAndDate(employeeId, date);
    }

    @Override
    public Attendance saveAttendace(Attendance attendance) {
        return attendanceRepository.save(attendance);
    }

    @Override
    public void deleteAttendance(int attendanceId) {
        attendanceRepository.deleteById(attendanceId);
    }

    @Override
    public boolean hasCheckedInToday(Employee employee) {
        Date currentToday = new Date(System.currentTimeMillis());
        List<Attendance> todayAttendances = attendanceRepository.findByEmployeeIdAndDate(employee.getId(), currentToday);
        return !todayAttendances.isEmpty();
    }

    @Override
    public boolean hasCheckedOutToday(Employee employee) {
        Date currentToday = new Date(System.currentTimeMillis());
        List<Attendance> todayAttendance = attendanceRepository.findByEmployeeIdAndDate(employee.getId(), currentToday);
        return todayAttendance.stream().anyMatch(attendance -> attendance.getCheckOutTime() != null);
    }


    @Override
    public String checkIn(int employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new RuntimeException("Nhân viên không được tìm thấy"));
        if (hasCheckedInToday(employee)) {
            return "Nhân viên đã check-in hôm nay";
        }

        // Gio lam mac dinh
        LocalTime workStartTime = LocalTime.of(8, 30);
        LocalTime currentTime = LocalTime.now();

        Attendance attendance = new Attendance();
        attendance.setEmployee(employee);
        attendance.setDate(new java.sql.Date(System.currentTimeMillis()));
        attendance.setCheckInTime(new Timestamp(System.currentTimeMillis()));

        String message = "";
        if (currentTime.isAfter(workStartTime)) {
            attendance.setStatus("Trễ");
            message = "Bạn đã đi làm trễ ";
        } else {
            attendance.setStatus("Đúng giờ");
            message = "Bạn đã đi làm đúng giờ ";
        }

        attendanceRepository.save(attendance);
        return message;
    }

    @Override
    public String checkOut(int employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new RuntimeException("Nhân viên không được tìm thấy"));
        if (!hasCheckedInToday(employee)) {
            return "Nhân viên chưa check-in hôm nay";
        }
        if (hasCheckedOutToday(employee)) {
            return "Nhân viên đã check-out hôm nay";

        }
        List<Attendance> attendances = attendanceRepository.findByEmployeeIdAndDate(employeeId, new java.sql.Date(System.currentTimeMillis()));

        String message = "";
        if (!attendances.isEmpty()) {
            Attendance attendance = attendances.get(0);

            LocalTime workEndTime = LocalTime.of(17, 30);
            LocalTime checkOutTime = LocalTime.now();

            String currentStatus = attendance.getStatus();
            if (checkOutTime.isBefore(workEndTime)) {
                if ("Trễ".equals(currentStatus)) {
                    attendance.setStatus("Đi trễ và rời sớm");
                } else {
                    attendance.setStatus("Rời sớm");
                }
            } else {
                if ("Trễ".equals(currentStatus)) {
                    attendance.setStatus("Đi trễ nhưng hoàn thành công việc");
                } else {
                    attendance.setStatus("Hoàn thành công việc");
                }
            }
            attendance.setCheckOutTime(new Timestamp(System.currentTimeMillis()));
            attendanceRepository.save(attendance);
            message = attendance.getStatus();
        }
        return message;
    }

    @Override
    public List<Attendance> getTodayAttendances() {
        Date date = new Date(System.currentTimeMillis());
        return attendanceRepository.findByDate(date);
    }

    public Map<String, List<Employee>> getAttendanceSummary() {
        List<Attendance> attendances = getTodayAttendances();
        Map<String, List<Employee>> sumary = new HashMap<>();
        List<Employee> lateEmployees = new ArrayList<>();
        List<Employee> completedEmployees = new ArrayList<>();
        List<Employee> earlyEmployees = new ArrayList<>();

        for (Attendance attendance : attendances) {
            if ("Trễ".equals(attendance.getStatus()) || "Đi trễ nhưng hoàn thành công việc".equals(attendance.getStatus())
                    || "Đi trễ và rời sớm".equals(attendance.getStatus())) {
                lateEmployees.add(attendance.getEmployee());
            } else if ("Đúng giờ".equals(attendance.getStatus())
                    || "Hoàn thành công việc".equals(attendance.getStatus())) {
                completedEmployees.add(attendance.getEmployee());
            } else {
                earlyEmployees.add(attendance.getEmployee());
            }
        }
        sumary.put("Đi Trễ", lateEmployees);
        sumary.put("Về Sớm", earlyEmployees);
        sumary.put("Hoàn Thành", completedEmployees);
        return sumary;
    }
}
