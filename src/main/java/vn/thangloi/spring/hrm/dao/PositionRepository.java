package vn.thangloi.spring.hrm.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.thangloi.spring.hrm.entity.Position;

public interface PositionRepository extends JpaRepository<Position, Integer> {

}
