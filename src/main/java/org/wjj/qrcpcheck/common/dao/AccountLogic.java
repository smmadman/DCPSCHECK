package org.wjj.qrcpcheck.common.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountLogic {
    @Autowired
    AccountDAO accountDAO;

    public AccountEntity getById(String id){
        return accountDAO.getById(id);
    }
}
