package vn.thangloi.spring.hrm.service;

import vn.thangloi.spring.hrm.entity.Account;

public interface AccountService {
    public Account findAccountByUserName(String username);
}
