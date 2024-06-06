package vn.thangloi.spring.hrm.service;

import org.springframework.stereotype.Service;
import vn.thangloi.spring.hrm.entity.Position;

import java.util.List;


public interface PositionService {

    public List<Position> getAllPositions();

    public Position getPositionById(int id);

    public Position savePosition(Position position);

    public Position updatePosition(Position position);

    public void deletePositionById(int id);


}
