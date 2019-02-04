package farmershao.product.console.dao.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品
 * @author ShaoYu
 * @since 2019/2/2 0002 下午 8:46
 */
public class Product {

    private long id;
    private String name;        // 商品名称
    private BigDecimal price;   // 价格

    private long merchantId;    // 店铺ID
    private long categoryFir;   // 一级目录
    private long categorySec;   // 二级目录
    private long categoryThi;   // 三级目录
    private String attribute;   // 属性：json字符串

    private Date createdAT;     // 创建时间
    private Date updatedAt;     // 修改时间
    private String modifier;    // 修改者账号
    private boolean deleted;    // 是否删除

    public boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(long merchantId) {
        this.merchantId = merchantId;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

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
