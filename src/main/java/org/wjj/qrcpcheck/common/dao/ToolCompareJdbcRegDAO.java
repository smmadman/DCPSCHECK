package org.wjj.qrcpcheck.common.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ToolCompareJdbcRegDAO {
    int insert(@Param("toolCompareJdbcRegEntity") ToolCompareJdbcRegEntity toolCompareJdbcRegEntity);

    ToolCompareJdbcRegEntity selectByIdUrlUser(@Param("idUrlUser") String idUrlUser);

    ToolCompareJdbcRegEntity selectByAliasesJdbc(@Param("aliasesJdbc") String aliasesJdbc);
}
