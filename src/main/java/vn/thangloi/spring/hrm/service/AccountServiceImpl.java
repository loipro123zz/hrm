package vn.thangloi.spring.hrm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.thangloi.spring.hrm.dao.AccountRepository;
import vn.thangloi.spring.hrm.entity.Account;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    @Override
    public Account findAccountByUserName(String username) {
        return accountRepository.findByUsername(username);
    }
}
