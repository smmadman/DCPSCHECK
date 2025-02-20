package org.wjj.qrcpcheck.common.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AccountDAO {
    AccountEntity getById(@Param("id") String id);
}
