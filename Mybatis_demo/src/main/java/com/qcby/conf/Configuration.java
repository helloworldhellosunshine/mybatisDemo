package com.qcby.conf;



import java.util.HashMap;
import java.util.Map;

/**
 * 此类用于封装配置信息
 */
public class Configuration {


    private String dbUrl;

    private String dbUserName;

    private String dbPassWord;

    private String dbDriver;

    //用于存放解析的mapper中的sql操作语句
    private final Map<String, MappedStatement> mappedStatements = new HashMap<String,MappedStatement>();


    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public String getDbUserName() {
        return dbUserName;
    }

    public void setDbUserName(String dbUserName) {
        this.dbUserName = dbUserName;
    }

    public String getDbPassWord() {
        return dbPassWord;
    }

    public void setDbPassWord(String dbPassWord) {
        this.dbPassWord = dbPassWord;
    }

    public String getDbDriver() {
        return dbDriver;
    }

    public void setDbDriver(String dbDriver) {
        this.dbDriver = dbDriver;
    }

    public Map<String, MappedStatement> getMappedStatements() {
        return mappedStatements;
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "dbUrl='" + dbUrl + '\'' +
                ", dbUserName='" + dbUserName + '\'' +
                ", dbPassWord='" + dbPassWord + '\'' +
                ", dbDriver='" + dbDriver + '\'' +
                ", mappedStatements=" + mappedStatements +
                '}';
    }
}
