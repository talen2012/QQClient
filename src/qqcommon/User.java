package qqcommon;

import java.io.Serializable;

/**
 * @author 田磊
 * @version 1.0
 * @date 2024/8/30
 * @time 14:01
 * 描述一个用户/客户信息
 */
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private String userId; // 用户ID/用户名
    private String pwd;  // 用户密码

    public User() {}

    public User(String UserId, String pwd) {
        this.userId = UserId;
        this.pwd = pwd;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
