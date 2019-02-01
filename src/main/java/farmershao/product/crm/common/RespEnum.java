package farmershao.product.crm.common;

import io.vertx.core.json.JsonObject;

/**
 * @author ShaoYu
 * @since 2019/2/1 0001 下午 3:50
 */
public enum RespEnum {

    SUCCESS("0", "操作成功"),
    SYSTEM_ERROR("500", "系统异常，请联系管理员")
    ;

    private String code;
    private String msg;

    RespEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private JsonObject convert2Json() {
        JsonObject rspJson = new JsonObject();
        rspJson.put("code", code);
        rspJson.put("msg", msg);
        return rspJson;
    }

    public String toJson() {
        return convert2Json().toString();
    }

    public String toJson(Object result) {
        JsonObject jsonObject = convert2Json();
        if (this == SUCCESS) {
            jsonObject.put("data", result);
        }
        return jsonObject.toString();
    }
}
