package farmershao.product.console.dao.entity;

import java.util.Date;

/**
 *
 * 商家：店铺
 * @author ShaoYu
 * @since 2019/2/4 0004 上午 11:21
 */
public class Merchants {

    private long id;
    private String name;        // 店铺名
    private String logo;        // 店铺LOGO
    private int level;          // 商家评级

    private Date createdAt;     // 创建时间
    private Date updatedAt;     // 修改时间
    private boolean deleted;    // 是否删除

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
