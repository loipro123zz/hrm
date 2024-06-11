package vn.thangloi.spring.hrm.entity;

import jakarta.persistence.*;
import vn.thangloi.spring.hrm.entity.Employee;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @Column(name = "username", length = 50)
    private String username;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name = "active", nullable = false)
    private boolean active;

    @OneToOne
    @JoinColumn(name = "employee_id", unique = true)
    private Employee employee;

    public Account() {
    }

    public Account(String password, boolean active, Employee employee) {
        this.password = password;
        this.active = active;
        this.employee = employee;
    }

    public Account(String username, String password, boolean active, Employee employee) {
        this.username = username;
        this.password = password;
        this.active = active;
        this.employee = employee;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
