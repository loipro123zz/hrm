package vn.thangloi.spring.hrm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.thangloi.spring.hrm.dao.AttendanceRepository;
import vn.thangloi.spring.hrm.dao.SalaryRepository;
import vn.thangloi.spring.hrm.entity.Attendance;
import vn.thangloi.spring.hrm.entity.Employee;
import vn.thangloi.spring.hrm.entity.Salary;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@Service
public class SalaryService {

    private AttendanceRepository attendanceRepository;

    private SalaryRepository salaryRepository;

    @Autowired
    public SalaryService(AttendanceRepository attendanceRepository, SalaryRepository salaryRepository) {
        this.attendanceRepository = attendanceRepository;
        this.salaryRepository = salaryRepository;
    }


    public BigDecimal calculateSalaryForEmployee(Employee employee, Date fromDate, Date toDate) {
        List<Attendance> attendances = attendanceRepository.findByEmployeeAndDateBetween(employee, fromDate, toDate);
        double totalHoursWorked = calculateTotalHoursWorked(attendances);
        BigDecimal salaryAmount = calculateSalaryFromHoursWorked(totalHoursWorked);
        return salaryAmount;
    }

    public void saveSalary(Employee employee, BigDecimal salaryAmount, Date fromDate, Date toDate) {
        Salary salary = new Salary(employee, salaryAmount, fromDate, toDate);
        salaryRepository.save(salary);
    }


    private double calculateTotalHoursWorked(List<Attendance> attendances) {
        double totalHoursWorked = 0;
        for (Attendance attendance : attendances) {
            long diffInMillies = Math.abs(attendance.getCheckOutTime().getTime() - attendance.getCheckInTime().getTime());
            totalHoursWorked += (double) diffInMillies / (1000 * 60 * 60);
        }
        return totalHoursWorked;
    }

    private BigDecimal calculateSalaryFromHoursWorked(double totalHoursWorked) {
        double hourlyRate = 100000000;
        BigDecimal salaryAmount = BigDecimal.valueOf(totalHoursWorked * hourlyRate);
        return salaryAmount;
    }

    public List<Salary> getAllSalaries() {
        return salaryRepository.findAll();
    }
}
