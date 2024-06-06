package vn.thangloi.spring.hrm.controller;

import jakarta.persistence.Column;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.thangloi.spring.hrm.entity.Employee;
import vn.thangloi.spring.hrm.entity.Position;
import vn.thangloi.spring.hrm.service.EmployeeService;
import vn.thangloi.spring.hrm.service.PositionService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/positions")
public class PositionController {
    private PositionService positionService;

    private EmployeeService employeeService;

    @Autowired
    public PositionController(PositionService positionService, EmployeeService employeeService) {
        this.positionService = positionService;
        this.employeeService = employeeService;
    }

    @GetMapping("/list")
    public String listAll(Model model) {
        List<Position> positions = positionService.getAllPositions();
        model.addAttribute("positions", positions);

        Map<Integer, Integer> positionEmployeesCounts = new HashMap<>();
        for (Position position : positions) {
            int employeeCount = employeeService.getEmployeeCountByPositionId(position.getId());
            positionEmployeesCounts.put(position.getId(), employeeCount);
        }
        model.addAttribute("positionEmployeeCounts", positionEmployeesCounts);

        return "position/positions";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("position") Position position, RedirectAttributes redirectAttributes) {
        positionService.savePosition(position);
        redirectAttributes.addFlashAttribute("message", "Thêm thành công vị trí");
        return "redirect:/positions/list";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("position") Position position, RedirectAttributes redirectAttributes) {
        positionService.updatePosition(position);
        redirectAttributes.addFlashAttribute("message", "Cập nhật thành công vị trí");
        return "redirect:/positions/list";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        try {
            positionService.deletePositionById(id);
            redirectAttributes.addFlashAttribute("message", "Xóa thành công vị trí");
            return "redirect:/positions/list";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/positions/list";
        }
    }

}
