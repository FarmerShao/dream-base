package farmershao.product.console.common;



public enum CacheKeyEnum {

    ADMIN_MANAGER("admin.manager."),
    ADMIN_MANAGER_PHONE("admin.manager.phone");

    private String key;

    CacheKeyEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}