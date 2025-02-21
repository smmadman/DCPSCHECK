package org.wjj.qrcpcheck.common.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ToolCompareUrlRegLogic {
    @Autowired
    ToolCompareUrlRegDAO toolCompareUrlRegDAO;

    public int insert(ToolCompareUrlRegEntity toolCompareUrlRegEntity){
        return toolCompareUrlRegDAO.insert(toolCompareUrlRegEntity);
    }

    public ToolCompareUrlRegEntity selectByIdUrlHeader(String idUrlHeader){
        return toolCompareUrlRegDAO.selectByIdUrlHeader(idUrlHeader);
    }

    public ToolCompareUrlRegEntity selectByAliasesUrl(String aliasesUrl){
        return toolCompareUrlRegDAO.selectByAliasesUrl(aliasesUrl);
    }
}
