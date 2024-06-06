package vn.thangloi.spring.hrm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.thangloi.spring.hrm.dao.PositionRepository;
import vn.thangloi.spring.hrm.entity.Department;
import vn.thangloi.spring.hrm.entity.Employee;
import vn.thangloi.spring.hrm.entity.Position;

import java.util.List;
import java.util.Set;

@Service
public class PositionServiceImpl implements PositionService {

    private PositionRepository positionRepository;

    @Autowired
    public PositionServiceImpl(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    @Override
    public List<Position> getAllPositions() {
        return positionRepository.findAll();
    }

    @Override
    public Position getPositionById(int id) {
        return positionRepository.findById(id).orElse(null);
    }

    @Override
    public Position savePosition(Position position) {
        return positionRepository.save(position);
    }

    @Override
    public Position updatePosition(Position position) {
        return positionRepository.saveAndFlush(position);
    }

    @Override
    public void deletePositionById(int id) {
        Position position = positionRepository.findById(id).orElse(null);
        if (position != null) {
            Set<Employee> employees = position.getEmployees();
            if (!employees.isEmpty()) {
                throw new RuntimeException("Không thể xóa vị trí này vì có nhân viên đang thuộc vị trí này.");
            } else {
                positionRepository.deleteById(id);
            }
        } else {
            throw new RuntimeException("Không có id vị trị này.");
        }
    }


}
