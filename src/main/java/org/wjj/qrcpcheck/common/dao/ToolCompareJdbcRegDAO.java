package org.wjj.qrcpcheck.common.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ToolCompareJdbcRegDAO {
    int insert(@Param("toolCompareJdbcRegEntity") ToolCompareJdbcRegEntity toolCompareJdbcRegEntity);

    ToolCompareJdbcRegEntity selectByIdUrlUser(@Param("idUrlUser") String idUrlUser);

    ToolCompareJdbcRegEntity selectByAliasesJdbc(@Param("aliasesJdbc") String aliasesJdbc);
    List<String> selectAllAliasesJdbc();
}
