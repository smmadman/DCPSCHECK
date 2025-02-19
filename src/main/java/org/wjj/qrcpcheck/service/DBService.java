package org.wjj.qrcpcheck.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.wjj.qrcpcheck.util.MySQLDBTest;

import java.util.List;

@Service
public class DBService {
    @Autowired
    MySQLDBTest mySQLDBTest;

    public List<String> getTableNames(String url, String user, String password){
        return mySQLDBTest.mySQLTabelsGet(url, user, password);
    }
}
