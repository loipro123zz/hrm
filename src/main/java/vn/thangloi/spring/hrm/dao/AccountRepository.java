package vn.thangloi.spring.hrm.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.thangloi.spring.hrm.entity.Account;

public interface AccountRepository extends JpaRepository<Account, String> {
    Account findByUsername(String username);
}
