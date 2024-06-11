package vn.thangloi.spring.hrm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.thangloi.spring.hrm.dao.AccountRepository;
import vn.thangloi.spring.hrm.dao.EmployeeRepository;
import vn.thangloi.spring.hrm.dao.RoleRepository;
import vn.thangloi.spring.hrm.entity.Account;
import vn.thangloi.spring.hrm.entity.Employee;
import vn.thangloi.spring.hrm.entity.Role;

import java.util.List;

@Controller
@RequestMapping()
public class RegisterController {
    private EmployeeRepository employeeRepository;
    private AccountRepository accountRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public RegisterController(EmployeeRepository employeeRepository, AccountRepository accountRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register-form")
    public String showRegisterForm(Model model) {
        List<Employee> employees = employeeRepository.findAll();
        model.addAttribute("employees", employees);
        model.addAttribute("account", new Account());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("employeeId") int employeeId,
                               @ModelAttribute("username") String username,
                               @ModelAttribute("password") String password,
                               @ModelAttribute("password2") String password2,
                               @ModelAttribute("active") boolean active,
                               @ModelAttribute("role") String roleName,
                               Model model, RedirectAttributes redirectAttributes) {
        try {
            Employee employee = employeeRepository.findById(employeeId)
                    .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy nhân viên có ID" + employeeId));

            if (accountRepository.findByUsername(username) != null) {
                redirectAttributes.addFlashAttribute("error", "Username Này Đã Tồn Tại!!!");
                return "redirect:/register-form";
            }

            if (!(password.equals(password2))) {
                redirectAttributes.addFlashAttribute("error", "Password Và Password Xác Thực Không Khớp. Vui Lòng Tạo Lại!!!");
                return "redirect:/register-form";
            }

            String encryptedPassword = passwordEncoder.encode(password);

            Account account = new Account(username, encryptedPassword, active, employee);
            accountRepository.save(account);

            Role role = new Role(roleName, account);
            roleRepository.save(role);
            redirectAttributes.addFlashAttribute("message", "Đăng Kí Tài Khoản Thành Công.");
            return "redirect:/register-form";
        } catch (Exception e) {
            model.addAttribute("error", "Registration failed. Please try again.");
            return "redirect:/register-form";
        }
    }
}
