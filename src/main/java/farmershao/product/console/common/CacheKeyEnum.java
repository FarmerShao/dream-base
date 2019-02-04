package farmershao.product.console.common;



public enum CacheKeyEnum {

    TOKEN("token."),

    ADMIN_MANAGER("admin.manager."),
    CATEGORY_ID("category.id."),
    ADMIN_MANAGER_PHONE("admin.manager.phone");

    private String key;

    CacheKeyEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}