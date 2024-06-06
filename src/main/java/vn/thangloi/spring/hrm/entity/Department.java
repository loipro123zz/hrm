package vn.thangloi.spring.hrm.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "departments")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 11)
    private int id;
    @Column(name = "name", length = 200)
    private String name;
    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "department")
    private Set<Employee> employees;

    @OneToOne
    @JoinColumn(name = "manager_id", unique = true)
    private Employee manager;

    public Department() {
    }

    public Department(String name, String description, Set<Employee> employees, Employee manager) {
        this.name = name;
        this.description = description;
        this.employees = employees;
        this.manager = manager;
    }

    public Department(int id, String name, String description, Set<Employee> employees, Employee manager) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.employees = employees;
        this.manager = manager;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }


}
