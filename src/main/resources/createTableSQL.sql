CREATE TABLE TOOL_COMPARE_URL_REG (
    ID_URL_HEADER VARCHAR(100) PRIMARY KEY NOT NULL, -- url + | + header
    ALIASES_URL  VARCHAR(100)  NOT NULL UNIQUE, -- url别名，如:T4-常规报文头
    URL VARCHAR(100) NOT NULL,
    HEADER VARCHAR(100) NOT NULL UNIQUE,
    REG_TIME DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE TOOL_COMPARE_JDBC_REG (
    ID_URL_USER VARCHAR(100) PRIMARY KEY NOT NULL, -- url + | + user
    ALIASES_JDBC  VARCHAR(100)  NOT NULL UNIQUE, -- jdbc别名，如:T4-应用库
    URL VARCHAR(100) NOT NULL,
    JDBC_USER VARCHAR(100) NOT NULL,
    JDBC_PASSWORD VARCHAR(100) NOT null,
    JDBC_TYPE VARCHAR(100) NOT null,
    REG_TIME DATETIME DEFAULT CURRENT_TIMESTAMP
);

insert into `tool_compare_jdbc_reg` (`ID_URL_USER`, `ALIASES_JDBC`, `URL`, `JDBC_USER`, `JDBC_PASSWORD`, `JDBC_TYPE`, `REG_TIME`) values('jdbc:mysql://localhost:3306/test|root','本地测试Jdbc','jdbc:mysql://localhost:3306/test','root','y32163214','MYSQL','2025-02-21 15:38:41');
insert into `tool_compare_url_reg` (`ID_URL_HEADER`, `ALIASES_URL`, `URL`, `HEADER`, `REG_TIME`) values('http://127.0.0.1:8080/api/requestA|{\"Content-Type\": \"application/json\"}','本地测试','http://127.0.0.1:8080/api/requestA','{\"Content-Type\": \"application/json\"}','2025-02-21 14:46:43');
