package com.yundao.core.email;

/**
 * 用户名和密码
 *
 * @author wupengfei wupf86@126.com
 */
public class UsernamePassword {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    public UsernamePassword(String username, String password) {
        this.username = username;
        this.password = password;
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
}
