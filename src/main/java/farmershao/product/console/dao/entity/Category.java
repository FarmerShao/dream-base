package farmershao.product.console.dao.entity;

/**
 *
 * 商品分类
 * @author ShaoYu
 * @since 2019/2/3 0003 上午 10:18
 */
public class Category {

    private long id;
    private int level;              // 分类级别
    private String name;            // 分类名
    private Long categoryOne;       // 一级分类ID
    private Long categoryTwo;       // 二级分类ID

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCategoryOne() {
        return categoryOne;
    }

    public void setCategoryOne(Long categoryOne) {
        this.categoryOne = categoryOne;
    }

    public Long getCategoryTwo() {
        return categoryTwo;
    }

    public void setCategoryTwo(Long categoryTwo) {
        this.categoryTwo = categoryTwo;
    }
}
