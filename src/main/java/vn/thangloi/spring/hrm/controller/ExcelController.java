package vn.thangloi.spring.hrm.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.thangloi.spring.hrm.entity.Attendance;
import vn.thangloi.spring.hrm.entity.Employee;
import vn.thangloi.spring.hrm.service.AccountService;
import vn.thangloi.spring.hrm.service.AttendanceService;
import vn.thangloi.spring.hrm.service.EmployeeService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/export")
public class ExcelController {

    private EmployeeService employeeService;
    private AttendanceService attendanceService;

    @Autowired
    public ExcelController(EmployeeService employeeService, AttendanceService attendanceService) {
        this.employeeService = employeeService;
        this.attendanceService = attendanceService;
    }

    @GetMapping("/employees")
    public void exportToEmployeesExcel(HttpServletResponse response) throws IOException {
        // tao workbook
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Danh sách nhân viên");

        // Tạo header
        Row headerRow = sheet.createRow(0);
        String[] headers = {"ID", "Email", "Họ", "Tên", "Số điện thoại", "Địa chỉ", "Phòng ban", "Vị trí", "Trưởng phòng"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        // Lay danh sach nhan vien tu Database
        List<Employee> employees = employeeService.getAllEmployees();

        int rowNum = 1;
        for (Employee employee : employees) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(employee.getId());
            row.createCell(1).setCellValue(employee.getEmail());
            row.createCell(2).setCellValue(employee.getLastName());
            row.createCell(3).setCellValue(employee.getFirstName());
            row.createCell(4).setCellValue(employee.getPhoneNumber());
            row.createCell(5).setCellValue(employee.getAddress());
            row.createCell(6).setCellValue(employee.getDepartment().getName()); // Tên phòng ban
            row.createCell(7).setCellValue(employee.getPosition().getName()); // Tên vị trí
            if (employee.getManager() != null) {
                row.createCell(8).setCellValue(employee.getManager().getLastName() + " " + employee.getManager().getFirstName());
            } else {
                row.createCell(8).setCellValue("");
            }
        }

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=employees.xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();

    }

    @GetMapping("/late-summary")
    public void exportLateEmployeesToExcel(HttpServletResponse response) throws IOException {
        Map<String, List<Employee>> summary = attendanceService.getAttendanceSummary();

        List<Employee> lateEmployees = summary.get("Đi Trễ");

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Late Employees");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Nhân Viên Đi Trễ");

        int rowNum = 1;
        for (Employee employee : lateEmployees) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(employee.getLastName() + ' ' + employee.getFirstName());
        }

        // Set content type and attachment header
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=late_employees.xlsx");

        // Write the workbook content to response stream
        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
