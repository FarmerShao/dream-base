package farmershao.product.console.dao.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author ShaoYu
 * @since 2019/2/2 0002 下午 8:46
 */
public class Product {

    private long id;
    private String name;        // 商品名称
    private BigDecimal price;   // 价格
    private long categoryFir;   // 一级目录
    private long categorySec;   // 二级目录
    private long categoryThi;   // 三级目录

    private Date createdAT;     // 创建时间
    private Date updatedAt;     // 修改时间
    private String modifier;    // 修改者账号

    public Date getCreatedAT() {
        return createdAT;
    }

    public void setCreatedAT(Date createdAT) {
        this.createdAT = createdAT;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public long getCategoryFir() {
        return categoryFir;
    }

    public void setCategoryFir(long categoryFir) {
        this.categoryFir = categoryFir;
    }

    public long getCategorySec() {
        return categorySec;
    }

    public void setCategorySec(long categorySec) {
        this.categorySec = categorySec;
    }

    public long getCategoryThi() {
        return categoryThi;
    }

    public void setCategoryThi(long categoryThi) {
        this.categoryThi = categoryThi;
    }
}
