package org.wjj.qrcpcheck.common.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ToolCompareJdbcRegLogic {
    @Autowired
    ToolCompareJdbcRegDAO toolCompareJdbcRegDAO;

    public int insert(ToolCompareJdbcRegEntity toolComparejdbcRegEntity){
        return toolCompareJdbcRegDAO.insert(toolComparejdbcRegEntity);
    }

    public ToolCompareJdbcRegEntity selectByIdUrlUser(String idUrlUser){
        return toolCompareJdbcRegDAO.selectByIdUrlUser(idUrlUser);
    }

    public ToolCompareJdbcRegEntity selectByAliasesJdbc(String aliasesJdbc){
        return toolCompareJdbcRegDAO.selectByAliasesJdbc(aliasesJdbc);
    }

    public List<String> selectAllAliasesJdbc(){
        return toolCompareJdbcRegDAO.selectAllAliasesJdbc();
    }
}
