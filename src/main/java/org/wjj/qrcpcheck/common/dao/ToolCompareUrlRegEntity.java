package org.wjj.qrcpcheck.common.dao;

import java.sql.Time;

public class ToolCompareUrlRegEntity {
    private String idUrlHeader;
    private String aliasesUrl;
    private String url;
    private String header;
    private Time regTime;

    public String getIdUrlHeader() {
        return idUrlHeader;
    }

    public void setIdUrlHeader(String idUrlHeader) {
        this.idUrlHeader = idUrlHeader;
    }

    public String getAliasesUrl() {
        return aliasesUrl;
    }

    public void setAliasesUrl(String aliasesUrl) {
        this.aliasesUrl = aliasesUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public Time getRegTime() {
        return regTime;
    }

    public void setRegTime(Time regTime) {
        this.regTime = regTime;
    }

    @Override
    public String toString() {
        return "ToolCampareUrlRegEntity{" +
                "idUrlHeader='" + idUrlHeader + '\'' +
                ", aliasesUrl='" + aliasesUrl + '\'' +
                ", url='" + url + '\'' +
                ", header='" + header + '\'' +
                ", regTime=" + regTime +
                '}';
    }
}
