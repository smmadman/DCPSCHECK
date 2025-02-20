package org.wjj.qrcpcheck.common.dao;

public class AccountEntity {
    private String id;
    private String uid;
    private String money;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "AccountEntity{" +
                "id='" + id + '\'' +
                ", uid='" + uid + '\'' +
                ", money='" + money + '\'' +
                '}';
    }
}
