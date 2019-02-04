package farmershao.product.console.dao.entity;

/**
 *
 * 商品库存
 * @author ShaoYu
 * @since 2019/2/4 0004 上午 11:18
 */
public class ProductStock {

    private long id;
    private long productId;     // 商品ID
    private int stock;          // 库存

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
