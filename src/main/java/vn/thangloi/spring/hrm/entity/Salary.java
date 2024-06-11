package vn.thangloi.spring.hrm.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Table(name = "salaries")
public class Salary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "salary_amount", nullable = false)
    private BigDecimal salaryAmount;

    @Column(name = "from_date", nullable = false)
    private Date fromDate;

    @Column(name = "to_date", nullable = false)
    private Date toDate;

    public Salary() {
    }

    public Salary(Employee employee, BigDecimal salaryAmount, Date fromDate, Date toDate) {
        this.employee = employee;
        this.salaryAmount = salaryAmount;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public Salary(Long id, Employee employee, BigDecimal salaryAmount, Date fromDate, Date toDate) {
        this.id = id;
        this.employee = employee;
        this.salaryAmount = salaryAmount;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public Salary(Employee employee) {
        this.employee = employee;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public BigDecimal getSalaryAmount() {
        return salaryAmount;
    }

    public void setSalaryAmount(BigDecimal salaryAmount) {
        this.salaryAmount = salaryAmount;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }
}
