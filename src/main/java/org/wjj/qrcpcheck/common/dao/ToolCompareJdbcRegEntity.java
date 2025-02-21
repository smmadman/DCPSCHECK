package org.wjj.qrcpcheck.common.dao;

import java.sql.Time;

public class ToolCompareJdbcRegEntity {
    private String idUrlUser;
    private String aliasesJdbc;
    private String url;
    private String jdbcUser;
    private String jdbcPassword;
    private String jdbcType;
    private Time regTime;

    public String getIdUrlUser() {
        return idUrlUser;
    }

    public void setIdUrlUser(String idUrlUser) {
        this.idUrlUser = idUrlUser;
    }

    public String getAliasesJdbc() {
        return aliasesJdbc;
    }

    public void setAliasesJdbc(String aliasesJdbc) {
        this.aliasesJdbc = aliasesJdbc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getJdbcUser() {
        return jdbcUser;
    }

    public void setJdbcUser(String jdbcUser) {
        this.jdbcUser = jdbcUser;
    }

    public String getJdbcPassword() {
        return jdbcPassword;
    }

    public void setJdbcPassword(String jdbcPassword) {
        this.jdbcPassword = jdbcPassword;
    }

    public String getJdbcType() {
        return jdbcType;
    }

    public void setJdbcType(String jdbcType) {
        this.jdbcType = jdbcType;
    }

    public Time getRegTime() {
        return regTime;
    }

    public void setRegTime(Time regTime) {
        this.regTime = regTime;
    }

    @Override
    public String toString() {
        return "ToolCompareJdbcRegEntity{" +
                "idUrlUser='" + idUrlUser + '\'' +
                ", aliasesJdbc='" + aliasesJdbc + '\'' +
                ", url='" + url + '\'' +
                ", jdbcUser='" + jdbcUser + '\'' +
                ", jdbcPassword='" + jdbcPassword + '\'' +
                ", jdbcType='" + jdbcType + '\'' +
                ", regTime=" + regTime +
                '}';
    }
}