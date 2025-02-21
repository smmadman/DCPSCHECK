package org.wjj.qrcpcheck.common.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ToolCompareUrlRegDAO {
    int insert(@Param("toolCompareUrlRegEntity") ToolCompareUrlRegEntity toolCompareUrlRegEntity);

    ToolCompareUrlRegEntity selectByIdUrlHeader(@Param("idUrlHeader") String idUrlHeader);

    ToolCompareUrlRegEntity selectByAliasesUrl(@Param("aliasesUrl") String aliasesUrl);
}
