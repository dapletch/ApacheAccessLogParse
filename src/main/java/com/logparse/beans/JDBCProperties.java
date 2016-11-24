package com.logparse.beans;

/**
 * Created by Seth on 11/22/2016.
 */
public class JDBCProperties {

    private String className;
    private String url;
    private String username;
    private String password;

    public JDBCProperties() {
        super();
    }

    public JDBCProperties(String className, String url, String username, String password) {
        this.className = className;
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "JDBCProperties{" +
                "className='" + className + '\'' +
                ", url='" + url + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
