package farmershao.product.console.dao.entity;

import java.util.Date;

/**
 * @author ShaoYu
 * @since 2019/2/1 0001 下午 2:29
 */
public class AdminManager {

    private long id;
    private String password;        // 密码
    private String phone;           // 手机号（账号）
    private String name;            // 姓名
    private Date lastLoginTime;     //最后登录时间

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
}
